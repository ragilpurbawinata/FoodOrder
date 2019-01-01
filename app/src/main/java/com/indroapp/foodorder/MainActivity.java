package com.indroapp.foodorder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.indroapp.foodorder.adapter.MakananAdapter;
import com.indroapp.foodorder.adapter.MinumanAdapter;
import com.indroapp.foodorder.model.MenuModel;
import com.indroapp.foodorder.presenter.MainPresenter;
import com.indroapp.foodorder.service.API;
import com.indroapp.foodorder.view.MainView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements
        MainView, MakananAdapter.MakananSelect, MinumanAdapter.MinumanSelect {
    RecyclerView rvMakanan, rvMinuman;
    TextView btPesan;
    EditText etMeja;
    ArrayList<MenuModel> listPesanMakanan, listPesanMinuman;
    MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvMakanan = findViewById(R.id.rvMakanan);
        rvMinuman = findViewById(R.id.rvMinuman);
        btPesan = findViewById(R.id.btPesan);
        etMeja = findViewById(R.id.noMeja);

        presenter = new MainPresenter(this, this);
        presenter.getMakanan(API.BASE_URL+"pesan.php?menu=makanan");
        presenter.getMinuman(API.BASE_URL+"pesan.php?menu=minuman");

        listPesanMakanan = new ArrayList<>();
        listPesanMinuman = new ArrayList<>();

        btPesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postPesan();
            }
        });
    }

    @Override
    public void onSuccesGetMakanan(ArrayList<MenuModel> menuModel) {
        MakananAdapter adapter = new MakananAdapter(menuModel, this);
        rvMakanan.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvMakanan.setAdapter(adapter);
    }

    @Override
    public void onSuccesGetMinuman(ArrayList<MenuModel> menuModel) {
        MinumanAdapter adapter = new MinumanAdapter(menuModel, this);
        rvMinuman.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvMinuman.setAdapter(adapter);
    }

    @Override
    public void onSuccesPesan(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        finish();
        startActivity(getIntent());
    }

    @Override
    public void resultMakanan(ArrayList<MenuModel> listPesan) {
        listPesanMakanan = listPesan;
    }

    @Override
    public void resultMinuman(ArrayList<MenuModel> listPesan) {
        listPesanMinuman = listPesan;
    }

    private void postPesan() {
        String noMeja = etMeja.getText().toString();
        boolean arMkFill = false, arMiFill = false;

        JSONArray arrayMakanan = new JSONArray();
        for (int i=0;i<listPesanMakanan.size();i++){
            MenuModel model = listPesanMakanan.get(i);
            JSONObject obj = new JSONObject();
            if (model.getItemId()!=0 && model.getJumPesan()!=0){
                try {
                    obj.put("id", model.getItemId());
                    obj.put("jumlah", model.getJumPesan());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                arrayMakanan.put(obj);
                arMkFill = true;
            }
        }

        JSONArray arrayMinuman = new JSONArray();
        for (int i=0;i<listPesanMinuman.size();i++){
            MenuModel model = listPesanMinuman.get(i);
            JSONObject obj = new JSONObject();
            if (model.getItemId()!=0 && model.getJumPesan()!=0){
                try {
                    obj.put("id", model.getItemId());
                    obj.put("jumlah", model.getJumPesan());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                arrayMinuman.put(obj);
                arMiFill = true;
            }
        }

        if (!etMeja.getText().toString().isEmpty() && arMkFill && arMiFill){
            presenter.sendPesan(API.BASE_URL+"pesan.php", arrayMakanan.toString(), arrayMinuman.toString(), noMeja);
        }
        else {
            Toast.makeText(this, "Lengkapi isian !", Toast.LENGTH_LONG).show();
        }
    }
}

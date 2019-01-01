package com.indroapp.foodorder.presenter;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.indroapp.foodorder.model.MenuModel;
import com.indroapp.foodorder.model.ResponPesan;
import com.indroapp.foodorder.service.AppController;
import com.indroapp.foodorder.utils.DialogUtil;
import com.indroapp.foodorder.view.MainView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import timber.log.Timber;

public class MainPresenter {
    public Context context;
    private MainView mainView;

    public MainPresenter(Context context, MainView mainView) {
        this.context = context;
        this.mainView = mainView;
    }

    public void getMakanan(String url){
        DialogUtil.showProgressDialog(context, "Memuat menu...");
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ArrayList<MenuModel> list = new ArrayList<>();
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            Timber.e(String.valueOf(jsonArray.length()));
                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                MenuModel model = new MenuModel();
                                model.setItemId(jsonObject.getInt("item_id"));
                                model.setItemNama(jsonObject.getString("item_nama"));
                                model.setItemHarga(jsonObject.getInt("item_harga"));
                                model.setItemStok(jsonObject.getInt("item_stok"));
                                list.add(model);
                            }
                            mainView.onSuccesGetMakanan(list);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        DialogUtil.dialogDismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        AppController.getInstance().addToRequestQueue(request);
    }

    public void getMinuman(String url){
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ArrayList<MenuModel> list = new ArrayList<>();
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            Timber.e(String.valueOf(jsonArray.length()));
                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                MenuModel model = new MenuModel();
                                model.setItemId(jsonObject.getInt("item_id"));
                                model.setItemNama(jsonObject.getString("item_nama"));
                                model.setItemHarga(jsonObject.getInt("item_harga"));
                                model.setItemStok(jsonObject.getInt("item_stok"));
                                list.add(model);
                            }
                            mainView.onSuccesGetMinuman(list);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        AppController.getInstance().addToRequestQueue(request);
    }

    public void sendPesan(String url, final String makanan, final String minuman, final String meja){
        DialogUtil.showProgressDialog(context, "Order pesanan...");
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ResponPesan responPesan = new Gson().fromJson(response, ResponPesan.class);
                        if (!responPesan.getError()){
                            mainView.onSuccesPesan(responPesan.getMessage());
                            DialogUtil.dialogDismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("makanan", makanan);
                params.put("minuman", minuman);
                params.put("meja", meja);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(request);
    }
}

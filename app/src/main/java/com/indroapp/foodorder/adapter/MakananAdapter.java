package com.indroapp.foodorder.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.indroapp.foodorder.R;
import com.indroapp.foodorder.model.MenuModel;
import com.indroapp.foodorder.utils.FormatRupiah;

import java.util.ArrayList;
import java.util.List;

public class MakananAdapter extends RecyclerView.Adapter<MakananAdapter.Holder> {
    private ArrayList<MenuModel> modelList;
    private ArrayList<MenuModel> makananSelect;
    private MakananSelect listener;

    public MakananAdapter(ArrayList<MenuModel> modelList, MakananSelect listener) {
        this.modelList = modelList;
        this.listener = listener;
        makananSelect = new ArrayList<>();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rv_item_menu, viewGroup, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, int i) {
        final MenuModel model = modelList.get(i);
        final int pos = i;

        holder.nama.setText(model.getItemNama());
        holder.harga.setText(FormatRupiah.formatRupiah(model.getItemHarga()));
        holder.stok.setText("Stok : "+model.getItemStok());
        holder.jumPesan.setText(String.valueOf(holder.jum));

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.jum++;
                holder.jumPesan.setText(String.valueOf(holder.jum));
                updatePesan(model.getItemId(), holder.jum, pos);
            }
        });

        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.jum>0){
                    holder.jum--;
                    holder.jumPesan.setText(String.valueOf(holder.jum));
                    updatePesan(model.getItemId(), holder.jum, pos);
                }
            }
        });

        MenuModel modelPesan = new MenuModel();
        modelPesan.setItemId(0);
        modelPesan.setJumPesan(0);
        makananSelect.add(modelPesan);
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView nama, harga, stok, jumPesan;
        ImageButton add, del;
        int jum = 0;

        public Holder(@NonNull View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.nama);
            harga = itemView.findViewById(R.id.harga);
            stok = itemView.findViewById(R.id.stok);
            jumPesan = itemView.findViewById(R.id.jumPesan);
            add = itemView.findViewById(R.id.addPesan);
            del = itemView.findViewById(R.id.delPesan);
        }
    }

    private void updatePesan(int id, int jum, int pos){
        if (jum!=0){
            MenuModel modelPesan = new MenuModel();
            modelPesan.setItemId(id);
            modelPesan.setJumPesan(jum);
            makananSelect.set(pos, modelPesan);
        }
        listener.resultMakanan(makananSelect);
    }

    public interface MakananSelect{
        void resultMakanan(ArrayList<MenuModel> listPesan);
    }
}

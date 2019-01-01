package com.indroapp.foodorder.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MenuModel {
    private Integer itemId;
    private String itemNama;
    private Integer itemHarga;
    private Integer itemStok;
    private Integer jumPesan;

    public Integer getJumPesan() {
        return jumPesan;
    }

    public void setJumPesan(Integer jumPesan) {
        this.jumPesan = jumPesan;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getItemNama() {
        return itemNama;
    }

    public void setItemNama(String itemNama) {
        this.itemNama = itemNama;
    }

    public Integer getItemHarga() {
        return itemHarga;
    }

    public void setItemHarga(Integer itemHarga) {
        this.itemHarga = itemHarga;
    }

    public Integer getItemStok() {
        return itemStok;
    }

    public void setItemStok(Integer itemStok) {
        this.itemStok = itemStok;
    }
}

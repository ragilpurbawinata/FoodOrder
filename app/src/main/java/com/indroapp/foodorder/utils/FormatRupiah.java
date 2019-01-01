package com.indroapp.foodorder.utils;

import java.text.NumberFormat;
import java.util.Locale;

public class FormatRupiah {
    private static Locale localeID = new Locale("in", "ID");
    private static final NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
    public static String formatRupiah(int price){
        return formatRupiah.format(price);
    }
}

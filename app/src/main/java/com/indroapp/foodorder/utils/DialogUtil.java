package com.indroapp.foodorder.utils;

import android.app.ProgressDialog;
import android.content.Context;

public class DialogUtil {
    private static ProgressDialog m_Dialog;

    public static ProgressDialog showProgressDialog(Context context, String message){
        m_Dialog = new ProgressDialog(context);
        m_Dialog.setMessage(message);
        m_Dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        m_Dialog.setCancelable(false);
        m_Dialog.show();
        return m_Dialog;
    }

    public static void dialogDismiss(){
        m_Dialog.dismiss();
    }
}

package com.indroapp.foodorder.view;

import com.indroapp.foodorder.model.MenuModel;

import java.util.ArrayList;
import java.util.List;

public interface MainView {
    void onSuccesGetMakanan(ArrayList<MenuModel> menuModel);
    void onSuccesGetMinuman(ArrayList<MenuModel> menuModel);
    void onSuccesPesan(String msg);
}

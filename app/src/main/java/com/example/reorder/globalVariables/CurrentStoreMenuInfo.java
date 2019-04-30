package com.example.reorder.globalVariables;

import com.example.reorder.info.BookMarkStoreInfo;
import com.example.reorder.info.Store;
import com.example.reorder.info.StoreMenu;

public class CurrentStoreMenuInfo {
    private static StoreMenu storeMenu=new StoreMenu();

    public static StoreMenu getStoreMenu() {
        return storeMenu;
    }

    public static void setStoreMenu(StoreMenu storeMenu) {
        CurrentStoreMenuInfo.storeMenu = storeMenu;
    }
}

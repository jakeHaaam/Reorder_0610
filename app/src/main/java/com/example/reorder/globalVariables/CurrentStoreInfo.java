package com.example.reorder.globalVariables;

import com.example.reorder.info.Store;

public class CurrentStoreInfo {
    private static Store store=new Store();

    public static Store getStore() { return store;    }

    public static void setStore(Store store) { CurrentStoreInfo.store = store;    }
}

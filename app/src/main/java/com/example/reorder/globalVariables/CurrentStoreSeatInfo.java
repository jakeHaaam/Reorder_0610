package com.example.reorder.globalVariables;

import com.example.reorder.info.StoreSeat;

public class CurrentStoreSeatInfo {
    private static StoreSeat storeSeat=new StoreSeat();

    public static StoreSeat getStoreSeat() {
        return storeSeat;
    }

    public static void setStoreSeat(StoreSeat storeSeat) {
        CurrentStoreSeatInfo.storeSeat = storeSeat;
    }
}

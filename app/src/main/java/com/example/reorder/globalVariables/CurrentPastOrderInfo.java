package com.example.reorder.globalVariables;

import com.example.reorder.info.PastOrder;

public class CurrentPastOrderInfo {
    private static PastOrder pastOrder=new PastOrder();

    public static PastOrder getPastOrder() {
        return pastOrder;
    }

    public static void setPastOrder(PastOrder pastOrder) {
        CurrentPastOrderInfo.pastOrder = pastOrder;
    }
}
package com.example.reorder.info;

import com.example.reorder.info.PastOrder;
import com.example.reorder.info.RenewPast;

import java.util.List;

public class RenewPastOrderInfo {
    private static RenewPast renewPast=new RenewPast();

    public static RenewPast getRenewPast() {
        return renewPast;
    }

    public static void setRenewPast(RenewPast renewPast) {
        RenewPastOrderInfo.renewPast = renewPast;
    }
}
package com.example.reorder.globalVariables;

import com.example.reorder.info.LiveOrderState;

public class CurrentLiveOrderStateInfo {
    private static LiveOrderState liveOrderState=new LiveOrderState();

    public static LiveOrderState getLiveOrderState() {
        return liveOrderState;
    }

    public static void setLiveOrderState(LiveOrderState liveOrderState) {
        CurrentLiveOrderStateInfo.liveOrderState = liveOrderState;
    }
}
package com.example.reorder.info;

import java.util.List;

public class LiveOrderState {
    List<LiveOrderStateInfo> liveOrderStateInfos;

    public List<LiveOrderStateInfo> getLiveOrderStateInfos() {
        return liveOrderStateInfos;
    }

    public void setLiveOrderStateInfos(List<LiveOrderStateInfo> liveOrderStateInfos) {
        this.liveOrderStateInfos = liveOrderStateInfos;
    }
}
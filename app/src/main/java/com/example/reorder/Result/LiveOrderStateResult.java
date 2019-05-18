package com.example.reorder.Result;

import com.example.reorder.info.LiveOrderStateInfo;

import java.util.List;

public class LiveOrderStateResult {
    int result;
    List<LiveOrderStateInfo> liveOrderStateInfoList;//현재 값 2개만 보내는 상태

    public LiveOrderStateResult(int result, List<LiveOrderStateInfo> liveOrderStateInfoList) {
        this.result = result;
        this.liveOrderStateInfoList = liveOrderStateInfoList;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public List<LiveOrderStateInfo> getLiveOrderStateInfoList() {
        return liveOrderStateInfoList;
    }

    public void setLiveOrderStateInfoList(List<LiveOrderStateInfo> liveOrderStateInfoList) {
        this.liveOrderStateInfoList = liveOrderStateInfoList;
    }
}
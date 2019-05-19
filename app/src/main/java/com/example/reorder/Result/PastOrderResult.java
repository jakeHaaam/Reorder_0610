package com.example.reorder.Result;

import com.example.reorder.info.PastOrderInfo;

import java.util.List;

public class PastOrderResult {
    int result;
    List<PastOrderInfo> pastOrderInfos;

    public PastOrderResult(int result, List<PastOrderInfo> pastOrderInfos) {
        this.result = result;
        this.pastOrderInfos = pastOrderInfos;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public List<PastOrderInfo> getPastOrderInfos() {
        return pastOrderInfos;
    }

    public void setPastOrderInfos(List<PastOrderInfo> pastOrderInfos) {
        this.pastOrderInfos = pastOrderInfos;
    }
}
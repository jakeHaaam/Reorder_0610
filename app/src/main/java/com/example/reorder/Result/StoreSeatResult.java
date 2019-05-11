package com.example.reorder.Result;

import com.example.reorder.info.SeatInfo;
import com.example.reorder.info.StoreSeatInfo;

import java.util.List;

public class StoreSeatResult {
    int result;
    StoreSeatInfo storeSeatInfo;
    List<SeatInfo> seatInfos;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public StoreSeatInfo getStoreSeatInfo() {
        return storeSeatInfo;
    }

    public void setStoreSeatInfo(StoreSeatInfo storeSeatInfo) {
        this.storeSeatInfo = storeSeatInfo;
    }

    public List<SeatInfo> getSeatInfos() {
        return seatInfos;
    }

    public void setSeatInfos(List<SeatInfo> seatInfos) {
        this.seatInfos = seatInfos;
    }

    public StoreSeatResult(int result, StoreSeatInfo storeSeatInfo, List<SeatInfo> seatInfos) {
        this.result = result;
        this.storeSeatInfo = storeSeatInfo;
        this.seatInfos = seatInfos;
    }
}

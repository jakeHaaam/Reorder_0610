package com.example.reorder.Result;

import com.example.reorder.info.StoreMenuInfo;

import java.util.List;

public class StoreIdResult {
    //스토어 아이디 보내주면 메뉴 정보랑 전화번호, 가게위치 같은거 어떻게 받아올지 작성
    int result;
    List<StoreMenuInfo> storeMenuInfo;

    public StoreIdResult(int result, List<StoreMenuInfo> storeMenuInfo) {
        this.result = result;
        this.storeMenuInfo = storeMenuInfo;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public List<StoreMenuInfo> getStoreMenuInfo() {
        return storeMenuInfo;
    }

    public void setStoreMenuInfo(List<StoreMenuInfo> storeMenuInfo) {
        this.storeMenuInfo = storeMenuInfo;
    }
}

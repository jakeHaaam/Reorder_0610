package com.example.reorder.Result;

import com.example.reorder.info.StoreMenuInfo;

public class StoreIdResult {
    //스토어 아이디 보내주면 메뉴 정보랑 전화번호, 가게위치 같은거 어떻게 받아올지 작성
    int result;
    StoreMenuInfo storeMenuInfo;

    public StoreIdResult(int result, StoreMenuInfo storeMenuInfo) {
        this.result = result;
        this.storeMenuInfo = storeMenuInfo;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public StoreMenuInfo getStoreMenuInfo() {
        return storeMenuInfo;
    }

    public void setStoreMenuInfo(StoreMenuInfo storeMenuInfo) {
        this.storeMenuInfo = storeMenuInfo;
    }
}

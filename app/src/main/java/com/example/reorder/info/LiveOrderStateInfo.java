package com.example.reorder.info;

public class LiveOrderStateInfo {//오더 시간,+몇번째 순서인지 이거되면 좋아
    int order_serial;
    int order_state;

    public int getOrder_serial() {
        return order_serial;
    }

    public void setOrder_serial(int order_serial) {
        this.order_serial = order_serial;
    }

    public int getOrder_state() {
        return order_state;
    }

    public void setOrder_state(int order_state) {
        this.order_state = order_state;
    }

    public LiveOrderStateInfo(int order_serial, int order_state) {
        this.order_serial = order_serial;
        this.order_state = order_state;
    }
}
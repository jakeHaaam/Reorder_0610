package com.example.reorder.info;

public class LiveOrderStateInfo {
    int order_serial;
    int order_state;
    String store_name;
    int sequence;

    public LiveOrderStateInfo(int order_serial, int order_state, String store_name, int sequence) {
        this.order_serial = order_serial;
        this.order_state = order_state;
        this.store_name = store_name;
        this.sequence = sequence;
    }

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

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }
}
package com.example.reorder.Result;

public class OrderAndSeatResult {
    int result;
    int order_serial;
    int order_state;
    int id;//개개인의 고유 좌석번호

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
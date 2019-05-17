package com.example.reorder.Result;

public class OrderAndSeatResult {
    int result;
    int oreder_serial;
    int order_state;
    int id;//개개인의 고유 좌석번호

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int getOreder_serial() {
        return oreder_serial;
    }

    public void setOreder_serial(int oreder_serial) {
        this.oreder_serial = oreder_serial;
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
package com.example.reorder.Result;

public class OrderResult {
    int result;
    int order_id;
    int order_state;

    public OrderResult(int result, int order_id, int order_state) {
        this.result = result;
        this.order_id = order_id;
        this.order_state = order_state;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getOrder_state() {
        return order_state;
    }

    public void setOrder_state(int order_state) {
        this.order_state = order_state;
    }
}

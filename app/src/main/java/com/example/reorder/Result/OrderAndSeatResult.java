package com.example.reorder.Result;

public class OrderAndSeatResult {
    int result;
    int oreder_id;
    int order_state;
    int id;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int getOreder_id() {
        return oreder_id;
    }

    public void setOreder_id(int oreder_id) {
        this.oreder_id = oreder_id;
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

    public OrderAndSeatResult(int result, int oreder_id, int order_state, int id) {
        this.result = result;
        this.oreder_id = oreder_id;
        this.order_state = order_state;
        this.id = id;
    }
}
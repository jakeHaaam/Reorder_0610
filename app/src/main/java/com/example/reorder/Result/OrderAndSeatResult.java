package com.example.reorder.Result;

public class OrderAndSeatResult {
    int result;
    int oreder_number;

    public OrderAndSeatResult(int result, int oreder_number) {
        this.result = result;
        this.oreder_number = oreder_number;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int getOreder_number() {
        return oreder_number;
    }

    public void setOreder_number(int oreder_number) {
        this.oreder_number = oreder_number;
    }
}
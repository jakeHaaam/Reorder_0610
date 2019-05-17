package com.example.reorder.globalVariables;

public class SeatOrderState {
    public static int order_id;
    public static int order_state;
    public static int id;

    public static int getOrder_id() {
        return order_id;
    }

    public static void setOrder_id(int order_id) {
        SeatOrderState.order_id = order_id;
    }

    public static int getOrder_state() {
        return order_state;
    }

    public static void setOrder_state(int order_state) {
        SeatOrderState.order_state = order_state;
    }

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        SeatOrderState.id = id;
    }
}
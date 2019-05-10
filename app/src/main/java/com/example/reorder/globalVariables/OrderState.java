package com.example.reorder.globalVariables;

public class OrderState {
    public static int order_id;
    public static int order_state;

    public OrderState() {
    }

    public static int getOrder_id() {
        return order_id;
    }

    public static void setOrder_id(int order_id) {
        OrderState.order_id = order_id;
    }

    public static int getOrder_state() {
        return order_state;
    }

    public static void setOrder_state(int order_state) {
        OrderState.order_state = order_state;
    }
}

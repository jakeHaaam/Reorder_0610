package com.example.reorder.globalVariables;

public class CurrentUsingSeatInfo {
    public static int seat_id=0;//0으로 초기화

    public static int getSeat_id() {
        return seat_id;
    }

    public static void setSeat_id(int seat_id) {
        CurrentUsingSeatInfo.seat_id = seat_id;
    }
}

package com.example.reorder.globalVariables;

import com.example.reorder.info.Seat;

public class CurrentSeatInfo {
    private static Seat seat = new Seat();

    public static Seat getSeat() {
        return seat;
    }

    public static void setSeat(Seat seat) {
        CurrentSeatInfo.seat = seat;
    }
}

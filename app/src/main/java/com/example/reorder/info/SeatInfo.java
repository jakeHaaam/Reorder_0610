package com.example.reorder.info;

public class SeatInfo {
    int id;//이건 seat_id라고 생각
    int seat_statement;

    public SeatInfo(int id, int seat_statement) {
        this.id = id;
        this.seat_statement = seat_statement;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSeat_statement() {
        return seat_statement;
    }

    public void setSeat_statement(int seat_statement) {
        this.seat_statement = seat_statement;
    }
}

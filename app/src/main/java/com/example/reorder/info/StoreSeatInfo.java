package com.example.reorder.info;

public class StoreSeatInfo {
    int storeinfo_id;//store_id
    int seat_row;
    int seat_num;

    public int getStoreinfo_id() {
        return storeinfo_id;
    }

    public void setStoreinfo_id(int storeinfo_id) {
        this.storeinfo_id = storeinfo_id;
    }

    public int getSeat_row() {
        return seat_row;
    }

    public void setSeat_row(int seat_row) {
        this.seat_row = seat_row;
    }

    public int getSeat_num() {
        return seat_num;
    }

    public void setSeat_num(int seat_num) {
        this.seat_num = seat_num;
    }

    public StoreSeatInfo(int storeinfo_id, int seat_row, int seat_num) {
        this.storeinfo_id = storeinfo_id;
        this.seat_row = seat_row;
        this.seat_num = seat_num;
    }
}

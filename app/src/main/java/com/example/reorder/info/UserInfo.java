package com.example.reorder.info;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class UserInfo  {
    int id;
    String client_id;
    String client_password;
    int client_mileage;
    int client_seat_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getClient_password() {
        return client_password;
    }

    public void setClient_password(String client_password) {
        this.client_password = client_password;
    }

    public int getClient_mileage() {
        return client_mileage;
    }

    public void setClient_mileage(int client_mileage) {
        this.client_mileage = client_mileage;
    }

    public int getClient_seat_id() {
        return client_seat_id;
    }

    public void setClient_seat_id(int client_seat_id) {
        this.client_seat_id = client_seat_id;
    }

    public UserInfo(int id, String client_id, String client_password, int client_mileage, int client_seat_id) {
        this.id = id;
        this.client_id = client_id;
        this.client_password = client_password;
        this.client_mileage = client_mileage;
        this.client_seat_id = client_seat_id;
    }
}

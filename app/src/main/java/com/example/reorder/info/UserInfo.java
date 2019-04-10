package com.example.reorder.info;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class UserInfo  {
    int id;
    String client_id;
    String client_password;
    int client_mileage;



    public UserInfo(int id,String client_id, String client_password,int client_mileage) {
        this.id=id;
        this.client_id=client_id;
        this.client_password = client_password;
        this.client_mileage=client_mileage;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClientId(String client_id) {
        this.client_id = client_id;
    }

    public String getClient_password() {
        return client_password;
    }

    public void setClient_password(String client_password) {this.client_password = client_password; }

    public int getClient_mileage() {
        return client_mileage;
    }

    public void setClient_mileage(int client_mileage) {
        this.client_mileage = client_mileage;
    }


}

package com.example.reorder.info;

import android.os.Parcel;
import android.os.Parcelable;

public class UserInfo implements Parcelable {
    String client_email;//**client_id로 해야하나??
    String client_password;//**


    public UserInfo(String userId, String userPassword) {
        this.client_email = client_email;
        this.client_password = client_password;
    }

    protected UserInfo(Parcel in) {
        client_email = in.readString();
        client_password = in.readString();
    }



    public static final Creator<UserInfo> CREATOR = new Creator<UserInfo>() {

        @Override
        public UserInfo createFromParcel(Parcel in) {
            return new UserInfo(in);
        }

        @Override
        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };

    public String getClient_id() {
        return client_email;
    }

    public void setClient_id(String client_email) {
        this.client_email = client_email;
    }

    public String getClient_password() {
        return client_password;
    }

    public void setClient_password(String client_password) {
        this.client_password = client_password;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(client_email);
        dest.writeString(client_password);
    }
}
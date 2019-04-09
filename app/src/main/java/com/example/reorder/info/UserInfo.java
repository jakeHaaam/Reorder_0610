package com.example.reorder.info;

import android.os.Parcel;
import android.os.Parcelable;

public class UserInfo implements Parcelable {
    String client_email;
    String client_password;
    int client_mileage;
    String client_bookmark_id;
    int client_id;
    Boolean isChange=false;


    public UserInfo(String client_email, String client_password,int client_mileage,String client_bookmark_id) {
        this.client_email = client_email;
        this.client_password = client_password;
        this.client_mileage=client_mileage;
        this.client_bookmark_id=client_bookmark_id;
        this.client_id=client_id;
    }


    public String getClient_email() {
        return client_email;
    }

    public void setClient_email(String client_email) {
        this.client_email = client_email;
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

    public String getClient_bookmark_id() {
        return client_bookmark_id;
    }

    public void setClient_bookmark_id(String client_bookmark_id) {
        this.client_bookmark_id = client_bookmark_id;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public Boolean getChange(){
        return isChange;
    }
    public void setChange(Boolean change){
        isChange=change;
    }

    public static Creator<UserInfo>getCREATOR() {return CREATOR;}


    protected UserInfo(Parcel in) {
        client_email = in.readString();
        client_password = in.readString();
        client_mileage=in.readInt();
        client_bookmark_id=in.readString();
        client_id=in.readInt();
        byte tmpIsChange=in.readByte();
        isChange=tmpIsChange==0? null: tmpIsChange==1;

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(client_email);
        dest.writeString(client_password);
        dest.writeInt(client_mileage);
        dest.writeString(client_bookmark_id);
        dest.writeInt(client_id);
        dest.writeByte((byte)(isChange==null? 0 : isChange? 1:2));
    }
}

package com.example.reorder.globalVariables;

public class CurrentLocation {
    public static double lat;
    public static double lng;

    public static double getLat() {
        return lat;
    }

    public static void setLat(double lat) {
        CurrentLocation.lat = lat;
    }

    public static double getLng() {
        return lng;
    }

    public static void setLng(double lng) {
        CurrentLocation.lng = lng;
    }
}

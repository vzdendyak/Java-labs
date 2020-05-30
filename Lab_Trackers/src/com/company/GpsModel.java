package com.company;

public class GpsModel {
    int id;
    double coordinateLong;
    double coordinateLat;
    String coordinates;
    GpsModel(int id, String coordinateLong, String coordinateLat) {
        this.id = id;
        this.coordinateLong = Double.parseDouble(coordinateLong)*2;
        this.coordinateLat = Double.parseDouble(coordinateLat)*2;
    }
}


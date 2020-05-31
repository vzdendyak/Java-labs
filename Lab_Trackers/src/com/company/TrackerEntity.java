package com.company;

public class TrackerEntity {
    int id;
    String name;
    String coordinates;
     String[] coordinatesArray;
    public TrackerEntity(int id, String name, String coordinates){
        this.id =id;
        this.name=name;
        this.coordinates = coordinates;
    }
}

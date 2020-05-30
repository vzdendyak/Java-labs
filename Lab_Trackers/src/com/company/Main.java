package com.company;

import java.util.HashSet;

public class Main {
    static StringBuilder number = new StringBuilder();

    public static void main(String[] args) {

        var models = GpsReader.ReadGps("10.kml");
        ParseModels(models);
        models = GpsReader.ReadGps("71.kml");
        ParseModels(models);
        models = GpsReader.ReadGps("73.kml");
        ParseModels(models);
        models = GpsReader.ReadGps("77.kml");
        ParseModels(models);



    }

    public static void ParseModels(GpsModel[] models){
        DBaseConnection baseConnection = new DBaseConnection();
        HashSet<String> coordSet = new HashSet<String>();
        assert models != null;
        for (GpsModel model : models) {
            Coordinate lon = new Coordinate(0, 360);
            Coordinate lat = new Coordinate(0, 180);
            model.coordinates = FindTile(model, lon, lat, 30);
            if (!coordSet.contains(model.coordinates)){
                coordSet.add(model.coordinates);
                number.append(",").append(model.coordinates);
            }
        }
        TrackerEntity track = new TrackerEntity(1,"First", number.toString());
        baseConnection.addRow(track);
        System.out.println(models.length);
        System.out.println(coordSet.size());
     }
    public static String FindTile(GpsModel model, Coordinate lon, Coordinate lat, int zoom) {
        StringBuilder numberTemp = new StringBuilder();
        for (int i = 0; i < zoom; i++) {
            float lonDiff = (lon.min + (lon.max - lon.min) / 2);
            float latDiff = (lat.min + (lat.max - lat.min) / 2);
            if (model.coordinateLong < lonDiff && model.coordinateLat > latDiff) {
                //0
                //lon.max = (lon.min + (lon.max - lon.min) / 2); // < lon
                //lat.min = (lat.min + (lat.max - lat.min) / 2); // > lat
                lon.max = lonDiff; // < lon
                lat.min = latDiff; // > lat
                numberTemp.append('0');

            } else if (model.coordinateLong > lonDiff && model.coordinateLat > latDiff) {
                //1
                // lat.min = lat.max / 2; // > lat
                //lon.min = lon.max / 2; // > lon
                lat.min = latDiff; // > lat
                lon.min = lonDiff; // > lon
                numberTemp.append('1');
            } else if (model.coordinateLong < lonDiff && model.coordinateLat < latDiff) {
                //2
                //lon.max = (lon.min + (lon.max - lon.min) / 2); // < lon
                //lat.max = (lat.min + (lat.max - lat.min) / 2); // < lat
                lon.max = lonDiff; // < lon
                lat.max = latDiff; // < lat
                numberTemp.append('2');
            } else if (model.coordinateLong > lonDiff && model.coordinateLat < latDiff) {
                //3
                //lon.min = lon.max / 2; // > lon
                //lat.max = (lat.min + (lat.max - lat.min) / 2); // < lat
                lon.min = lonDiff; // > lon
                lat.max = latDiff; // < lat
                numberTemp.append('3');
            }
        }
        return numberTemp.toString();
    }
}

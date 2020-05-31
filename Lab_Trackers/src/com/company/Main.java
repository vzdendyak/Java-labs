package com.company;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

public class Main {

    static StringBuilder number;
    static int ZOOM = 20;

    public static void main(String[] args) {

        boolean write = true;

        if (write) {
            DBaseConnection baseConnection = new DBaseConnection();
            baseConnection.clean();
            var models = GpsReader.ReadGps("10.kml");
            ParseModels(models, 10);
            models = GpsReader.ReadGps("71.kml");
            ParseModels(models, 71);
            models = GpsReader.ReadGps("73.kml");
            ParseModels(models, 73);
            models = GpsReader.ReadGps("77.kml");
            ParseModels(models, 77);

            ParseTrackersToModels();

        } else {
            ParseTrackersToModels();
        }


    }

    public static void ParseTrackersToModels() {
        DBaseConnection baseConnection = new DBaseConnection();
        int trackerSelected = 0;

        var trackers = baseConnection.selectAll();
        if (trackers.length == 0)
            return;

        System.out.println(ConsoleColors.ANSI_PURPLE + "Database contains " + trackers.length + " trackers" + ConsoleColors.ANSI_RESET);

        for (int i = 0; i < trackers.length; i++) {
            System.out.println(ConsoleColors.ANSI_YELLOW + (i + 1) + ". Track " + trackers[i].name);
            trackers[i].coordinatesArray = ParseTiles(trackers[i].coordinates);
        }
        System.out.println("Select MAIN track: ");
        Scanner in = new Scanner(System.in);
        trackerSelected = in.nextInt();

        var selectedTracker = trackers[trackerSelected - 1];
        System.out.println();
        for (TrackerEntity track : trackers) {
            if (selectedTracker.id == track.id)
                continue;
            System.out.println(String.format("%s    Compare tracker %s and tracker %s %s", ConsoleColors.ANSI_GREEN, selectedTracker.name, track.name, ConsoleColors.ANSI_RESET));
            CompareTwoTails(selectedTracker.coordinatesArray, track.coordinatesArray);
            System.out.println();
        }

    }

    public static void CompareTwoTails(String[] mainTails, String[] subTails) {
        HashSet<String> result = new HashSet<>(Arrays.asList(mainTails));
        result.retainAll(Arrays.asList(subTails));


        float equalityPercent = (result.size() / (float) (mainTails.length + subTails.length) * 100);
        System.out.println(String.format("Main tail count: %d. \nSub Tail count: %d.\nEqual points count: %d. \n    %sEquality: %.2f %% .%s",
                mainTails.length, subTails.length, result.size(), ConsoleColors.ANSI_RED, equalityPercent, ConsoleColors.ANSI_RESET));
    }

    public static String[] ParseTiles(String tiles) {
        String[] coords = tiles.split(",");
        return coords;
    }

    public static void ParseModels(GpsModel[] models, int id) {
        number = new StringBuilder();
        DBaseConnection baseConnection = new DBaseConnection();
        HashSet<String> coordSet = new HashSet<String>();
        assert models != null;
        for (GpsModel model : models) {
            Coordinate lon = new Coordinate(0, 360);
            Coordinate lat = new Coordinate(0, 180);
            model.coordinates = FindTile(model, lon, lat, ZOOM);
            if (!coordSet.contains(model.coordinates)) {
                coordSet.add(model.coordinates);
                if (number.length() == 0) {
                    number.append(model.coordinates);
                } else {
                    number.append(",").append(model.coordinates);
                }
            }

        }
        TrackerEntity track = new TrackerEntity(id, Integer.toString(id), number.toString());
        baseConnection.addRow(track);
        System.out.println(ConsoleColors.ANSI_BLUE + "Track " + id + ConsoleColors.ANSI_RESET + "\nCoords number: " + models.length);
        System.out.println("Unique coords number: " + coordSet.size());
        System.out.println("String length: " + number.length());
        System.out.println();

    }


    public static String FindTile(GpsModel model, Coordinate lon, Coordinate lat, int zoom) {
        StringBuilder numberTemp = new StringBuilder();
        for (int i = 0; i < zoom; i++) {
            float lonDiff = (lon.min + (lon.max - lon.min) / 2);
            float latDiff = (lat.min + (lat.max - lat.min) / 2);
            if (model.coordinateLong < lonDiff && model.coordinateLat > latDiff) {
                //0
                lon.max = lonDiff; // < lon
                lat.min = latDiff; // > lat
                numberTemp.append('0');

            } else if (model.coordinateLong > lonDiff && model.coordinateLat > latDiff) {
                //1
                lat.min = latDiff; // > lat
                lon.min = lonDiff; // > lon
                numberTemp.append('1');
            } else if (model.coordinateLong < lonDiff && model.coordinateLat < latDiff) {
                //2
                lon.max = lonDiff; // < lon
                lat.max = latDiff; // < lat
                numberTemp.append('2');
            } else if (model.coordinateLong > lonDiff && model.coordinateLat < latDiff) {
                //3
                lon.min = lonDiff; // > lon
                lat.max = latDiff; // < lat
                numberTemp.append('3');
            }
        }
        return numberTemp.toString();
    }
}

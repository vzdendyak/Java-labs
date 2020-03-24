package com.company;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Scanner;

import data.Response;

public class Main {

    public static void main(String[] args) throws IOException {
        String city;
        FileReader cityReader = new FileReader("cities.txt");
        Scanner scanner = new Scanner(cityReader);
        int i = 1;
        while (scanner.hasNextLine()) {
            city = scanner.nextLine();

            Json.request(city);
            String jsonText = "";
            try {
                FileReader freader = new FileReader("server.json");
                Scanner scan = new Scanner(freader);
                int j = 1;
                while (scan.hasNextLine()) {
                    jsonText += scan.nextLine();
//                System.out.println(i + " : " + scan.nextLine());
                    j++;
                }
            } catch (IOException ex) {
                System.out.println("Error! " + ex.getMessage());
            }
//        System.out.println(jsonText);

            Response myData = Json.fromJson(jsonText);
            showTable(myData);
            writeToFile(myData);
            i++;
        }
    }

    public static void showTable(Response weatherData) {
        System.out.println(" City: " + weatherData.getCity().getName());
        for (int i = 0; i <= 6; i++) {
            System.out.println(" Date : " + weatherData.getList().get(i).getDtTxt());
            System.out.println(" Weather : " + weatherData.getList().get(i).getWeather().get(0).getMain());
            System.out.println(" Tempetarute : " + kelvinToCelsium(weatherData.getList().get(i).getMain().getTemp().toString())+"°");
            System.out.println("--------------------");
        }

        System.out.println("######################################################################");

    }

    public static void writeToFile(Response weatherData) throws IOException {
        FileWriter fileServer = new FileWriter("weather.txt", true);
        String inputLine;
//        for (int i = 0; i <= 6; i++) {
        int i = 0;
        fileServer.write("*********************************************\nCity: " + weatherData.getCity().getName() + "\n*********************************************\n\n");
        while (i < weatherData.getList().size()) {
            fileServer.write(" Date : " + weatherData.getList().get(i).getDtTxt() + '\n');
            fileServer.write(" Weather : " + weatherData.getList().get(i).getWeather().get(0).getMain() + '\n');
            fileServer.write(" Tempetarute : " + kelvinToCelsium(weatherData.getList().get(i).getMain().getTemp().toString()) + "°\n");
            fileServer.write("----------------------------------------------------------------------------\n");
            i++;
        }
        fileServer.close();

    }

    public static double kelvinToCelsium(String kelvinTemp) {
        double kelvinDouble = Double.parseDouble(kelvinTemp);

        return Math.round(((kelvinDouble - 273.15) * 1) * 100.0) / 100.0;
    }
}
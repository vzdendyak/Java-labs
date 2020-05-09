package com.company;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.Gson;
import data.City;
import data.Response;


public class Json {
//    private static String Url = "http://api.openweathermap.org/data/2.5/forecast?q=Truskavets&appid=e1f212833a4fa11a2cf5e0a5a263814f";
    private static String USER_AGENT = "Mozilla/5.0";

    public static void request(String cityName) throws IOException {
        String Url = "http://api.openweathermap.org/data/2.5/forecast?q="+cityName+"&appid=e1f212833a4fa11a2cf5e0a5a263814f";

        URL url = new URL(Url);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            FileWriter fileServer = new FileWriter("server.json",false);

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
                fileServer.append(inputLine);
            }
            in.close();
            fileServer.close();

            // print result
//            System.out.println(response.toString());
        } else {
            System.out.println("GET request not worked");
        }
    }
    public static Response fromJson(String jsonText){
        Response response= new Gson().fromJson(jsonText,Response.class);
//        System.out.println(response);
        return response;
    }
}

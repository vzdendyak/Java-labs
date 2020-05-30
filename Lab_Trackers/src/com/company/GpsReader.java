package com.company;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class GpsReader {
    static GpsModel[] ReadGps(String fileName) {
        try {

            // Відкриваємо наш файл gpx (вхідний)
            File inputFile = new File(fileName);
            // Команди по створенню файла
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            // Отримуємо тег wpt з нашого вхідного файлу
            NodeList nameList = doc.getDocumentElement().getElementsByTagName("longitude");
            NodeList nodeDescription = doc.getDocumentElement().getElementsByTagName("latitude");
            GpsModel[] models = new GpsModel[nameList.getLength()];
            for (int i = 0; i < nameList.getLength(); i++) {
                models[i] = new GpsModel(i, nameList.item(i).getTextContent(), nodeDescription.item(i).getTextContent());
            }
            return models;
        } catch (Exception ex) {

            ex.printStackTrace();
            return null;
        }
    }

    static String[] concatCoordinates(String coordinates) {
        int kr = coordinates.indexOf('.');
        int kom = coordinates.indexOf(',', kr);
        String first = coordinates.substring(0, kom);
        String second = coordinates.substring(kom + 1, coordinates.length());
        return new String[]{first, second};

    }
}

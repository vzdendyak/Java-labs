package com.company;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class GpsReader {
    static GpsModel[] ReadGps(String fileName) {
        try {

            // Відкриваємо наш файл kml (вхідний)
            File inputFile = new File(fileName);

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();


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

}

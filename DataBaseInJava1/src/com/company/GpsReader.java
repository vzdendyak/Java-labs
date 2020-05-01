package com.company;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class GpsReader {
    static GpsModel[] ReadGps() {
        try {

            // Відкриваємо наш файл gpx (вхідний)
            File inputFile = new File("points.kml");
            // Команди по створенню файла
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            // Отримуємо тег wpt з нашого вхідного файлу
            NodeList nameList = doc.getDocumentElement().getElementsByTagName("name");
            NodeList nodeDescription = doc.getDocumentElement().getElementsByTagName("coordinates");
            GpsModel[] models = new GpsModel[nameList.getLength()];
            for (int i = 0; i < nameList.getLength(); i++) {
                String[] coord = concatCoordinates(nodeDescription.item(i).getTextContent());
                models[i] = new GpsModel(i, nameList.item(i).getTextContent(), coord[0],coord[1]);
            }
            return models;
        } catch (Exception ex) {

            ex.printStackTrace();
            return null;
        }
    }
    static String[] concatCoordinates(String coordinates){
        int kr = coordinates.indexOf('.');
        int kom = coordinates.indexOf(',',kr);
        String first = coordinates.substring(0,kom);
        String second = coordinates.substring(kom+1,coordinates.length());
        return new String[]{first,second};

    }

}


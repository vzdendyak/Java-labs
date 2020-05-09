package com.company;

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class Main {

    public static void main(String[] args) {
        try {
            // Відкриваємо наш файл gpx (вхідний)
            File inputFile = new File("L-35-003-points.gpx");
            // Команди по створенню файла
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document docGpx = dBuilder.parse(inputFile);
            docGpx.getDocumentElement().normalize();

            // Створюємо новий kml документ (вихідний)
            Document docKml = dBuilder.newDocument();
            // створюємо основний тег <kml>
            Element rootElement = docKml.createElement("kml");
            docKml.appendChild(rootElement);
            // Створюємо тег <document>
            Element docEl = docKml.createElement("Document");
            rootElement.appendChild(docEl);
            // Отримуємо тег wpt з нашого вхідного файлу
            NodeList nList = docGpx.getDocumentElement().getElementsByTagName("wpt");

            for (int i = 0; i < nList.getLength(); i++) {
                // Отримуємо один вузол
                Node nNode = nList.item(i);
                // Витягуємо з вхідного елементу тег <cmt>
                NodeList infoName = docGpx.getElementsByTagName("cmt");
                // Витягуємо з вхідного елементу тег <link>
                NodeList infoDesc = docGpx.getElementsByTagName("link");

                Element element = (Element) nNode;

                // Витягуємо атрибут з елементу тегу  link
                var links = infoDesc.item(i).getAttributes().item(0).getTextContent();
                // Створюємо нашу структуру KML документа
                Element placeMark = docKml.createElement("Placemark");
                Element nameElement = docKml.createElement("name");
                Element pointElement = docKml.createElement("Point");
                Element descriptionElement = docKml.createElement("Description");
                Element coordinatesElement = docKml.createElement("coordinates");

                // Додаємо створені теги у відповідному порядку
                docEl.appendChild(placeMark);

                nameElement.appendChild(docKml.createTextNode(infoName.item(i).getTextContent()));
                placeMark.appendChild(nameElement);

                descriptionElement.appendChild(docKml.createTextNode(links));
                placeMark.appendChild(descriptionElement);

                coordinatesElement.appendChild(docKml.createTextNode(element.getAttribute("lon")+","+element.getAttribute("lat")+",0"));
                pointElement.appendChild(coordinatesElement);
                placeMark.appendChild(pointElement);

                // Перетворюємо у KML
                TransformerFactory transformerFactory =
                        TransformerFactory.newInstance();
                Transformer transformer =
                        transformerFactory.newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
                DOMSource source = new DOMSource(docKml);
                StreamResult result =
                        new StreamResult(new File("newPointsFile.kml"));
                transformer.transform(source, result);
                StreamResult consoleResult =
                        new StreamResult(System.out);
                transformer.transform(source, consoleResult);

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}

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
            File inputFile = new File("L-35-003-points.gpx");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document docGpx = dBuilder.parse(inputFile);
            docGpx.getDocumentElement().normalize();

            // create new document kml
            Document docKml = dBuilder.newDocument();
            Element rootElement = docKml.createElement("kml");
            docKml.appendChild(rootElement);
            // create main tag
            Element docEl = docKml.createElement("Document");
            rootElement.appendChild(docEl);
            // get tag 'wpt'
            NodeList nList = docGpx.getDocumentElement().getElementsByTagName("wpt");

            for (int i = 0; i < nList.getLength(); i++) {
                // get node
                Node nNode = nList.item(i);
                // cmt == name
                NodeList infoName = docGpx.getElementsByTagName("cmt");
                // link == description
                NodeList infoDesc = docGpx.getElementsByTagName("link");
                Element element = (Element) nNode;

                var links = infoDesc.item(i).getAttributes().item(0).getTextContent();
                // create elements for KML document
                Element placeMark = docKml.createElement("Placemark");
                Element nameElement = docKml.createElement("name");
                Element pointElement = docKml.createElement("Point");
                Element descriptionElement = docKml.createElement("Description");
                Element coordinatesElement = docKml.createElement("coordinates");
                // add elements in right order
                docEl.appendChild(placeMark);
                nameElement.appendChild(docKml.createTextNode(infoName.item(i).getTextContent()));
                placeMark.appendChild(nameElement);
                descriptionElement.appendChild(docKml.createTextNode(links));
                placeMark.appendChild(descriptionElement);
                // add a description-link
                coordinatesElement.appendChild(docKml.createTextNode(element.getAttribute("lon")+","+element.getAttribute("lat")+",0"));
                pointElement.appendChild(coordinatesElement);
                placeMark.appendChild(pointElement);

                // create a KML file
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

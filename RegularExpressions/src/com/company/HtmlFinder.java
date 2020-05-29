package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlFinder {

    static public void FindLinks() {
        ArrayList<String> links = new ArrayList<String>();
        Pattern pattern = Pattern.compile("<a\\s+(?:[^>]*?\\s+)?href[\\s]*=[\\s]*([\"'])(.*?)\\1", Pattern.CASE_INSENSITIVE);//поиск совпадений с шаблоном будет производиться без учета регистра символов.
        var htmlText = readLinksFromHtml();
        Matcher matcher = pattern.matcher(htmlText);
        while (matcher.find()) {
            links.add(matcher.group(2));
        }
        writeLinksToHtml(links);
    }
    
    static private String readLinksFromHtml() {
        StringBuilder htmlText = new StringBuilder();
        try {
            File myObj = new File("in.html");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                htmlText.append(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return htmlText.toString();
    }

    static private void writeLinksToHtml(ArrayList<String> links) {
        StringBuilder htmlBegin =
                new StringBuilder("<!DOCTYPE html>\n"
                        + "<html>\n"
                        + " <head>\n"
                        + "  <title>Назви</title>\n"
                        + " </head>\n"
                        + " <body>\n"
                        + "  <table border=\"1\">");
        String htmlEnd = "\n  </table>\n"
                + " </body>\n"
                + "</html>\n";
        for (int i = 0; i < links.size(); i++) {
            String tempHtml = String.format("\n   <tr>\n    <td>%d</td>\n    <td>%s</td>\n   </tr>", i, links.get(i));
            htmlBegin.append(tempHtml);
        }
        htmlBegin.append(htmlEnd);
        System.out.println(htmlBegin);
        try {
            FileWriter myWriter = new FileWriter("out.html",false);
            myWriter.write(htmlBegin.toString());
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
//<tr>
//<td>1</td>
//<td>http://pnu.edu.ua</td>
//</tr>
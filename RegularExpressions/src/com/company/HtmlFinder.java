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
        // Task 1  - Find Links
        var htmlText = readLinksFromHtml("in.html");
        Pattern pattern = Pattern.compile("<a\\s+(?:[^>]*?\\s+)?href[\\s]*=[\\s]*([\"'])(.*?)\\1", Pattern.CASE_INSENSITIVE);//поиск совпадений с шаблоном будет производиться без учета регистра символов.
        Matcher matcher = pattern.matcher(htmlText);

        while (matcher.find()) {
            links.add(matcher.group(2));
        }
        writeLinksToHtml(links);
    }

    static public void FindKontakts(){
        var htmlTextPhones = readContactsFromHtml("phones.html");
        Pattern pattern = Pattern.compile("<td .* style=\"width: 32.0388%\">.*\\n([А-ЯІЄЮЙ]*).*\\n*([А-ЯІЄЮЙ]*.*) ([А-ЯІЄЮЙ].*)<.*\\n.*<td.*26\\.2136.*>(\\+\\d{2}\\(\\d{4}\\)\\d{2}-\\d{2}-\\d{2})", Pattern.CASE_INSENSITIVE);//поиск совпадений с шаблоном будет производиться без учета регистра символов.
        Matcher matcher = pattern.matcher(htmlTextPhones);
        ArrayList<KontaktHelper> contacts = new ArrayList<KontaktHelper>();
        while (matcher.find()) {
            KontaktHelper contact = new KontaktHelper(matcher.group(2),matcher.group(1),matcher.group(3),matcher.group(4));
            contacts.add(contact);
        }
        writeContactsToHtml(contacts);
    }
    static private String readLinksFromHtml(String fileName) {
        StringBuilder htmlText = new StringBuilder();
        try {
            File myObj = new File(fileName);
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

    static private String readContactsFromHtml(String fileName) {
        StringBuilder htmlText = new StringBuilder();
        try {
            File myObj = new File(fileName);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                htmlText.append('\n'+data);
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
        //System.out.println(htmlBegin);
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
    static private void writeContactsToHtml(ArrayList<KontaktHelper> contacts){
        StringBuilder htmlBegin =
                new StringBuilder("<!DOCTYPE html>\n"
                        + "<html lang=\"uk\"><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n"
                        + " <head>\n"
                        + "  <title>Назви</title>\n"
                        + " </head>\n"
                        + " <body>\n"
                        + "  <table border=\"1\">");
        String htmlEnd = "\n  </table>\n"
                + " </body>\n"
                + "</html>\n";
        for (KontaktHelper contact : contacts) {
            String tempHtml = String.format("\n   <tr>\n    <td>%s<br>%s %s</td>\n    <td>%s</td>\n   </tr>", contact.LastName, contact.FirstName, contact.Patronymic, contact.PhoneNumber);
            htmlBegin.append(tempHtml);
        }
        htmlBegin.append(htmlEnd);
        try {
            FileWriter myWriter = new FileWriter("phonesOut.html",false);
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
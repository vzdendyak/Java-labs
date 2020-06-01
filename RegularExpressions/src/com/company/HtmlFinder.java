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

    // task 1
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
    // task 4
    static public void FindDateInComments() {
        ArrayList<String> links = new ArrayList<String>();
        var text = readContactsFromHtml("task4.txt");
        Pattern pattern = Pattern.compile("\\/\\*\\n(((([0-3][0-1])|([0-2][0-9]))-((([0-1][0-2])|(0[0-9]))|(січ[а-яі]+|лют[а-яі]+|бер[а-яі]+|квіт[а-яі]+|трав[а-яі]+)|черв[а-яі]+|лип[а-яі]+|серпн[а-яі]+|верес[а-яі]+|жовт[а-яі]+|листоп[а-яі]+|груд[а-яі]+)-(\\d{1,4}))|((([0-3][0-1])|([0-2][0-9]))-((([0-1][0-2])|(0[0-9]))|(січ[а-яі]+|лют[а-яі]+|бер[а-яі]+|квіт[а-яі]+|трав[а-яі]+)|черв[а-яі]+|лип[а-яі]+|серп[а-яі]+|верес[а-яі]+|жовт[а-яі]+|листоп[а-яі]+|груд[а-яі]+)))\\n\\*\\/", Pattern.CASE_INSENSITIVE);//поиск совпадений с шаблоном будет производиться без учета регистра символов.
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            System.out.println(matcher.group(1));
        }
    }
    // task 2
    static public void FindContacts() {
        var htmlTextPhones = readContactsFromHtml("dovidnuk.html");
        //Pattern pattern = Pattern.compile("<td .* style=\"width: 32.0388%\">.*\\n([А-ЯІЄЮЙ]*).*\\n*([А-ЯІЄЮЙ]*.*) ([А-ЯІЄЮЙ].*)<.*\\n.*<td.*26\\.2136.*>(\\+\\d{2}\\(\\d{4}\\)\\d{2}-\\d{2}-\\d{2})", Pattern.CASE_INSENSITIVE);//поиск совпадений с шаблоном будет производиться без учета регистра символов.
        Pattern pattern = Pattern.compile("<td .* style=\"width: \\d{2}.\\d{4}%\">.*\\n([А-ЯІЄЮЙ]*).*\\n*([А-ЯІЄЮЙ]*.*) ([А-ЯІЄЮЙ].*)<.*\\n.*<td.*\\d{2}\\.\\d{4}.*>(\\+\\d{2}\\(\\d{4}\\)\\d{2}-\\d{2}-\\d{2})");//поиск совпадений с шаблоном будет производиться без учета регистра символов.
        Matcher matcher = pattern.matcher(htmlTextPhones);
        ArrayList<ContactHelper> contacts = new ArrayList<ContactHelper>();
        while (matcher.find()) {
            ContactHelper contact = new ContactHelper(matcher.group(2), matcher.group(1), matcher.group(3), matcher.group(4));
            contacts.add(contact);
        }
        writeContactsToHtml(contacts);
    }
    // task 3
    static public void FindMonthAndReplace() {
        var text = readContactsFromHtml("task3.txt");
        Pattern pattern = Pattern.compile("(\\d{1,2}) (лют[а-яі]*|берез[а-яі]*|квіт[а-яі]*|трав[а-яі]*|черв[а-яі]*|лип[а-яі]*|серп[а-яі]*|верес[а-яі]*|жовт[а-яі]*|листопад[а-яі]*|груд[а-яі]*)*", Pattern.CASE_INSENSITIVE);//поиск совпадений с шаблоном будет производиться без учета регистра символов.
        Matcher matcher = pattern.matcher(text);
        StringBuilder newText = new StringBuilder();
        while (matcher.find()) {
            newText.append(matcher.group(1)).append(" Січня\n");
        }
        try {
            FileWriter myWriter = new FileWriter("task3.txt", false);
            myWriter.write(newText.toString());
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    // task 5
    static public void FindCombinationCatDog(){
        var text = readContactsFromHtml("task5.txt");
        Pattern pattern = Pattern.compile("((чорний|білий)\\s(пес|кіт))", Pattern.CASE_INSENSITIVE);//поиск совпадений с шаблоном будет производиться без учета регистра символов.
        Matcher matcher = pattern.matcher(text);
        StringBuilder newText = new StringBuilder();
        int whiteCat,whiteDog,blackCat,blackDog;
        whiteCat=whiteDog=blackDog=blackCat=0;
        while (matcher.find()) {
            switch (matcher.group(1)){
                case "білий пес":
                    whiteDog++;
                    break;
                case "чорний пес":
                    blackDog++;
                    break;
                case "білий кіт":
                    whiteCat++;
                    break;
                case "чорний кіт":
                    blackCat++;
                    break;
            }
        }
        System.out.println(String.format("Чорних котів: %d\nБілих котів: %d\nЧорних собак: %d\nБілих собак: %d",blackCat,whiteCat,blackDog,whiteDog));

    }
    // task 6
    static public void FindFirstLastNameValid(){
        var text = readContactsFromHtml("task6.txt");
        Pattern pattern = Pattern.compile("([A-Z][a-z]+ [A-Z][a-z]+\\s*)|([А-ЯІЇ][а-яії]+ [А-ЯІЇ][а-яії]+\\s?)");//поиск совпадений с шаблоном будет производиться без учета регистра символов.
        Matcher matcher = pattern.matcher(text);
        StringBuilder newText = new StringBuilder();
        System.out.println("Файл: " + text);
        while (matcher.find()) {
            System.out.println("Допустиме прізвище: " + matcher.group(2));
        }

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
                htmlText.append('\n' + data);
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
            FileWriter myWriter = new FileWriter("out.html", false);
            myWriter.write(htmlBegin.toString());
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    static private void writeContactsToHtml(ArrayList<ContactHelper> contacts) {
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
        for (ContactHelper contact : contacts) {
            String tempHtml = String.format("\n   <tr>\n    <td>%s<br>%s %s</td>\n    <td>%s</td>\n   </tr>", contact.LastName, contact.FirstName, contact.Patronymic, contact.PhoneNumber);
            htmlBegin.append(tempHtml);
        }
        htmlBegin.append(htmlEnd);
        try {
            FileWriter myWriter = new FileWriter("phonesOut.html", false);
            myWriter.write(htmlBegin.toString());
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}

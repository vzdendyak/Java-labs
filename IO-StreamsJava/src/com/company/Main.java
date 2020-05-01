package com.company;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        Model[] userList = new Model[10];
        userList[0] = new Model(2, 18.5, 196, "Vasyl", "Zdendyak");
        userList[1] = new Model(3, 19, 169, "Ivan", "Tsikhun");
        userList[2] = new Model(1, 20.2, 158, "Igor", "Yavorskiy");
        userList[3] = new Model(4, 21, 112, "Igor", "Khalupniak");
        userList[4] = new Model(5, 25, 168, "Mihail", "Dubrovskiy");
        userList[5] = new Model(7, 35.4, 195, "Oleksandr", "Datsiuk");
        userList[6] = new Model(8, 45.7, 147, "Zahar", "Chesnniy");
        userList[7] = new Model(6, 55, 157, "Pavlo", "Poseniv");
        userList[8] = new Model(1000, 65.2, 137, "Oleksiy", "Kucher");
        userList[9] = new Model(9, 15, 168, "Denis", "Khomei");
        Arrays.sort(userList);
        writeToFile(userList);
        var users = readFromFile();
    }

    public static void writeToFile(Model[] users) {
        try {
            String header = "| Id | FirstName | LastName | Age | Height |\n";
            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream("info.txt"), StandardCharsets.UTF_8);
            writer.write(header);
            for (int i = 0; i < users.length; i++) {
                int id = users[i].getId();
                double age = users[i].getAge();
                int height = users[i].getHeight();
                String firstName = users[i].getFirstName();
                String lastName = users[i].getLastName();

                String output = String.format("| %d | %s | %s | %.2f | %d |", id, firstName, lastName, age, height);
                writer.write(output);
                writer.write("\n");
            }
            writer.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Model> readFromFile() {
        ArrayList<Model> res = new ArrayList<Model>();
        try {
            FileInputStream is = new FileInputStream("info.txt");
            InputStreamReader reader = new InputStreamReader(is, StandardCharsets.UTF_8);
            int tempId = 0, tempHeight = 0;
            String tempFirstName = null, tempLastName = null;
            double tempAge = 0;
            char symbol;
            int counter = 0;
            String tempString = "";
            for (int i=0; i < 45; i++)
                reader.read();

            while ((symbol = (char) reader.read()) != '\uFFFF') {
                if (symbol == '|') {
                    try {
                        tempString = tempString.trim();
                        int tempCounter = counter % 5;
                        switch (tempCounter) {
                            case 0:
                                tempId = Integer.parseInt(tempString);
                                System.out.println(tempId);
                                break;
                            case 1:
                                tempFirstName = tempString;
                                System.out.println(tempFirstName);
                                break;
                            case 2:
                                tempLastName = tempString;
                                System.out.println(tempLastName);
                                break;
                            case 3:
                               tempString =  tempString.replace(',','.');
                                tempAge = Double.parseDouble(tempString);
                                System.out.println(tempAge);
                                break;
                            case 4:
                                tempHeight = Integer.parseInt(tempString);
                                System.out.println(tempHeight);
                                break;
                        }
                        //counter = 0;
                    } catch (Exception e) {
                        System.out.println("BAD");
                    }
                    //System.out.println(tempString);
                    if (!tempString.equals(""))
                        counter++;
                    tempString = "";
                } else if (symbol == '\n') {
                    if (tempId != 0 && tempAge != 0 && tempHeight != 0 && tempFirstName != null && tempLastName != null) {
                        Model tempModel = new Model(tempId, tempAge, tempHeight, tempFirstName, tempLastName);
                        res.add(tempModel);
                    }
                } else {
                    tempString += symbol;
                }


                //System.out.print(symbol);
            }

            reader.close();
        } catch (Exception e) {

        }
        return res;
    }

}

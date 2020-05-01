package com.company;

import org.w3c.dom.ls.LSOutput;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Main {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLUE = "\u001B[34m";

    public static void main(String[] args) {
        DBaseConnection baseConnection = new DBaseConnection();
        // Очищуємо базу перед записом
        baseConnection.clean();
        // Отримуємо дані з kml файлу
        var models = GpsReader.ReadGps();
        // Записуємо ці дані у память
        baseConnection.addRange(models);
        int buttonSelected = 1;

        while (buttonSelected != 6) {

            System.out.println(ANSI_GREEN + "Оберіть одну з команд!");
            System.out.println(ANSI_BLUE + "1. Отримати записи ");
            System.out.println(ANSI_BLUE + "2. Отримати записи в певних межах ");
            System.out.println(ANSI_BLUE + "3. Видалити запис по ID");
            System.out.println(ANSI_BLUE + "4. Модифікувати по ID");
            System.out.println(ANSI_BLUE + "5. Очистити список");
            System.out.println(ANSI_BLUE + "6. Вихід");
            Scanner in = new Scanner(System.in);
            System.out.println(ANSI_RESET);

            buttonSelected = in.nextInt();

            switch (buttonSelected) {

                case 1:
                    baseConnection.selectAll();
                    break;
                case 2:
                    System.out.println("Введіть першу межу для координати Х");
                    int firstX = in.nextInt();
                    System.out.println("Введіть другу межу для координати Х");
                    int secondX = in.nextInt();
                    System.out.println("Введіть першу межу для координати У");
                    int firstY = in.nextInt();
                    System.out.println("Введіть другу межу для координати У");
                    int secondY = in.nextInt();
                    baseConnection.getInRange(firstX, secondX, firstY, secondY);
                    break;
                case 3:
                    System.out.println("Введіть id для видалення");
                    int idDelete = in.nextInt();
                    baseConnection.deleteRow(idDelete);
                    break;
                case 4:
                    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                    System.out.println("Введіть id для оновлення");
                    int idUpdate = in.nextInt();
                    try {
                        System.out.println("Введіть нове імя");
                        String newName = reader.readLine();
                        System.out.println("Введіть нову координату X");
                        String newX = reader.readLine();
                        float newXf = Float.parseFloat(newX);
                        System.out.println("Введіть нову координату Y");
                        String newY = reader.readLine();

                        float newYf = Float.parseFloat(newY);
                        baseConnection.updateRow(idUpdate, newName, newXf, newYf);
                    } catch (IOException e) {
                        e.printStackTrace();
                        break;
                    }
                    break;
                case 5:
                    baseConnection.clean();
                    break;
                case 6:
                    buttonSelected = 6;
                    break;
                default:
                    System.out.println("Виберіть команду зі списку, або завершіть роботу! Не мучте того компютора ");


            }
        }
    }
}

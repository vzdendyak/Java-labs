package com;

public class HastTable {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    private final int maxSize = 100;
    private Item[] items;

    public HastTable() {
        items = new Item[maxSize];
    }

    public void Insert(Item item) {
        if (item.Key == 0) {
            throw new IllegalArgumentException();
        }
        int hash = GetHash(item.Key);
        if (items[hash] == null) {
            items[hash] = item;
            ShowCurrentItem(hash);
            if (HowManyNotNull() > maxSize / 2) {
                System.out.println(ANSI_RED + "УВАГА. Перевищено половину memory. Можливi затримки у обрахуваннях!" + ANSI_RESET);
            }
            return;
        }
        InsertInNext(item, hash);

    }

    private void InsertInNext(Item item, int index) {
        String output = String.format("Place %s занято: переходимо до %s", index, index + 1);
        System.out.println(ANSI_RED + output);
        while (items[index] != null) {
            output = String.format("Place %s занято: переходимо до %s", index, index + 1);
            System.out.println(output);
            index++;
        }
        if (HowManyNotNull() > maxSize / 2) {
            System.out.println(ANSI_PURPLE + "УВАГА. Перевищено половину memory. Можливi затримки у обрахуваннях!");
        }

        items[index] = item;
        System.out.println();
        output = String.format("Successfull додано по iндексу: %s", index);
        System.out.println(ANSI_GREEN + output + ANSI_RESET);
    }

    public int GetHash(int value) {
        if (value == 0) {
            throw new IllegalArgumentException();
        }

        return value % maxSize;
    }

    public void ShowCurrentItem(int index) {
        String output = String.format("Item at index %s: \nKey: %s  \nValue: %s \n", index, items[index].Key,items[index].Value );
        System.out.println(ANSI_GREEN + output + ANSI_RESET);
    }

    public int HowManyNotNull() {
        int counter = 0;
        for (int i = 0; i < items.length; i++)
        {
            if (items[i] != null)
            {
                counter++;
            }
        }
        return counter;
    }
    public void Search(int key)
    {
        var index = GetHash(key);
        if (items[index].Key == key)
        {
            ShowCurrentItem(index);
            // return items[index];
        }
        else
        {
            while (index < items.length && (items[index] == null || (items[index].Key != key)))
            {
                index++;
            }
            if (index >= items.length)
            {
                String output = String.format(ANSI_BLUE + "Sorry. Key: %s not find!" + ANSI_RESET,key);
                System.out.println(output);
                return;
            }

            ShowCurrentItem(index);
        }
    }
}

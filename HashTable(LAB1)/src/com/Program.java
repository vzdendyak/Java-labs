package com;

import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        HastTable table = new HastTable();
        int itemCount = 60;
        Item[] items = new Item[itemCount];
        for (int i = 0; i < itemCount; i++) {
            String output = String.format("Value for item %s", i + 1);
            items[i] = new Item(i + 100, output);
            table.Insert(items[i]);
        }
        Item curItem = new Item(520, "MY TEXT");
        table.Insert(curItem);
        table.Insert(199, "Value99");
        table.Search(520);
        System.out.println(table.Count());
        table.Remove(520);
        System.out.println(table.Count());
        table.Search(520);
        table.Search(199);
        table.Insert(299, "Value909");
        table.Search(299);



    }
}

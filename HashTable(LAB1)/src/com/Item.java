package com;

public class Item {
    public int Key;
    public String Value;

    public Item(int key, String value) {
        if (key == 0 || value == null) {
            throw new IllegalArgumentException();
        }
        this.Key = key;
        this.Value = value;
    }

}

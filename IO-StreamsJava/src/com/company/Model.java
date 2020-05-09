package com.company;

public class Model implements Comparable<Model>{
    private int id;
    private double age;
    private int height;
    private String firstName;
    private String lastName;

    Model(int id, double age, int height, String name, String surname){
        this.id=id;
        this.age=age;
        this.height=height;
        this.firstName=name;
        this.lastName=surname;
    }

    public int getId() {
        return id;
    }

    public double getAge() {
        return age;
    }

    public int getHeight() {
        return height;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }


    @Override
    public int compareTo(Model model) {
        return this.id-model.id;
    }
}

package com.company;

import java.io.Console;

public class Vehicle implements  Comparable<Vehicle>, Runnable {
    int id;
    String color;
    int isMainRoad;
    int roadDirection; // 0-MU 1-MD  2-SU 3-SD

    Vehicle(int id, String color, int isMainRoad, int roadDirection){
        this.id=id;
        this.color=color;
        this.isMainRoad=isMainRoad;
        this.roadDirection=roadDirection;
    }

    @Override
    public int compareTo(Vehicle vehicle) {
        return vehicle.isMainRoad-this.isMainRoad;
    }

    @Override
    public void run() {
        try {
            switch (this.roadDirection){
                case 0:
                    Crossroad.MainRoadUp.add(this);
                    System.out.println(ConsoleColors.ANSI_GREEN + String.format("Додано на дорогу Main-UP(0) - автомобіль %d - напрям %d", this.id,this.roadDirection)+ ConsoleColors.ANSI_RESET );
                    this.isMainRoad=1;
                    break;
                case 1:
                    Crossroad.MainRoadDown.add(this);
                    System.out.println(ConsoleColors.ANSI_GREEN + String.format("Додано на дорогу Main-DOWN(1) - автомобіль %d - напрям %d", this.id,this.roadDirection)+ ConsoleColors.ANSI_RESET );
                    this.isMainRoad=1;
                    break;
                case 2:
                    Crossroad.SubRoadUp.add(this);
                    System.out.println(ConsoleColors.ANSI_GREEN + String.format("Додано на дорогу Sub-UP(2) - автомобіль %d - напрям %d", this.id,this.roadDirection)+ ConsoleColors.ANSI_RESET );
                    this.isMainRoad=0;
                    break;
                case 3:
                    Crossroad.SubRoadDown.add(this);
                    System.out.println(ConsoleColors.ANSI_GREEN + String.format("Додано на дорогу Sub-DOWN(3) - автомобіль %d - напрям %d", this.id,this.roadDirection)+ ConsoleColors.ANSI_RESET );
                    this.isMainRoad=0;
                    break;
            }
            Crossroad.CrossRoadVehicles.add(this);
            Crossroad.MoveNextVehicle();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //System.out.println(ConsoleColors.ANSI_PURPLE +  String.format("Автомобіль %d зник, %d", this.id,this.isMainRoad) + ConsoleColors.ANSI_RESET);

    }
}

package com.company;

import org.w3c.dom.ls.LSOutput;

import java.util.*;
import java.util.concurrent.PriorityBlockingQueue;

public class Crossroad {
    static Queue<Vehicle> MainRoadUp = new LinkedList<Vehicle>();
    static Queue<Vehicle> MainRoadDown = new LinkedList<Vehicle>();
    static Queue<Vehicle> SubRoadUp = new LinkedList<Vehicle>();
    static Queue<Vehicle> SubRoadDown = new LinkedList<Vehicle>();
    static PriorityQueue<Vehicle> CrossRoadVehicles = new PriorityQueue<Vehicle>();

    Crossroad() {

    }

    synchronized static public void MoveNextVehicle() throws InterruptedException {
        var veh = CrossRoadVehicles.poll();
        assert veh != null;
        System.out.println(ConsoleColors.ANSI_WHITE + "УВАГА  " + veh.id + ConsoleColors.ANSI_RESET);
        switch (veh.roadDirection) {
            case 0:
                System.out.println(ConsoleColors.ANSI_RED + String.format("Проїхав автомобіль %d Він був на дорозі  Main-UP ", Objects.requireNonNull(MainRoadUp.poll()).id) + ConsoleColors.ANSI_RESET);
                break;
            case 1:
                // System.out.println(ConsoleColors.ANSI_RED+ "Він був на дорозі  Main-DOWN " + Objects.requireNonNull(MainRoadDown.poll()).id+ ConsoleColors.ANSI_RESET);
                System.out.println(ConsoleColors.ANSI_RED + String.format("Проїхав автомобіль %d Він був на дорозі  Main-DOWN ", Objects.requireNonNull(MainRoadDown.poll()).id) + ConsoleColors.ANSI_RESET);
                break;
            case 2:
                //System.out.println(ConsoleColors.ANSI_RED+ "Він був на дорозі  Sub-UP " + Objects.requireNonNull(SubRoadUp.poll()).id+ ConsoleColors.ANSI_RESET);
                System.out.println(ConsoleColors.ANSI_RED + String.format("Проїхав автомобіль %d Він був на дорозі  Sub-UP ", Objects.requireNonNull(SubRoadUp.poll()).id) + ConsoleColors.ANSI_RESET);
                break;
            case 3:
                // System.out.println(ConsoleColors.ANSI_RED+ "Він був на дорозі  Sub-DOWN " + Objects.requireNonNull(SubRoadDown.poll()).id+ ConsoleColors.ANSI_RESET);
                System.out.println(ConsoleColors.ANSI_RED + String.format("Проїхав автомобіль %d Він був на дорозі  Sub-DOWN ", Objects.requireNonNull(SubRoadDown.poll()).id) + ConsoleColors.ANSI_RESET);
                break;
        }
    }
}


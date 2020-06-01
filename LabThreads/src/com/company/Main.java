package com.company;

import java.util.Date;
import java.util.Random;

public class Main {

    public static void main(String[] args) throws InterruptedException {
    	Thread[] ths = new Thread[]{
				new Thread(new Vehicle(1, "black", 1,1)),
				new Thread(new Vehicle(2, "black", 0,0)),
				new Thread(new Vehicle(3, "black", 0,2)),
				new Thread(new Vehicle(4, "black", 1,1)),
				new Thread(new Vehicle(5, "black", 1,0)),
				new Thread(new Vehicle(5, "black", 1,2)),
				new Thread(new Vehicle(5, "black", 1,3)),
		};

		for (int i = 0; i < ths.length; i++) {
			ths[i].start();
			Thread.sleep( Math.abs((i+1)*new Random().nextInt())%70 + 5);
		}
    }
}

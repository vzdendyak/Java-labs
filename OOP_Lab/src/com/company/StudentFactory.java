package com.company;

import java.util.Random;

public class StudentFactory {
    static Student newStudent(){
        Random rand  = new Random();
        Student student;
        int randNumber = rand.nextInt(7);
        switch (randNumber) {
            case 0:
                student = new Student_StudyGood_RestGood_EatGood();
                return student;
            case 1:
                student = new Student_StudyGood_RestGood_EatBad();
                return student;
            case 2:
                student = new Student_StudyGood_RestBad_EatGood();
                return student;
            case 3:
                student = new Student_StudyGood_RestBad_EatBad();
                return student;
            case 4:
                student = new Student_StudyBad_RestGood_EatGood();
                return student;
            case 5:
                student = new Student_StudyBad_RestGood_EatBad();
                return student;
            case 6:
                student = new Student_StudyBad_RestBad_EatGood();
                return student;
            case 7:
                student = new Student_StudyBad_RestBad_EatBad();
                return student;
            default:
                return  null;
        }
    }
}

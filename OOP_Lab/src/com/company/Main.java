package com.company;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        ArrayList<Student> students = new ArrayList<Student>();
        StudentHelper studentsHelper = new StudentHelper();

        for (int i = 0; i < 10; i++) {
            students.add(StudentFactory.newStudent());
        }

        for (int i = 0; i < 10; i++) {
            System.out.println("Студент номер " + (i + 1) + "!");
            students.get(i).Eat();
            students.get(i).Rest();
            students.get(i).Study();
            System.out.println("----------------------------");
        }

        System.out.println("######################################################\n\n\n");

        var uncleverStudent = new Student_StudyBad_RestGood_EatGood();
        var cleverStudent = new Student_StudyGood_RestGood_EatGood();

        studentsHelper.setHelperStudent(cleverStudent);

        studentsHelper.requestHelp(uncleverStudent);

    }
}

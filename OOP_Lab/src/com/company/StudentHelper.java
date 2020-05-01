package com.company;

import java.util.ArrayList;

interface IHelper{
    void ReadyHelp();
    void NeedHelp();
}

public class StudentHelper {
    Student studentNeedHelp;
    Student studentReadyHelp;
    ArrayList<Student> students = new ArrayList<Student>();

    StudentHelper(){
        for (int i = 0; i < 10; i++) {
            students.add(StudentFactory.newStudent());
        }
    }

    void showStudentMessages(){

    }
}

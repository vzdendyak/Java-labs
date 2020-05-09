package com.company;

interface IHelper {
    void Help();
}

public class StudentHelper implements IHelper {
    Student_StudyBad studentNeedHelp;
    Student_StudyGood studentReadyHelp;

    public void requestHelp(Student studentNeedHelp) {
        if (studentNeedHelp instanceof Student_StudyBad) {
            var tempStudentNeedHelp = (Student_StudyBad) studentNeedHelp;
            tempStudentNeedHelp.needYourHelp(this);
        } else {
            System.out.println("Helper: Цей студент мудрий і йому не потрібна допомога!");
        }
    }


    public void setHelperStudent(Student studentGiveHelp) {
        if (studentGiveHelp instanceof Student_StudyGood) {
            this.studentReadyHelp = (Student_StudyGood) studentGiveHelp;
            this.studentReadyHelp.readyToHelp();
        } else
            System.out.println("Helper: Цей студент не достатньо успішний шоб надати допомогу!");
    }

    @Override
    public void Help() {
        System.out.println("Неуспішний студент: Попросив про допомогу");
        if (this.studentReadyHelp != null) {
            studentReadyHelp.readyToHelp();
            System.out.println("Успішний студент: Допомогу надано!");
        } else {
            System.out.println("Helper: Мудрих студентів поки не знаємо...");
        }
    }
}

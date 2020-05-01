package com.company;

abstract class Student {
    abstract void Study();

    abstract void Rest();

    abstract void Eat();

}

abstract class Student_StudyGood extends Student {

    @Override
    void Study() {
        Studying.Good();
    }
}

abstract class Student_StudyBad extends Student {

    @Override
    void Study() {
        Studying.Bad();
    }
}

abstract class Student_StudyGood_RestGood extends Student_StudyGood {
    @Override
    void Rest() {
        Resting.Good();
    }
}

abstract class Student_StudyGood_RestBad extends Student_StudyGood {
    @Override
    void Rest() {
        Resting.Bad();
    }

}

abstract class Student_StudyBad_RestGood extends Student_StudyBad {
    @Override
    void Rest() {
        Resting.Good();
    }
}

abstract class Student_StudyBad_RestBad extends Student_StudyBad {
    @Override
    void Rest() {

        Resting.Bad();
    }
}

class Student_StudyGood_RestGood_EatGood extends Student_StudyGood_RestGood {
    @Override
    void Eat() {

        Eating.Good();
    }

    void readyToHelp(){
        System.out.println("Допомагаю!");
    }

}

class Student_StudyGood_RestGood_EatBad extends Student_StudyGood_RestGood {
    @Override
    void Eat() {

        Eating.Bad();
    }
}

class Student_StudyGood_RestBad_EatGood extends Student_StudyGood_RestBad {
    @Override
    void Eat() {

        Eating.Good();
    }

}

class Student_StudyGood_RestBad_EatBad extends Student_StudyGood_RestBad {
    @Override
    void Eat() {

        Eating.Bad();
    }
}

class Student_StudyBad_RestGood_EatGood extends Student_StudyBad_RestGood {
    @Override
    void Eat() {

        Eating.Good();
    }

    void needYourHelp(){
        System.out.println("Прошу про допомогу!");
    }
}

class Student_StudyBad_RestGood_EatBad extends Student_StudyBad_RestGood {
    @Override
    void Eat() {

        Eating.Bad();
    }
}

class Student_StudyBad_RestBad_EatGood extends Student_StudyBad_RestBad {
    @Override
    void Eat() {

        Eating.Good();
    }
}

class Student_StudyBad_RestBad_EatBad extends Student_StudyBad_RestBad {
    @Override
    void Eat() {

        Eating.Bad();
    }
}

class Studying {
    static void Good() {
        System.out.println("Study - GOOD");
    }

    static void Bad() {
        System.out.println("Study - BAD");
    }
}

class Resting {
    static void Good() {
        System.out.println("Rest - GOOD");
    }

    static void Bad() {
        System.out.println("Rest - BAD");
    }
}

class Eating {
    static void Good() {
        System.out.println("Eat - GOOD");
    }

    static void Bad() {
        System.out.println("Eat - BAD");
    }
}

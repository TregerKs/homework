package com.kseniya.homework;
public class Person {
    String fullName;
    int age;

    public Person(){};
    public Person(String fullName, int age) {
        this.fullName = fullName;
        this.age = age;
    }

    public void talk() {
        if (age == 0 & (fullName == null)) {
            System.out.println("Ничего, если имя и возраст не заданы");
        } else {
            System.out.println("Привет, я " + fullName + ", мне " + age + " лет");
        }
    }
}
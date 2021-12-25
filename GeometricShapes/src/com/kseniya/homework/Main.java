package com.kseniya.homework;

public class Main {

    public static void main(String[] args) {
	GeometricShapes shapes = new Triangle(12, 11);
        System.out.println(shapes.countArea());

        GeometricShapes shapes1 = new Square(2, 144);
        System.out.println(shapes1.countArea());

        GeometricShapes shapes2 = new Diamond(3, 5);
        System.out.println(shapes2.countArea());
    }
}

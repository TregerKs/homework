package com.kseniya.homework;

public class Triangle extends GeometricShapes {

    public Triangle(int length, int height) {
        super(length, height);
    }

    @Override
    public int countArea() {
        return height * length;
    }
}

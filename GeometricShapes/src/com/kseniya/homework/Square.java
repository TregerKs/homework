package com.kseniya.homework;

public class Square extends GeometricShapes {

    public Square(int length, int height) {
        super(length, height);
    }

    @Override
    public int countArea() {
        return height * length;
    }
}

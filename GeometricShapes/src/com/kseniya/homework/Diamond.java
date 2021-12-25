package com.kseniya.homework;

public class Diamond extends GeometricShapes {

    public Diamond(int length, int height) {
        super(length, height);
    }

    @Override
    public int countArea() {
        return height * length;
    }
}
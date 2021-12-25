package com.kseniya.homework;

public class GeometricShapes {
    protected int length;
    protected int height;
    protected int area; // формула от балды

    public GeometricShapes(int length, int height) {
        this.length = length;
        this.height = height;
    }

    public int countArea() {
        return area;
    }
}

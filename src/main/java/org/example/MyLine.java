package org.example;

import java.awt.Point;

public class MyLine {
    public Point p1;
    public Point p2;

    public MyLine(Point p1, Point p2) {
        this.p1 = p1;
        
        this.p2 = p2;
    }

    public String toString() {
        return "(" + this.p1.x + "," + this.p1.y + ") and (" + this.p2.x + "," + this.p2.y + ")";
    }
}

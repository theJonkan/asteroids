package se.liu.jonbj802;

import java.awt.*;

public class Asteroid implements EnemyObject
{
    private int size = 10;

    @Override public int getHealth() {
        return 0;
    }

    @Override public void setHealth() {

    }

    @Override public double getAngle() {
        return 0;
    }

    @Override public int getSize() {
        return size;
    }

    @Override public void setPos(final int x, final int y) {

    }

    @Override public int getX() {
        return 150;
    }

    @Override public int getY() {
        return 150;
    }

    @Override public double[][] getMatrix() {
        return new double[][] {
                { -1, 0, 0, 2, 2, 4, 4, 4, 4, 3, 3, 1, 1, 1, 1, -1, -1, -4, -4, -4, -4, -3, -3, -4, -4, -4, -4, -2, -2, -1},
                { 5, 4, 4, 4, 4, 2, 2, 0, 0, -3, -3, -2, -2, -4, -4, -4, -4, -2, -2, -1, -1, 0, 0, 1, 1, 2, 2, 2, 2, 5},
        };
    }
}

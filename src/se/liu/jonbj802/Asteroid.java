package se.liu.jonbj802;

import java.awt.*;
import java.util.Random;

public class Asteroid implements EnemyObject
{
    private final static Random RND = new Random();
    private int size;
    private double angle;
    private int x, y;

    public Asteroid(final Dimension screenSize) {
        this.size = RND.nextInt(3, 15);
        this.size = 20;

        final int sector = RND.nextInt(4);
        switch (sector) {
            case 0 -> {
                this.x = 0;
                this.y = RND.nextInt(screenSize.height);
                this.angle = RND.nextDouble(0, Math.PI);
            }
            case  1 -> {
                this.x = screenSize.width;
                this.y = RND.nextInt(screenSize.height);
                this.angle = RND.nextDouble(Math.PI, 2 * Math.PI);
            }
            case 2 -> {
                this.x = RND.nextInt(screenSize.width);
                this.y = 0;
                this.angle = RND.nextDouble(Math.PI / 2, 3.0/2 * Math.PI);
            }
            case 3 -> {
                this.x = RND.nextInt(screenSize.width);
                this.y = screenSize.height;
                this.angle = RND.nextDouble(3.0/2 * Math.PI, 5.0/2 * Math.PI);
            }
        }
    }

    @Override public int getHealth() {
        return 0;
    }

    @Override public void setHealth() {

    }

    @Override public double getAngle() {
        return angle;
    }

    @Override public int getSize() {
        return size;
    }

    @Override public void setPos(final int x, final int y) {

    }

    @Override public int getX() {
        return x;
    }

    @Override public int getY() {
        return y;
    }

    @Override public double[][] getMatrix() {
        return new double[][] {
                { -1, 0, 0, 2, 2, 4, 4, 4, 4, 3, 3, 1, 1, 1, 1, -1, -1, -4, -4, -4, -4, -3, -3, -4, -4, -4, -4, -2, -2, -1},
                { 5, 4, 4, 4, 4, 2, 2, 0, 0, -3, -3, -2, -2, -4, -4, -4, -4, -2, -2, -1, -1, 0, 0, 1, 1, 2, 2, 2, 2, 5},
        };
    }
}

package se.liu.jonbj802;

import java.awt.*;
import java.util.Random;

public class Asteroid extends AbstractEnemyObject
{
    private final static Random RND = new Random();
    private int size;
    private double speed;

    public Asteroid(final Dimension screenSize) {
        super(screenSize);
        this.size = RND.nextInt(4, 15);
        this.speed = 45.0 / size;
    }

    @Override public int getHealth() {
        return 0;
    }

    @Override public void setHealth() {

    }

    @Override public int getSize() {
        return size;
    }

    @Override public double[][] getMatrix() {
        return new double[][] {
                { -1, 0, 0, 2, 2, 4, 4, 4, 4, 3, 3, 1, 1, 1, 1, -1, -1, -4, -4, -4, -4, -3, -3, -4, -4, -4, -4, -2, -2, -1},
                { 5, 4, 4, 4, 4, 2, 2, 0, 0, -3, -3, -2, -2, -4, -4, -4, -4, -2, -2, -1, -1, 0, 0, 1, 1, 2, 2, 2, 2, 5},
        };
    }

    @Override public void update() {
        move(speed);
    }
}

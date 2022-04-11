package se.liu.jonbj802;

import java.awt.*;
import java.util.Random;

/**
 * Asteroid is an enemy that breaks into smaller chunks when hit.
 */
public class Asteroid extends AbstractEnemyObject
{
    private final static Random RND = new Random();
    private int size;
    private double speed;

    private final static int SMALLEST_ASTEROID = 4;
    private final static int BIGGEST_ASTEROID = 15;

    public Asteroid(final Dimension screenSize) {
        super(screenSize);
        this.size = RND.nextInt(SMALLEST_ASTEROID, BIGGEST_ASTEROID);
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

    @Override public Matrix getMatrix() {
        final Matrix matrix = new Matrix(new double[][] {
                { -1, 0, 0, 2, 2, 4, 4, 4, 4, 3, 3, 1, 1, 1, 1, -1, -1, -4, -4, -4, -4, -3, -3, -4, -4, -4, -4, -2, -2, -1},
                { 5, 4, 4, 4, 4, 2, 2, 0, 0, -3, -3, -2, -2, -4, -4, -4, -4, -2, -2, -1, -1, 0, 0, 1, 1, 2, 2, 2, 2, 5},
        });

        matrix.modify(size, angle);
        return matrix;
    }

    @Override public void update() {
        move(speed);
    }
}

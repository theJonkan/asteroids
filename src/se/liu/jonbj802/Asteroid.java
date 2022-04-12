package se.liu.jonbj802;

import java.awt.*;
import java.util.Random;

/**
 * Asteroid is an enemy that breaks into smaller chunks when hit.
 */
public class Asteroid extends AbstractEnemyObject
{
    private final static Random RND = new Random();
    private double speed;

    private final static int SMALLEST_ASTEROID = 4;
    private final static int BIGGEST_ASTEROID = 15;

    private Matrix matrix;
    private final static double[][] VECTORS = new double[][] {
            { -1, 0, 0, 2, 2, 4, 4, 4, 4, 3, 3, 1, 1, 1, 1, -1, -1, -4, -4, -4, -4, -3, -3, -4, -4, -4, -4, -2, -2, -1 },
            { 5, 4, 4, 4, 4, 2, 2, 0, 0, -3, -3, -2, -2, -4, -4, -4, -4, -2, -2, -1, -1, 0, 0, 1, 1, 2, 2, 2, 2, 5 },
    };

    public Asteroid(final Dimension screenSize) {
        super(screenSize, RND.nextInt(SMALLEST_ASTEROID, BIGGEST_ASTEROID));
        this.speed = 45.0 / size;
        this.matrix = new Matrix(VECTORS);
        this.matrix.modify(size, angle);
    }

    @Override public int getHealth() {
        return 0;
    }

    @Override public void setHealth() {

    }

    @Override public Matrix getMatrix() {
        return matrix;
    }

    @Override public void update() {
        move(speed);
    }
}

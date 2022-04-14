package se.liu.jonbj802;

import java.awt.*;

/**
 * Asteroid is an enemy that shoots towards the player.
 */
public class Saucer extends AbstractEnemyObject
{
    // TODO: Let saucer shoot bullets.

    private final static int SPEED = 5;
    private final static int SIZE = 4;

    private final Matrix matrix;
    private final static double[][] VECTORS = new double[][] {
	    {-2, 2, -3, 3, -6, 6, -4, 4, -2, -3, 2, 3, -3, -6, 3, 6, -6, -4, 6, 4},
	    {4, 4, 2, 2, 0, 0, -2, -2, 4, 2, 4, 2, 2, 0, 2, 0, 0, -2, 0, -2}
    };

    public Saucer(final Dimension screenSize) {
	super(screenSize, SIZE);
	matrix = new Matrix(VECTORS);
	matrix.modify(SIZE, 0);
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
	move(SPEED);
    }

    @Override public void collided() {

    }
}

package se.liu.jonbj802;

import java.awt.*;

/**
 * Bullets cause damage to enemies. It is spawned by the rocket and saucers.
 */
public class Bullet extends AbstractMoveableObject
{
    private final static int SIZE = 2;
    private final static int SPEED = 10;

    private final static int START_DISTANCE = 20;

    private final Matrix matrix;
    private final static double[][] VECTORS = new double[][] {
	    {1, 1, 1, -1, -1, -1, -1, 1},
	    {1, -1, -1, -1, -1, 1, 1, 1}
    };

    private final static int DELETION_DELAY = 100;
    private int frameCalls;

    public Bullet(final double angle, final int x, final int y) {
	super(new Point(x, y), angle, SIZE);
	matrix = new Matrix(VECTORS);
	matrix.modify(SIZE, angle);
	move(START_DISTANCE);
    }

    @Override public boolean shouldDespawn(final Dimension screenSize, final int offset) {
	return frameCalls >= DELETION_DELAY;
    }

    @Override public Matrix getMatrix() {
	return matrix;
    }

    @Override public void update() {
	move(SPEED);
	frameCalls++;
    }
}

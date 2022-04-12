package se.liu.jonbj802;

import java.awt.*;

/**
 * Bullets cause damage to enemies. It is spawned by the rocket and saucers.
 */
public class Bullet extends AbstractMoveableObject
{
    private final static int DEFAULT_SPEED = 7;
    private final static int SIZE = 2;
    private final static int START_DISTANCE = 40;

    private double speed;
    private boolean hasCollided;

    private final Matrix matrix;
    private final static double[][] VECTORS = new double[][] {
	    {1, 1, 1, -1, -1, -1, -1, 1},
	    {1, -1, -1, -1, -1, 1, 1, 1}
    };

    private final static int DELETION_DELAY = 100;
    private int frameCalls;

    public Bullet(final double angle, final int x, final int y, final double speed) {
	super(new Point(x, y), angle, SIZE);
	matrix = new Matrix(VECTORS);
	matrix.modify(SIZE, angle);
	this.speed = speed + DEFAULT_SPEED;
	move(START_DISTANCE);
    }

    @Override public boolean shouldDespawn(final Dimension screenSize, final int offset) {
	return frameCalls >= DELETION_DELAY || hasCollided;
    }

    @Override public Matrix getMatrix() {
	return matrix;
    }

    @Override public void update() {
	move(speed);
	frameCalls++;
    }

    @Override public void collided() {
	hasCollided = true;
    }
}

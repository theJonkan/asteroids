package se.liu.jonbj802;

import java.awt.*;

/**
 * Asteroid is an enemy that shoots towards the player.
 */
public class Saucer extends AbstractEnemyObject
{
    private final static int SIZE = 4;
    private final static int SPEED = 5;

    public Saucer(final Dimension screenSize) {
	super(screenSize);
    }

    @Override public int getHealth() {
	return 0;
    }

    @Override public void setHealth() {

    }

    @Override public double getAngle() {
	return 0;
    }

    @Override public int getSize() {
	return SIZE;
    }

    @Override public double[][] getMatrix() {
	return new double[][] {
		{-2, 2, -3, 3, -6, 6, -4, 4, -2, -3, 2, 3, -3, -6, 3, 6, -6, -4, 6, 4},
		{4, 4, 2, 2, 0, 0, -2, -2, 4, 2, 4, 2, 2, 0, 2, 0, 0, -2, 0, -2}
	};
    }

    @Override public void update() {
	move(SPEED);
    }
}

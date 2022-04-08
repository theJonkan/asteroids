package se.liu.jonbj802;

import java.awt.*;

/**
 * Bullets cause damage to enemies. It is spawned by the rocket and saucers.
 */
public class Bullet implements MoveableObject
{
    private final static int SIZE = 2;
    private final static int SPEED = 10;
    private final double angle;
    private Point pos;

    private final static int START_DISTANCE = 20;

    private final static int DELETION_DELAY = 100;
    private int frameCalls;

    public Bullet(final double angle, final int x, final int y) {
	this.angle = angle;
	this.pos = new Point(x, y);
	move(START_DISTANCE);
    }

    @Override public double getAngle() {
	return angle;
    }

    @Override public int getSize() {
	return SIZE;
    }

    @Override public Point getPos() {
	return pos;
    }

    @Override public void setPos(final int x, final int y) {
	pos.x = x;
	pos.y = y;
    }

    @Override public boolean shouldDespawn(final Dimension screenSize, final int offset) {
	return frameCalls >= DELETION_DELAY;
    }

    @Override public boolean shouldWrap(final Dimension screenSize, final int offset) {
	return true;
    }

    private void move(final double distance){
	pos.x += (int)Math.round(Math.cos(angle) * distance);
	pos.y += (int)Math.round(Math.sin(angle) * distance);
    }

    @Override public double[][] getMatrix() {
	return new double[][] {
		{-1, 1},
		{0, 0}
	};
    }

    @Override public void update() {
	move(SPEED);
	frameCalls++;
    }
}

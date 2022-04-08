package se.liu.jonbj802;

/**
 * Bullets cause damage to enemies.
 */
public class Bullet implements MoveableObject
{
    private final static int SIZE = 2;
    private final static int SPEED = 10;
    private final double angle;
    private int x, y;

    private final static int START_DISTANCE = 20;

    // TODO: Remove with timer.

    public Bullet(final double angle, final int x, final int y) {
	this.angle = angle;
	this.x = x;
	this.y = y;
	move(START_DISTANCE);
    }

    @Override public double getAngle() {
	return angle;
    }

    @Override public int getSize() {
	return SIZE;
    }

    @Override public int getX() {
	return x;
    }

    @Override public int getY() {
	return y;
    }

    @Override public void setPos(final int x, final int y) {
	this.x = x;
	this.y = y;
    }

    private void move(final double distance){
	x += (int)Math.round(Math.cos(angle) * distance);
	y += (int)Math.round(Math.sin(angle) * distance);
    }


    @Override public double[][] getMatrix() {
	return new double[][] {
		{-1, 1},
		{0, 0}
	};
    }

    @Override public void update() {
	move(SPEED);
    }
}

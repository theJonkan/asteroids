package se.liu.jonbj802;

public class Bullet implements MoveableObject
{
    private final static int SIZE = 2;
    private final static int SPEED = 10;
    private final double angle;
    private int x, y;

    // TODO: Wrap around at the edge and remove with timer.

    public Bullet(final double angle, final int x, final int y) {
	this.angle = angle;
	this.x = x;
	this.y = y;
	move(20);
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

    private void move(final double speed){
	x += (int)Math.round(Math.cos(angle) * speed);
	y += (int)Math.round(Math.sin(angle) * speed);
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

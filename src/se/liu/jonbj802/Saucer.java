package se.liu.jonbj802;

public class Saucer implements EnemyObject
{
    @Override public int getHealth() {
	return 0;
    }

    @Override public void setHealth() {

    }

    @Override public double getAngle() {
	return 0;
    }

    @Override public int getSize() {
	return 4;
    }

    @Override public void setPos(final int x, final int y) {

    }

    @Override public int getX() {
	return 250;
    }

    @Override public int getY() {
	return 250;
    }

    @Override public double[][] getMatrix() {
	return new double[][] {
		{-2, 2, -3, 3, -6, 6, -4, 4, -2, -3, 2, 3, -3, -6, 3, 6, -6, -4, 6, 4},
		{4, 4, 2, 2, 0, 0, -2, -2, 4, 2, 4, 2, 2, 0, 2, 0, 0, -2, 0, -2}
	};
    }
}

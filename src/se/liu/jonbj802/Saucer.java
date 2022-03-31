package se.liu.jonbj802;

import java.awt.*;
import java.util.Random;

public class Saucer implements EnemyObject
{
    private final static Random RND = new Random();
    private final static int SIZE = 4;


    private double angle;
    private int x, y;

    public Saucer(final Dimension screenSize) {
	final int sector = RND.nextInt(4);
	switch (sector) {
	    case 0 -> {
		this.x = 0;
		this.y = RND.nextInt(screenSize.height);
		this.angle = RND.nextDouble(0, Math.PI);
	    }
	    case  1 -> {
		this.x = screenSize.width;
		this.y = RND.nextInt(screenSize.height);
		this.angle = RND.nextDouble(Math.PI, 2 * Math.PI);
	    }
	    case 2 -> {
		this.x = RND.nextInt(screenSize.width);
		this.y = 0;
		this.angle = RND.nextDouble(Math.PI / 2, 3.0/2 * Math.PI);
	    }
	    case 3 -> {
		this.x = RND.nextInt(screenSize.width);
		this.y = screenSize.height;
		this.angle = RND.nextDouble(3.0/2 * Math.PI, 5.0/2 * Math.PI);
	    }
	}

	x = 250;
	y = 250;
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

    @Override public void setPos(final int x, final int y) {

    }

    @Override public int getX() {
	return x;
    }

    @Override public int getY() {
	return y;
    }

    @Override public double[][] getMatrix() {
	return new double[][] {
		{-2, 2, -3, 3, -6, 6, -4, 4, -2, -3, 2, 3, -3, -6, 3, 6, -6, -4, 6, 4},
		{4, 4, 2, 2, 0, 0, -2, -2, 4, 2, 4, 2, 2, 0, 2, 0, 0, -2, 0, -2}
	};
    }
}

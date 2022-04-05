package se.liu.jonbj802;

import com.google.gson.internal.bind.util.ISO8601Utils;

import java.awt.*;
import java.util.Random;

public abstract class AbstractEnemyObject implements EnemyObject
{
    private final static Random RND = new Random();
    protected int x, y;
    protected double angle;

    protected AbstractEnemyObject(final Dimension screenSize) {
        generateRandomPosition(screenSize);
    }

    @Override public int getX() {
        return x;
    }

    @Override public int getY() {
        return y;
    }

    @Override public double getAngle() {
        return angle;
    }

    protected void move(final double speed){
        x += (int)Math.round(Math.cos(angle) * speed);
        y += (int)Math.round(Math.sin(angle) * speed);
    }

    protected void generateRandomPosition(final Dimension screenSize) {
        final int sector = RND.nextInt(4);
        switch (sector) {
            case 0 -> {
                this.x = 0;
                this.y = RND.nextInt(screenSize.height);
                this.angle = RND.nextDouble(-1.0 / 2, 1.0/2);
            }
            case  1 -> {
                this.x = screenSize.width;
                this.y = RND.nextInt(screenSize.height);
                this.angle = RND.nextDouble(1.0/2, 3.0/2);
            }
            case 2 -> {
                this.x = RND.nextInt(screenSize.width);
                this.y = 0;
                this.angle = RND.nextDouble(1);
            }
            case 3 -> {
                this.x = RND.nextInt(screenSize.width);
                this.y = screenSize.height;
                this.angle = RND.nextDouble(1, 2);
            }
        }
        this.angle *= Math.PI;

        //System.out.printf("Sector %d: (%d, %d) at %f degrees\n", sector, x, y, angle/Math.PI);
    }
}

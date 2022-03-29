package se.liu.jonbj802;

import java.awt.*;

public class Rocket implements MoveableObject
{
    private boolean flying = true;
    private final static int SIZE = 5;
    private int speed = 5;
    private int x = 200, y = 200;
    private double angle;
    private final static double ANGEL_CHANGE = 0.2;

    public void rotate(Direction direction){

        if (direction == Direction.RIGHT) {
            if (angle >= 2*Math.PI) {
                angle -= 2*Math.PI;
            }
            angle -= ANGEL_CHANGE;
        } else {
            if (angle <= 0) {
                angle = 2*Math.PI + angle;
            }
            angle += ANGEL_CHANGE;
        }
    }

    public double getAngle(){
        return angle;
    }
    
    @Override public int getSize() {
        return SIZE;
    }

    @Override public void setPos(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    @Override public int getX() {
        return x;
    }

    @Override public int getY() {
        return y;
    }

    @Override public double[][] getMatrix() {
        if (flying) {
            return new double[][] {
                    { -3, 0, 3, 0, -2, 2, 0, -1, 0, 1},
                    { -3, 5, -3, 5, -1, -1, -4, -1, -4, -1},
            };
        }

        return new double[][] { { -3, 0, 3, 0, -2, 2 }, { -3, 5, -3, 5, -1, -1 }, };
    }
}

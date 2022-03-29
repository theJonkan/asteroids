package se.liu.jonbj802;

import java.awt.*;

public class Rocket implements MoveableObject
{
    private boolean flying;
    private final static int SIZE = 5;
    private int speed = 5;
    private int x = 200, y = 200;
    private int angle;

    public void rotate(Direction direction){

        if (direction == Direction.RIGHT) {
            if (angle >= 360) {
                angle = 0;
            }
            angle += 1;
        } else {
            if (angle <= 0) {
                angle = 360;
            }
            angle -= 1;
        }
    }

    public int getAngle(){
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

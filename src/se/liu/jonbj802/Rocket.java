package se.liu.jonbj802;

import java.awt.*;

public class Rocket implements MoveableObject
{
    private boolean flying, rotating;
    private Direction direction = null;
    private final static int SIZE = 5;
    private int speed = 8;
    private int x = 200, y = 200;
    private double angle = Math.PI / 2;
    private final static double ANGEL_CHANGE = 0.1;

    private void rotate(){

        if (direction == Direction.RIGHT) {
            if (angle >= 2*Math.PI) {
                angle -= 2*Math.PI;
            }
            angle -= ANGEL_CHANGE;
        } else {
            if (angle <= 0) {
                angle += 2*Math.PI;
            }
            angle += ANGEL_CHANGE;
        }
    }

    private void move(){
        x += (int)Math.round(Math.cos(angle) * SIZE);
        y += (int)Math.round(Math.sin(angle) * SIZE);
    }

    public double getAngle(){
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

    @Override public double[][] getMatrix() {
        if (flying) {
            return new double[][] {
                    { -3, 5, -3, 5, -1, -1, -4, -1, -4, -1},
                    { -3, 0, 3, 0, -2, 2, 0, -1, 0, 1},
            };
        }

        return new double[][] {
                { -3, 5, -3, 5, -1, -1 },
                { -3, 0, 3, 0, -2, 2 },
        };
    }

    @Override public void update() {
        if (rotating){
            rotate();
        }

        if (flying){
            move();
        }
    }

    public void upRelease() {
        flying = false;
    }

    public void leftRightRelease() {
        rotating = false;
        direction = null;
    }

    public void leftRightPress(final Direction direction) {
        rotating = true;
        this.direction = direction;
    }

    public void upPress() {
        flying = true;
    }
}

package se.liu.jonbj802;

import java.awt.*;

/**
 * Rocket is the player controlled object. It can shoot, fly and rotate in space.
 */
public class Rocket implements MoveableObject
{
    private final static int SIZE = 5;
    private final static double ANGEL_CHANGE = 0.1;
    private final static double ACCELERATION_INERTIA = 0.2;
    private final static double RETARDATION_INERTIA = 0.07;
    private final static int DEFAULT_SHOOTING_DELAY = 10;
    private final static int MAX_SPEED = 12;

    private double speed;
    private Point pos;
    private double angle = Math.PI / 2;
    private double movementAngle = angle;

    private boolean flying, rotating;
    private Direction direction = null;

    private int shootingDelay;

    public Rocket(final Dimension screenSize) {
        this.pos = new Point(screenSize.width / 2, screenSize.height / 2);
    }

    private void rotate(){
        if (direction == Direction.RIGHT) {
            angle -= ANGEL_CHANGE;
        } else {
            angle += ANGEL_CHANGE;
        }

        angle %= 2*Math.PI;
    }

    private void move(){
        pos.x += (int)Math.round(Math.cos(movementAngle) * speed);
        pos.y += (int)Math.round(Math.sin(movementAngle) * speed);
    }

    private void updateSpeed() {
        final double angleDiff = Math.abs(movementAngle - angle);
        if (flying) {
            if (angleDiff < Math.PI / 3) {
                speed = Math.min(MAX_SPEED, speed + ACCELERATION_INERTIA);
            } else {
                speed = Math.max(0, speed - angleDiff);
            }
        } else {
            speed = Math.max(0, speed - RETARDATION_INERTIA);
        }
    }

    private void updateAngle() {
        if (flying) {
            movementAngle = angle;
        }
    }

    public double getAngle(){
        return angle;
    }

    public void setPos(final int x, final int y) {
        pos.x = x;
        pos.y = y;
    }

    @Override public boolean shouldDespawn(final Dimension screenSize, final int offset) {
        return false;
    }

    @Override public boolean shouldWrap(final Dimension screenSize, final int offset) {
        return pos.x > screenSize.width + offset || pos.y > screenSize.height + offset || pos.x < -offset || pos.y < -offset;
    }

    @Override public int getSize() {
        return SIZE;
    }

    @Override public Point getPos() {
        return pos;
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

        updateSpeed();
        updateAngle();

        move();

        shootingDelay--;
    }

    public Bullet shoot() {
        if (shootingDelay <= 0) {
            shootingDelay = DEFAULT_SHOOTING_DELAY;
            return new Bullet(angle, pos.x, pos.y);
        }

        return null;
    }

    public void setRotation(final boolean release, final Direction direction) {
        this.rotating = !release;
        this.direction = direction;
    }

    public void setMovement(final boolean release) {
        flying = !release;
    }
}

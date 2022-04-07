package se.liu.jonbj802;

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

    private double speed = 5;
    private int x = 200, y = 200; // Middle of space (center of screen)?
    private double angle = Math.PI / 2;
    private double movementAngle = angle;

    private boolean flying, rotating;
    private Direction direction = null;

    private int shootingDelay;

    private void rotate(){
        if (direction == Direction.RIGHT) {
            angle -= ANGEL_CHANGE;
        } else {
            angle += ANGEL_CHANGE;
        }

        angle %= 2*Math.PI;
    }

    private void move(){
        x += (int)Math.round(Math.cos(movementAngle) * speed);
        y += (int)Math.round(Math.sin(movementAngle) * speed);
    }

    private void updateSpeed() {
        if (flying) {
            speed = Math.min(MAX_SPEED, speed + ACCELERATION_INERTIA);
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
        updateAngle();
        updateSpeed();

        if (rotating){
            rotate();
        }

        move();

        shootingDelay--;
    }

    public Bullet shoot() {
        if (shootingDelay <= 0) {
            shootingDelay = DEFAULT_SHOOTING_DELAY;
            return new Bullet(angle, x, y);
        }

        return null;
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

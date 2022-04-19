package se.liu.jonbj802;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Rocket is the player controlled object. It can shoot, fly and rotate in space.
 */
public class Rocket extends AbstractMoveableObject
{
    private final static int SIZE = 5;
    private final static double ANGEL_CHANGE = 0.1;

    private final static double ACCELERATION_INERTIA = 0.2;
    private final static double RETARDATION_INERTIA = 0.07;

    private final static int DEFAULT_SHOOTING_DELAY = 10;
    private final static int DEFAULT_RESPAWN_DELAY = 100;

    private final static int MAX_SPEED = 12;

    private final Dimension screenSize;

    private double speed;
    private double movementAngle = angle;
    private boolean flying, rotating;
    private Direction direction = null;
    private int score;
    private int health = 3;
    private SpawnListener spawner;

    private int shootingDelay;
    private int respawnDelay;

    // TODO: Fix holding down spacebar with "shooting state".
    // TODO: Should we move these out to separate files? Yes.
    private final static double[][] FLYING_VECTORS = new double[][] {
            { -3, 5, -3, 5, -1, -1, -4, -1, -4, -1},
            { -3, 0, 3, 0, -2, 2, 0, -1, 0, 1},
    };

    private final static double[][] DRIFTING_VECTORS = new double[][] {
            { -3, 5, -3, 5, -1, -1 },
            { -3, 0, 3, 0, -2, 2 },
    };

    public Rocket(final Dimension screenSize, final SpawnListener spawner) {
        super(new Point(screenSize.width / 2, screenSize.height / 2), Math.PI / 2, SIZE);
        this.screenSize = screenSize;
        this.spawner = spawner;
    }

    private void rotate(){
        if (direction == Direction.RIGHT) {
            angle -= ANGEL_CHANGE;
        } else {
            angle += ANGEL_CHANGE;
        }

        angle %= 2*Math.PI;
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

    @Override public boolean shouldDespawn(final Dimension screenSize, final int offset) {
        return health < 1;
    }

    @Override public boolean shouldWrap(final Dimension screenSize, final int offset) {
        return pos.x > screenSize.width + offset || pos.y > screenSize.height + offset || pos.x < -offset || pos.y < -offset;
    }

    @Override public Matrix getMatrix() {
        final double[][] vectors;

        if (flying) {
            vectors = FLYING_VECTORS;
        } else {
            vectors = DRIFTING_VECTORS;
        }

        Matrix matrix = new Matrix(vectors);
        matrix.modify(SIZE, angle);
        return matrix;
    }

    @Override public void update() {
        if (rotating){
            rotate();
        }

        updateSpeed();
        updateAngle();

        move(speed);

        shootingDelay--;
        respawnDelay--;
    }

    @Override public void collided() {
        damage();
        respawnDelay = 0;
    }

    @Override public CollisionType getCollisionType() {
        return CollisionType.ROCKET;
    }

    @Override protected void move(final double distance){
        pos.x += (int)Math.round(Math.cos(movementAngle) * distance);
        pos.y += (int)Math.round(Math.sin(movementAngle) * distance);
    }

    public void shoot() {
        if (shootingDelay > 0) {
            return;
        }

        shootingDelay = DEFAULT_SHOOTING_DELAY;
        final List<MoveableObject> list = new ArrayList<>();
        list.add(new Bullet(angle, pos.x, pos.y, speed, false));
        spawner.spawn(list);
    }

    public void setRotation(final boolean release, final Direction direction) {
        this.rotating = !release;
        this.direction = direction;
    }

    public void setMovement(final boolean release) {
        flying = !release;
    }

    public int getScore(){
        return score;
    }

    public void increaseScore(int increment){
        score += increment;
    }

    public int getHealth(){
        return health;
    }

    public void damage(){
        if (respawnDelay > 0) {
            return;
        }

        health--;

        respawnDelay = DEFAULT_RESPAWN_DELAY;
        setPos(screenSize.width / 2, screenSize.height / 2);
    }
}

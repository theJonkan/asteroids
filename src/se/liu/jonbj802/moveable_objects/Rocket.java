package se.liu.jonbj802.moveable_objects;

import se.liu.jonbj802.SpawnListener;
import se.liu.jonbj802.collisions.CollisionHandler;
import se.liu.jonbj802.collisions.CollisionType;
import se.liu.jonbj802.graphics.FileHandler;
import se.liu.jonbj802.graphics.Matrix;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Rocket is the player controlled object. It can shoot, fly and rotate in space.
 */
public class Rocket extends AbstractMoveableObject implements KeyListener
{
    private final static int SIZE = 5;
    private final static double ANGEL_CHANGE = 0.1;

    private final static int MAX_SPEED = 12;
    private final static double ACCELERATION_INERTIA = 0.2;
    private final static double RETARDATION_INERTIA = 0.07;

    private final static int DEFAULT_SHOOTING_DELAY = 10;
    private final static int DEFAULT_RESPAWN_DELAY = 100;
    private final static int DEFAULT_HEALTH = 3;
    private final static double DEFAULT_ANGLE = Math.PI / 2;

    private final Dimension screenSize;

    private double speed;
    private double movementAngle = angle;
    private boolean flying, rotating, shooting;
    private Direction direction = null;
    private int score;
    private int health = DEFAULT_HEALTH;
    private SpawnListener spawner;
    private CollisionHandler collisions;



    private int shootingDelay;
    private int respawnDelay;

    public Rocket(final Dimension screenSize, final SpawnListener spawner, final FileHandler fileHandler, final CollisionHandler collisions) {
        super(new Point(screenSize.width / 2, screenSize.height / 2), DEFAULT_ANGLE, SIZE, fileHandler);
        this.screenSize = screenSize;
        this.spawner = spawner;
        this.collisions = collisions;
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

    private void shoot() {
        if (shootingDelay > 0) {
            return;
        }

        shootingDelay = DEFAULT_SHOOTING_DELAY;
        spawner.spawn(new Bullet(angle, pos.x, pos.y, speed, false, fileHandler));
    }

    @Override public boolean shouldDespawn(final Dimension screenSize, final int offset) {
        return health < 1;
    }

    @Override public boolean shouldWrap(final Dimension screenSize, final int offset) {
        return pos.x > screenSize.width + offset || pos.y > screenSize.height + offset || pos.x < -offset || pos.y < -offset;
    }

    @Override public Matrix getMatrix() {
        if (flying) {
            return fileHandler.get("rocket_flying").modify(SIZE, angle);
        }

        return fileHandler.get("rocket_drifting").modify(SIZE, angle);
    }

    @Override public void update() {
        if (rotating){
            rotate();
        }

        if (shooting) {
            shoot();
        }

        updateSpeed();
        updateAngle();

        move(speed);

        shootingDelay--;
        respawnDelay--;

        collisions.setEnabled(respawnDelay <= 0);
    }

    @Override public void collided() {
        if (respawnDelay > 0) {
            return;
        }

        setPos(screenSize.width / 2, screenSize.height / 2);

        respawnDelay = DEFAULT_RESPAWN_DELAY;
        movementAngle = DEFAULT_ANGLE;
        angle = DEFAULT_ANGLE;
        shootingDelay = 0;
        speed = 0;

        health--;
    }

    @Override public CollisionType getCollisionType() {
        return CollisionType.ROCKET;
    }

    @Override protected void move(final double distance){
        pos.x += (int)Math.round(Math.cos(movementAngle) * distance);
        pos.y += (int)Math.round(Math.sin(movementAngle) * distance);
    }

    public void setShooting(final boolean release) {
        this.shooting = !release;
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

    public void setHealth(final int health){
        this.health = health;
    }

    public int getHealth(){
        return health;
    }

    @Override public void keyTyped(final KeyEvent e) {}

    private void handleKey(final boolean released, final KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP, KeyEvent.VK_W  -> setMovement(released);
            case KeyEvent.VK_LEFT, KeyEvent.VK_A -> setRotation(released, Direction.LEFT);
            case KeyEvent.VK_RIGHT, KeyEvent.VK_D  -> setRotation(released, Direction.RIGHT);
            case KeyEvent.VK_SPACE -> setShooting(released);
        }
    }

    @Override public void keyPressed(final KeyEvent e) {
        handleKey(false, e);
    }

    @Override public void keyReleased(final KeyEvent e) {
        handleKey(true, e);
    }
}

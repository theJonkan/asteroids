package se.liu.jonbj802.moveable_objects;

import se.liu.jonbj802.collisions.CollisionType;
import se.liu.jonbj802.graphics.FileHandler;
import se.liu.jonbj802.graphics.Matrix;

import java.awt.*;

/**
 * Bullets cause damage to enemies. It is spawned by the rocket and saucers.
 */
public class Bullet extends AbstractMoveableObject
{
    private final static int DEFAULT_SPEED = 7;
    private final static int SIZE = 2;
    private final static int START_DISTANCE = 40;

    private double speed;
    private boolean hasCollided;
    private boolean fromEnemy;

    private final Matrix matrix;

    private final static int DELETION_DELAY = 100;
    private int frameCalls;

    public Bullet(final double angle, final int x, final int y, final double speed, final boolean fromEnemy, final FileHandler fileHandler) {
	super(new Point(x, y), angle, SIZE, fileHandler);
	this.matrix = fileHandler.get("bullet").modify(SIZE, angle);
	this.speed = speed + DEFAULT_SPEED;
	this.fromEnemy = fromEnemy;
	move(START_DISTANCE);
    }

    @Override public boolean shouldDespawn(final Dimension screenSize, final int offset) {
	return frameCalls >= DELETION_DELAY || hasCollided;
    }

    @Override public Matrix getMatrix() {
	return matrix;
    }

    @Override public CollisionType getCollisionType() {
	if (fromEnemy) {
	    return CollisionType.BULLET_ENEMY;
	}

	return CollisionType.BULLET_PLAYER;
    }

    @Override public void update() {
	move(speed);
	frameCalls++;
    }

    @Override public void collided() {
	hasCollided = true;
    }
}

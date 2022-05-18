package se.liu.jonbj802.moveable_objects.enemy_objects;

import se.liu.jonbj802.SpawnListener;
import se.liu.jonbj802.collisions.CollisionType;
import se.liu.jonbj802.graphics.FileHandler;
import se.liu.jonbj802.graphics.Matrix;
import se.liu.jonbj802.moveable_objects.Bullet;
import se.liu.jonbj802.moveable_objects.Rocket;

import java.awt.*;

/**
 * Saucer is an enemy that moves across the space and shoots towards the player.
 */
public class Saucer extends AbstractEnemyObject
{
    private final static int SPEED = 5;
    private final static int SIZE = 4;
    private final static int DEFAULT_SHOOTING_DELAY = 20;

    private boolean hasCollided;
    private int shootingDelay;
    private final Rocket rocket;
    private final SpawnListener spawner;

    public Saucer(final Dimension screenSize, final Rocket rocket, final SpawnListener spawner, final FileHandler fileHandler) {
	super(screenSize, SIZE, fileHandler);
	this.rocket = rocket;
	this.spawner = spawner;
    }

    @Override public Matrix getMatrix() {
	return fileHandler.get("saucer", SIZE, 0);
    }

    @Override public void update() {
	move(SPEED);

	shoot();

	shootingDelay--;
    }

    @Override public void collided() {
	hasCollided = true;
    }

    @Override public boolean shouldDespawn(final Dimension screenSize, final int offset) {
	return hasCollided || super.shouldDespawn(screenSize, offset);
    }

    public void shoot() {
	if (shootingDelay > 0) {
	    return;
	}

	final double deltaX = pos.x - rocket.getPos().x;
	final double deltaY = pos.y - rocket.getPos().y;

	double angleToRocket = Math.atan(deltaY / deltaX);

	if (deltaX > 0) {
	    angleToRocket -= Math.PI;
	}

	shootingDelay = DEFAULT_SHOOTING_DELAY;
	spawner.spawn(new Bullet(angleToRocket, pos.x, pos.y, 0, true, fileHandler));
    }

    @Override public CollisionType getCollisionType() {
	return CollisionType.SAUCER;
    }
}

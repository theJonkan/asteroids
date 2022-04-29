package se.liu.jonbj802.moveable_objects;

import se.liu.jonbj802.SpawnListener;
import se.liu.jonbj802.collisions.CollisionType;
import se.liu.jonbj802.graphics.FileHandler;
import se.liu.jonbj802.graphics.Matrix;

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
    private final Rocket rocketPointer;
    private SpawnListener spawner;

    private final Matrix matrix;
    private final Dimension screenSize;

    public Saucer(final Dimension screenSize, final Rocket rocketPointer, final SpawnListener spawner, final FileHandler fileHandler) {
	super(screenSize, SIZE, fileHandler);
	matrix = fileHandler.get("saucer").modify(SIZE, 0);

	this.screenSize = screenSize;
	this.rocketPointer = rocketPointer;
	this.spawner = spawner;
    }

    @Override public Matrix getMatrix() {
	return matrix;
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

	double deltaX = (pos.x - (screenSize.getHeight() / 2)) - (rocketPointer.getPos().x - (screenSize.getHeight() / 2));
	double deltaY =  (pos.y - (screenSize.getWidth() / 2)) - (rocketPointer.getPos().y - (screenSize.getWidth() / 2)) ;

	double angleToRocket = Math.atan((double)deltaY/deltaX);

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

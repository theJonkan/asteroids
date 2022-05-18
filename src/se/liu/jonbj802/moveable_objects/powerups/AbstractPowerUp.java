package se.liu.jonbj802.moveable_objects.powerups;

import se.liu.jonbj802.collisions.CollisionType;
import se.liu.jonbj802.graphics.FileHandler;
import se.liu.jonbj802.moveable_objects.AbstractMoveableObject;
import se.liu.jonbj802.moveable_objects.Rocket;

import java.awt.*;
import java.util.Random;

/**
 * AbstractPowerUp contains common code for creating PowerUps.
 */
public abstract class AbstractPowerUp extends AbstractMoveableObject
{
    private final static int FPS = 50;
    private final static int DEFAULT_DESPAWN_DELAY = FPS * 7;

    private final static Random RND = new Random();
    private int despawnDelay;
    protected final Rocket rocket;
    private boolean hasCollided = false;

    protected AbstractPowerUp(final Dimension screenSize, final Rocket rocket, final FileHandler fileHandler)
    {
	super(new Point(), 0, 4, fileHandler);
	this.rocket = rocket;
	generateRandomPosition(screenSize);
    }

    private void generateRandomPosition(final Dimension screenSize) {
	final int posX = RND.nextInt(screenSize.width);
	final int posY = RND.nextInt(screenSize.height);
	setPos(posX, posY);
    }

    @Override public boolean shouldDespawn(final Dimension screenSize, final int offset) {
	return despawnDelay > DEFAULT_DESPAWN_DELAY || hasCollided;
    }

    @Override public void update() {
	despawnDelay++;
    }

    @Override public void collided() {
	hasCollided = true;
    }

    @Override public CollisionType getCollisionType() {
	return CollisionType.POWERUP;
    }
}

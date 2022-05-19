package se.liu.jonbj802.moveable_objects.enemy_objects;

import se.liu.jonbj802.SpawnListener;
import se.liu.jonbj802.collisions.CollisionType;
import se.liu.jonbj802.graphics.FileHandler;
import se.liu.jonbj802.graphics.Matrix;

import java.awt.*;

/**
 * Asteroid is an enemy that breaks into smaller chunks when hit.
 */
public class Asteroid extends AbstractEnemyObject
{
    private double speed;

    // The fields TYPES=3 and SIZE_DECREASE=2 are not sequential constants.

    private final static int TYPES = 3;
    private final static int SMALLEST_ASTEROID = 6;
    private final static int BIGGEST_ASTEROID = 15;

    private final static int POS_SEPARATION = 10;
    private final static double ANGLE_SEPARATION = 0.3;
    private final static int SIZE_DECREASE = 2;

    private boolean hasCollided;

    private Matrix matrix;

    public Asteroid(final Dimension screenSize, final SpawnListener spawner, final FileHandler fileHandler) {
	super(screenSize, RND.nextInt(SMALLEST_ASTEROID, BIGGEST_ASTEROID), spawner, fileHandler);
	init(spawner);
    }

    private Asteroid(final Point pos, final int size, final double angle, final SpawnListener spawner, final FileHandler fileHandler) {
	super(pos, size, angle, spawner, fileHandler);
	init(spawner);
    }

    private void init(final SpawnListener spawner) {
	this.speed = 45.0 / size;
	final int type = RND.nextInt(TYPES);
	this.matrix = fileHandler.get("asteroid_type_" + type).modify(size, angle);
    }

    @Override public Matrix getMatrix() {
	return matrix;
    }

    @Override public void update() {
	move(speed);
    }

    @Override public void collided() {
	super.collided();

	final int nextSize = size - SIZE_DECREASE;
	if (size - SMALLEST_ASTEROID <= 0 || nextSize < 0) {
	    return;
	}

	final Point pos1 = new Point(pos.x - POS_SEPARATION, pos.y - POS_SEPARATION);
	final Asteroid asteroid1 = new Asteroid(pos1, size - SIZE_DECREASE, angle - ANGLE_SEPARATION, spawner, fileHandler);

	final Point pos2 = new Point(pos.x + POS_SEPARATION, pos.y + POS_SEPARATION);
	final Asteroid asteroid2 = new Asteroid(pos2, size - SIZE_DECREASE, angle + ANGLE_SEPARATION, spawner, fileHandler);

	spawner.spawn(asteroid1, asteroid2);
    }

    @Override public CollisionType getCollisionType() {
	return CollisionType.ASTEROID;
    }
}

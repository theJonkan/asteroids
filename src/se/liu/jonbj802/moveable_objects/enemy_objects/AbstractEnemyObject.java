package se.liu.jonbj802.moveable_objects.enemy_objects;

import se.liu.jonbj802.graphics.FileHandler;
import se.liu.jonbj802.moveable_objects.AbstractMoveableObject;

import java.awt.*;
import java.util.Random;

/**
 * AbstractEnemyObject is an abstraction for common code related to enemies. The position and rotation are randomly generated to spawn at
 * the edge and move inwards.
 */
public abstract class AbstractEnemyObject extends AbstractMoveableObject
{
    protected final static Random RND = new Random();

    // The fields below do not make sense as enums. They contain values.

    private final static double TOP_ANGLE = Math.PI / 2;
    private final static double BOTTOM_ANGLE = 3 * Math.PI / 2;
    private final static double RIGHT_ANGLE = 2 * Math.PI;
    private final static double LEFT_ANGLE = Math.PI;

    protected AbstractEnemyObject(final Dimension screenSize, final int size, final FileHandler fileHandler) {
	super(null, 0, size, fileHandler); // Angle and position are generated after.
	generateRandomPosition(screenSize);
    }

    protected AbstractEnemyObject(final Point pos, final int size, final double angle, final FileHandler fileHandler) {
	super(pos, angle, size, fileHandler); // Angle and position are generated after.
    }

    @Override public boolean shouldDespawn(final Dimension screenSize, final int offset) {
	return pos.x > screenSize.width + offset || pos.y > screenSize.height + offset || pos.x < -offset || pos.y < -offset;
    }

    @Override public boolean shouldWrap(final Dimension screenSize, final int offset) {
	return false;
    }

    protected void generateRandomPosition(final Dimension screenSize) {
	final Edge sector = Edge.get(RND.nextInt(Edge.size()));
	switch (sector) {
	    case LEFT -> {
		pos = new Point(0, RND.nextInt(screenSize.height));
		angle = RND.nextDouble(-TOP_ANGLE, TOP_ANGLE);
	    }
	    case RIGHT -> {
		pos = new Point(screenSize.width, RND.nextInt(screenSize.height));
		angle = RND.nextDouble(TOP_ANGLE, BOTTOM_ANGLE);
	    }
	    case BOTTOM -> {
		pos = new Point(RND.nextInt(screenSize.width), 0);
		angle = RND.nextDouble(LEFT_ANGLE);
	    }
	    case TOP -> {
		pos = new Point(RND.nextInt(screenSize.width), screenSize.height);
		angle = RND.nextDouble(LEFT_ANGLE, RIGHT_ANGLE);
	    }
	}
    }
}

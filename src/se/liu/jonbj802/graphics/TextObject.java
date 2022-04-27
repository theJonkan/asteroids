package se.liu.jonbj802.graphics;

import se.liu.jonbj802.collisions.CollisionType;
import se.liu.jonbj802.moveable_objects.AbstractMoveableObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TextObject extends AbstractMoveableObject
{
    private final Matrix matrix;

    public TextObject(final String text, final Point pos, final int size, final FileHandler fileHandler) {
	super(pos, 0, size, fileHandler);
	final List<Matrix> letters = new ArrayList<>();
	for (int i = 1; i < text.length(); i++) {
	    letters.add(fileHandler.get("text_" + text.charAt(i)));
	}

	this.matrix = letters.get(0).append(letters).modify(size, 0);
    }

    @Override public void update() {

    }

    @Override public Matrix getMatrix() {
	return matrix;
    }

    @Override public void collided() {

    }

    @Override public CollisionType getCollisionType() {
	return CollisionType.BULLET_ENEMY;
    }

    @Override public boolean shouldDespawn(final Dimension screenSize, final int offset) {
	return false;
    }
}

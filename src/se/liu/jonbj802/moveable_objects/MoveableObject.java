package se.liu.jonbj802.moveable_objects;

import se.liu.jonbj802.collisions.CollisionType;
import se.liu.jonbj802.graphics.Matrix;

import java.awt.*;

/**
 * MoveableObject defines an object that can move. This is used by the renderer when displaying.
 */
public interface MoveableObject
{
    public Point getPos();
    public void setPos(final int x, final int y);
    public void update();

    public Matrix getMatrix();
    public Rectangle getHitbox(final Dimension screenSize);

    public void collided();
    public CollisionType getCollisionType();

    public boolean shouldDespawn(final Dimension screenSize, final int offset);
    public boolean shouldWrap(final Dimension screenSize, final int offset);
}

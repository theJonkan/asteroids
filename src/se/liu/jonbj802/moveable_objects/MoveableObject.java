package se.liu.jonbj802.moveable_objects;

import se.liu.jonbj802.DisplayableObject;
import se.liu.jonbj802.collisions.CollisionType;

import java.awt.*;

/**
 * MoveableObject defines an object that can move. This is used by the renderer when displaying.
 */
public interface MoveableObject extends DisplayableObject
{
    public void setPos(final int x, final int y);
    public void update();

    public void collided();
    public CollisionType getCollisionType();
    public Rectangle getHitbox(final Dimension screenSize);

    public boolean shouldDespawn(final Dimension screenSize, final int offset);
    public boolean shouldWrap(final Dimension screenSize, final int offset);
}

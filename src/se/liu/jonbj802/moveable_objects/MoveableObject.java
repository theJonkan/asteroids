package se.liu.jonbj802.moveable_objects;

import se.liu.jonbj802.collisions.CollisionType;
import se.liu.jonbj802.graphics.DisplayableObject;

import java.awt.*;

/**
 * MoveableObject defines an object that can move and collide. Note that the coordinates assume the bottom left corner as origin; in
 * contrast to Swing which assumes top left.
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

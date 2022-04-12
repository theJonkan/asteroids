package se.liu.jonbj802;

import java.awt.*;

/**
 * MoveableObject defines an object that can move. This is used by the renderer when displaying.
 */
public interface MoveableObject
{
    public Point getPos();
    public void setPos(final int x, final int y);
    public void update();
    public void collided();

    public Matrix getMatrix();
    public Rectangle getHitbox(final Dimension screenSize);

    public boolean shouldDespawn(final Dimension screenSize, final int offset);
    public boolean shouldWrap(final Dimension screenSize, final int offset);
}

package se.liu.jonbj802;

import java.awt.*;

/**
 * MoveableObject defines an object that can move. This is used by the renderer when displaying.
 */
public interface MoveableObject
{
    public double getAngle();
    public int getSize();
    public Point getPos();
    public void setPos(final int x, final int y);
    public Matrix getMatrix();
    public Rectangle getHitbox(final Dimension screenSize);
    public void update();

    public boolean shouldDespawn(final Dimension screenSize, final int offset);
    public boolean shouldWrap(final Dimension screenSize, final int offset);
}

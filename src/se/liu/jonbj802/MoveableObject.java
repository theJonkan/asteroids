package se.liu.jonbj802;

import java.awt.*;

/**
 * MoveableObject defines an object that can move.
 */
public interface MoveableObject
{
    public double getAngle();
    public int getSize();
    public Point getPos();
    public void setPos(final int x, final int y);
    public double[][] getMatrix();
    public void update();

    public boolean shouldBeRemoved(final Dimension screenSize, final int offset);
    public boolean shouldWrapAround(final Dimension screenSize, final int offset);
}

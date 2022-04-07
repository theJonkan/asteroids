package se.liu.jonbj802;

/**
 * MoveableObject defines an object that can move.
 */
public interface MoveableObject
{
    public double getAngle();
    public int getSize();
    public int getX();
    public int getY();
    // Maybe remove setPos from interface.
    public void setPos(final int x, final int y);
    public double[][] getMatrix();
    public void update();
}

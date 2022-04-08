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
    public double[][] getMatrix();
    public void update();
}

package se.liu.jonbj802;

import se.liu.jonbj802.graphics.Matrix;

import java.awt.*;

/**
 * DisplayableObject defines an object that can be drawn in a static position on screen.
 * Objects that need to move around should use MoveableObject instead.
 */
public interface DisplayableObject
{
    public Point getPos();

    public Matrix getMatrix();
}

package se.liu.jonbj802;

import se.liu.jonbj802.graphics.Matrix;

import java.awt.*;

public interface DisplayableObject
{
    public Point getPos();

    public void setPos(final int x, final int y);

    public Matrix getMatrix();
}

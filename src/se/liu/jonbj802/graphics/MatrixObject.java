package se.liu.jonbj802.graphics;

import java.awt.*;

/**
 * MatrixObject is a wrapper that lets a Matrix be rendered on a specific part of the screen.
 */
public class MatrixObject implements DisplayableObject
{
    private final Point pos;
    private final Matrix matrix;

    public MatrixObject(final Point pos, final Matrix matrix) {
	this.pos = pos;
	this.matrix = matrix;
    }

    @Override public Point getPos() {
	return pos;
    }

    @Override public Matrix getMatrix() {
	return matrix;
    }
}

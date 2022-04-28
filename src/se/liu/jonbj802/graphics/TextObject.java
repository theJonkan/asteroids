package se.liu.jonbj802.graphics;

import se.liu.jonbj802.DisplayableObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * TextObject allows rendering text on screen using vector graphics instead of raster fonts.
 * Allowed characters are: a, d, e, i, n, o, p, r, s, t.
 */
public class TextObject implements DisplayableObject
{
    private final Point pos;
    private final Matrix matrix;

    public TextObject(final String text, final Point pos, final int size, final FileHandler fileHandler) {
	this.pos = pos;

	final List<Matrix> letters = new ArrayList<>();
	letters.add(fileHandler.get("char_" + text.charAt(0)));
	for (int i = 1; i < text.length(); i++) {
	    final char letter = text.charAt(i);
	    if (letter == ' ') {
		letters.add(new Matrix(new double[][] {{0, 0}, {0, 0}}));
		continue;
	    }

	    letters.add(fileHandler.get("char_" + letter));
	}

	this.matrix = letters.get(0).append(letters).modify(size, 0);
    }

    @Override public Point getPos() {
	return pos;
    }

    @Override public Matrix getMatrix() {
	return matrix;
    }
}

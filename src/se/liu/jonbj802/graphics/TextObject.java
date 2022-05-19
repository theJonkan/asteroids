package se.liu.jonbj802.graphics;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * TextObject allows rendering text on screen using vector graphics instead of raster fonts. Allowed characters are: a, d, e, i, n, o, p, r,
 * s, t and space.
 */
public class TextObject implements DisplayableObject
{
    private final Point pos;
    private final Matrix matrix;
    private final FileHandler fileHandler;

    private final static char[] ALLOWED_CHARACTERS = new char[] { ' ', 'a', 'd', 'e', 'i', 'n', 'o', 'p', 'r', 's', 't' };

    public TextObject(final String text, final Point pos, final int size, final FileHandler fileHandler) {
	this.pos = pos;
	this.fileHandler = fileHandler;

	final Logger logger = Logger.getLogger("AsteroidsLog");

	if (text.isEmpty()) {
	    logger.log(Level.WARNING, "Created empty TextObject");
	    this.matrix = fileHandler.get("char_invalid");
	    return;
	}

	// Pre-allocate the list of letters to avoid more heap allocations as it grows.
	final List<Matrix> letters = new ArrayList<>(text.length());
	letters.add(getMatrixFromChar(text.charAt(0)));

	for (int i = 1; i < text.length(); i++) {
	    final char letter = text.charAt(i);
	    letters.add(getMatrixFromChar(letter));
	}

	this.matrix = letters.get(0).append(letters).modify(size, 0);
    }

    private Matrix getMatrixFromChar(final char letter) {
	final Logger logger = Logger.getLogger("AsteroidsLog");

	if (Arrays.binarySearch(ALLOWED_CHARACTERS, letter) < 0) {
	    logger.log(Level.WARNING, "Invalid character: " + letter);
	    return fileHandler.get("char_invalid");
	} else if (letter == ' ') {
	    return fileHandler.get("char_space");
	}

	return fileHandler.get("char_" + letter);
    }

    @Override public Point getPos() {
	return pos;
    }

    @Override public Matrix getMatrix() {
	return matrix;
    }
}

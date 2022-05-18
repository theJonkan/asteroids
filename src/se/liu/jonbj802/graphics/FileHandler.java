package se.liu.jonbj802.graphics;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.IllegalFormatWidthException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * FileHandler allows loading Matrix graphics from json files inside the resources folder.
 */
public class FileHandler
{
    private Map<String, Matrix> matrices;
    private Map<CacheEntry, Matrix> cache;
    private final Gson gson;

    public FileHandler() {
	this.matrices = new HashMap<>();
	this.cache = new HashMap<>();
	this.gson = new Gson();
    }

    public void load(final String name) throws IOException, IllegalFormatWidthException {
	final String path = "images" + File.separator + "matrices" + File.separator + name;
	final Logger logger = Logger.getLogger("AsteroidsLog");

	try (final InputStream fileStream = ClassLoader.getSystemResourceAsStream(path)) {
	    if (fileStream == null) {
		logger.log(Level.SEVERE, "Invalid input stream at: " + path);
		throw new IOException("the file at " + path + " does not exist");
	    }

	    final byte[] contents = fileStream.readAllBytes();
	    if (contents.length == 0) {
		logger.log(Level.WARNING, "Empty file at: " + path);
		throw new IOException("the file at " + path + " is empty");
	    }

	    final Matrix matrix = gson.fromJson(new String(contents), Matrix.class);
	    if (matrix.getColumns() % 2 != 0) {
		logger.log(Level.SEVERE, "Matrix length not even, length: " + matrix.getColumns());
		throw new IllegalFormatWidthException(matrix.getColumns());
	    }

	    matrices.put(name, matrix);
	}
    }

    public void loadAll() throws IOException {
        final Logger logger = Logger.getLogger("AsteroidsLog");

        final URL url = ClassLoader.getSystemResource("images/matrices/matrix_index.txt");
        if (url == null) {
            logger.log(Level.SEVERE, "Could not find the directory");
            throw new IOException("could not find matrices directory");
        }

        final List<String> files = new ArrayList<>();
        try (final InputStream stream = url.openStream()) {
            final Scanner scanner = new Scanner(stream);
            while (scanner.hasNextLine()) {
                files.add(scanner.nextLine());
            }
        }

	for (final String name : files) {
	    load(name);
	}
    }

    public Matrix get(final String name) {
	final Matrix matrix = matrices.get(name + ".json");
	if (matrix == null) {
	    final Logger logger = Logger.getLogger("AsteroidsLog");
	    logger.log(Level.WARNING, "Matrix \"" + name + "\" could not be found. Falling back to empty matrix.");
	    return new Matrix(new double[][] {});
	}

	return matrix;
    }

    public Matrix get(final String name, final int size, final int angle) {
	final CacheEntry entry = new CacheEntry(name, size, angle);
	final Matrix fromCache = cache.get(entry);
	if (fromCache != null) {
	    return fromCache;
	}

	final Matrix modified = matrices.get(name + ".json").modify(size, angle);
	cache.put(entry, modified);
	return modified;
    }

    /**
     * CacheEntry is a small wrapper for putting together an object with name, size and angle.
     */
    private class CacheEntry
    {
	private final String name;
	private final int size, angle;

	private CacheEntry(final String name, final int size, final int angle) {
	    this.name = name;
	    this.size = size;
	    this.angle = angle;
	}

	@Override public boolean equals(final Object obj) {
	    if (this == obj) {
		return true;
	    }
	    if (obj instanceof CacheEntry cache) {
		return cache.name.equals(name) && cache.size == size && cache.angle == angle;
	    }

	    return false;
	}

	@Override public int hashCode() {
	    return name.hashCode() + size + angle;
	}
    }
}

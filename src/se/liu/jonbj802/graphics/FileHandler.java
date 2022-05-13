package se.liu.jonbj802.graphics;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.IllegalFormatWidthException;
import java.util.Map;

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
        final InputStream fileStream = ClassLoader.getSystemResourceAsStream(path);
        if (fileStream == null) {
            throw new IOException("the file at " + path + " does not exist");
        }

        final byte[] contents = fileStream.readAllBytes();
        if (contents.length == 0) {
            throw new IOException("the file at " + path + " is empty");
        }

        final Matrix matrix = gson.fromJson(new String(contents), Matrix.class);
        if (matrix.getColumns() % 2 != 0) {
            throw new IllegalFormatWidthException(matrix.getColumns());
        }

        matrices.put(name, matrix);
    }

    public void loadAll() throws IOException {
        final URL url = ClassLoader.getSystemResource("images/matrices");
        if (url == null) {
            throw new IOException("could not find matrices directory");
        }

        final File directory = new File(url.getPath());
        final String[] files = directory.list();
        if (files == null) {
            throw new IOException("the directory " + directory.getPath() + " does not exist or is empty");
        }

        for (final String name : files) {
            load(name);
        }
    }

    public Matrix get(final String name) {
        return matrices.get(name + ".json");
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
}

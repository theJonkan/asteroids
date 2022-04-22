package se.liu.jonbj802.graphics;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * FileHandler allows loading Matrix graphics from json files inside the resources folder.
 */
public class FileHandler
{
    private Map<String, Matrix> matrices;
    private final Gson gson;

    public FileHandler() {
        this.matrices = new HashMap<>();
        this.gson = new Gson();
    }

    public void load(final String name) throws IOException {
        final String path = "images" + File.separator + "matricies" + File.separator + name + ".json";
        final InputStream matrix = ClassLoader.getSystemResourceAsStream(path);
        if (matrix == null) {
            throw new IOException("the file " + path + " does not exist or is empty");
        }

        final byte[] contents = matrix.readAllBytes();
        matrices.put(name, gson.fromJson(new String(contents), Matrix.class));
    }

    public Matrix get(final String name) {
        return matrices.get(name);
    }
}

package se.liu.jonbj802.graphics;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Matrix contains rendering positions for graphics objects. It will always assume a height of 2. These methods are performance sensitive
 * and mostly assumes to be called with valid inputs. Invalid inputs are logged accordingly.
 */
public class Matrix
{
    private double[][] positions;

    private final static int HEIGHT = 2;

    public Matrix(final double[][] positions) {
	this.positions = positions;
    }

    public Matrix modify(int scale, double angle) {
	final double scaledSine = scale * Math.sin(angle);
	final double scaledCosine = scale * Math.cos(angle);

	// See link for formula https://en.wikipedia.org/wiki/Rotation_matrix#In_two_dimensions.
	final double[][] scaler = {
		{scaledCosine, -scaledSine},
		{scaledSine, scaledCosine}
	};

	return new Matrix(multiply(scaler));
    }

    private double[][] multiply(final double[][] matrix) {
	if (matrix == null) {
	    final Logger logger = Logger.getLogger("AsteroidsLog");
	    logger.log(Level.WARNING, "Matrix is null pointer");
	    return new double[][] {};
	}

	final int columns = positions[0].length;
	final double[][] result = new double[HEIGHT][columns];

	// Optimized matrix multiplication for height of 2.
	for (int j = 0; j < columns; j++) {
	    // Faster way of doing: result[0][j] = matrix[0][0] * positions[0][j] + matrix[0][1] * positions[1][j];
	    result[0][j] = Math.fma(matrix[0][0], positions[0][j], matrix[0][1] * positions[1][j]);

	    // Faster way of doing: result[1][j] = matrix[1][0] * positions[0][j] + matrix[1][1] * positions[1][j];
	    result[1][j] = Math.fma(matrix[1][0], positions[0][j], matrix[1][1] * positions[1][j]);
	}

	return result;
    }

    public Matrix append(final List<Matrix> matrices) {
	if (matrices.isEmpty()) {
	    final Logger logger = Logger.getLogger("AsteroidsLog");
	    logger.log(Level.WARNING, "Matrix append with empty list. Falling back to existing information.");
	    return new Matrix(positions);
	}

	// Sum up all the columns in all the matricies.
	int columns = positions[0].length;
	for (Matrix matrix : matrices) {
	    columns += matrix.positions[0].length;
	}

	final double[][] result = new double[HEIGHT][columns];

	// Add the first lettter to the matrix.
	int indexOffset = positions[0].length;
	for (int i = 0; i < indexOffset; i++) {
	    result[0][i] = positions[0][i];
	    result[1][i] = positions[1][i];
	}

	// Add all the remaining letters with spacing between.
	final int letterOffset = 5;
	for (int i = 0; i < matrices.size(); i++) {
	    final Matrix matrix = matrices.get(i);
	    final int length = matrix.positions[0].length;
	    for (int j = 0; j < length; j++) {
		result[0][indexOffset + j] = matrix.positions[0][j] + letterOffset * i;
		result[1][indexOffset + j] = matrix.positions[1][j];
	    }

	    indexOffset += length;
	}

	return new Matrix(result);
    }

    public double get(int x, int y) {
	if (x >= positions[0].length || y >= positions.length) {
	    final Logger logger = Logger.getLogger("AsteroidsLog");
	    logger.log(Level.WARNING, "Matrix coordinates (" + x + ", " + y + ") out of range.");
	    return 0;
	}

	return positions[y][x];
    }

    public int getColumns() {
	return positions[0].length;
    }
}

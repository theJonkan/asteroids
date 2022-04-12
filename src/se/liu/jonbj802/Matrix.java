package se.liu.jonbj802;

/** Matrix contains rendering positions for graphics objects. It will always assume a height of 2. */
public class Matrix
{
    private double[][] positions;

    public Matrix(final double[][] positions) {
	this.positions = positions;
    }

    public void modify(int scale, double angle){
	final double scaledSine = scale * Math.sin(angle);
	final double scaledCosine = scale * Math.cos(angle);

	// See link for formula https://en.wikipedia.org/wiki/Rotation_matrix#In_two_dimensions.
	final double[][] scaler = {
		{scaledCosine, -scaledSine},
		{scaledSine, scaledCosine}
	};

	positions = multiply(scaler);
    }

    /** Matrix multiplication optimized for matrix height 2 */
    private double[][] multiply(final double[][] matrix){
	final int columns = positions[0].length;
	final double[][] result = new double[2][columns];

	for (int j = 0; j < columns; j++) {
	    // Faster way of doing: result[0][j] = matrix[0][0] * positions[0][j] + matrix[0][1] * positions[1][j];
	    result[0][j] = Math.fma(matrix[0][0], positions[0][j], matrix[0][1] * positions[1][j]);

	    // Faster way of doing: result[1][j] = matrix[1][0] * positions[0][j] + matrix[1][1] * positions[1][j];
	    result[1][j] = Math.fma(matrix[1][0], positions[0][j], matrix[1][1] * positions[1][j]);
	}

	return result;
    }

    public double get(int x, int y){
	return positions[y][x];
    }

    public int getColumns(){
	return positions[0].length;
    }
}

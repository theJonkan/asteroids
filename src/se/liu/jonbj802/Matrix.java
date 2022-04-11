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

	positions = multiply(scaler, positions);
    }

    /** Matrix multiplication optimized for matrix height 2 */
    private double[][] multiply(final double[][] matrix1, final double[][] matrix2){
	final int columns = matrix2[0].length;
	final double[][] result = new double[2][columns];

	for (int j = 0; j < columns; j++) {
	    result[0][j] = matrix1[0][0] * matrix2[0][j] + matrix1[0][1] * matrix2[1][j];
	}

	for (int j = 0; j < columns; j++) {
	    result[1][j] = matrix1[1][0] * matrix2[0][j] + matrix1[1][1] * matrix2[1][j];
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

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

    private double[][] multiply(final double[][] matrix1, final double[][] matrix2){
	final int rows = matrix1.length, columns = matrix2[0].length;
	final double[][] result = new double[rows][columns];

	for (int i = 0; i < rows; i++) {
	    for (int j = 0; j < columns; j++) {
		for (int k = 0; k < rows; k++) {
		    result[i][j] += matrix1[i][k] * matrix2[k][j];
		}
	    }
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

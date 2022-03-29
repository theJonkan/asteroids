package se.liu.jonbj802;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameComponent extends JComponent
{
    public GameComponent() {
    }

    private int[][] multiplyMatrix(final int[][] matrix1, final int[][] matrix2){
        final int rows = matrix1.length, columns = matrix2[0].length;
        final int[][] result = new int[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                for (int k = 0; k < rows; k++) {
                    result[i][j] += matrix1[i][k] * matrix2[k][j];
                }
            }
        }

        return result;
    }

    private int[][] multiplyMatrix(final int[][] matrix, final int a){
        final int rows = matrix.length, columns = matrix[0].length;
        final int[][] result = new int[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                result[i][j] = a * matrix[i][j];
            }
        }

        return result;
    }

    @Override public Dimension getPreferredSize() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        double preferredHeight = screenSize.getHeight();
        double preferredWidth = screenSize.getWidth();

        return new Dimension((int)preferredWidth, (int)preferredHeight);

    }

    @Override protected void paintComponent(final Graphics g) {
        final Graphics2D g2d = (Graphics2D) g;

        Dimension size = getSize();
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, size.width, size.height);
        super.paintComponent(g);
    }
}

package se.liu.jonbj802;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameComponent extends JComponent
{
    final List<MoveableObject> objects;

    public GameComponent(final List<MoveableObject> objects) {
        this.objects = objects;
    }

    private double[][] multiplyMatrix(final double[][] matrix1, final double[][] matrix2){
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

    private double[][] multiplyMatrix(final double[][] matrix, final double a){
        final int rows = matrix.length, columns = matrix[0].length;
        final double[][] result = new double[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                result[i][j] = a * matrix[i][j];
            }
        }

        return result;
    }

    private void drawLines(final double[][] matrix, final int xOffset, final int yOffset, final Graphics2D g2d) {
        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(1));

        final int height = getSize().height;

        final int columns = matrix[0].length;
        for (int i = 0; i < columns; i += 2) {
            final double x1 = matrix[0][i], y1 = matrix[1][i];
            final double x2 = matrix[0][i + 1], y2 = matrix[1][i + 1];
            g2d.drawLine((int) x1 + xOffset, height - ((int) y1 + yOffset), (int) x2 + xOffset, height - ((int) y2 + yOffset));
        }
    }

    @Override public Dimension getPreferredSize() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        double preferredHeight = screenSize.getHeight();
        double preferredWidth = screenSize.getWidth();

        return new Dimension((int)preferredWidth, (int)preferredHeight);

    }

    @Override protected void paintComponent(final Graphics g) {
        final Graphics2D g2d = (Graphics2D) g;

        final Dimension size = getSize();

        // Draw a black background.
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, size.width, size.height);

        for (final MoveableObject object : objects) {
            final int scale =  object.getSize();
            final double radians = Math.toRadians(object.getAngle());
            final double[][] scaler = {
                    {scale * Math.cos(radians), -scale * Math.sin(radians)},
                    {scale * Math.sin(radians), scale * Math.cos(radians)}
            };

            final double[][] matrix = multiplyMatrix(object.getMatrix(), object.getSize());
            final int x = object.getX(), y = object.getY();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            drawLines(matrix, x, y, g2d);

        }

        super.paintComponent(g);
    }
}

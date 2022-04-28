package se.liu.jonbj802;

import se.liu.jonbj802.graphics.Matrix;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * AbstractRendererComponent helps with the drawing of DisplayableObjects.
 * It draws a black background and all the lines between points in the objects' matrices.
 */
public abstract class AbstractRendererComponent extends JComponent
{
    private final static int LINE_WIDTH = 1;

    private void drawLines(final Matrix matrix, final int x, final int y, final Graphics2D g2d) {
        final int height = getSize().height;

        final int columns = matrix.getColumns();
        final int lineDataLength = 2;
        for (int i = 0; i < columns; i += lineDataLength) {
            final double x1 = matrix.get(i, 0), y1 = matrix.get(i, 1);
            final double x2 = matrix.get(i + 1, 0), y2 = matrix.get(i + 1, 1);
            g2d.drawLine((int) x1 + x, height - ((int) y1 + y), (int) x2 + x, height - ((int) y2 + y));
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

        super.paintComponent(g);
    }

    protected void paintObjects(final Graphics g, final List<? extends DisplayableObject> objects) {
        final Graphics2D g2d = (Graphics2D) g;

        // Change rendering hints for better line quality.
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(LINE_WIDTH));
        for (final DisplayableObject object : objects) {
            final Point pos = object.getPos();
            drawLines(object.getMatrix(), pos.x, pos.y, g2d);
        }

        super.paintComponent(g);
    }
}

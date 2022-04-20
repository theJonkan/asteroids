package se.liu.jonbj802;

import se.liu.jonbj802.graphics.Matrix;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * GameComponent renders lines between the points inside the matricies.
 */
public class GameComponent extends JComponent
{
    private final List<MoveableObject> objects;

    private final static int SCORE_OFFSET = 25;
    private final static int TEXT_SIZE = 30;
    private final static int LINE_WIDTH = 1;

    private boolean debug;

    public GameComponent(final List<MoveableObject> objects, final boolean debug) {
        this.objects = objects;
        this.debug = debug;
    }

    private void drawLines(final Matrix matrix, final int x, final int y, final Graphics2D g2d) {
        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(LINE_WIDTH));

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

        // Change rendering hints for better line quality.
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);


        for (final MoveableObject object : objects) {
            final Point pos = object.getPos();

            if (debug) {
                g2d.setColor(Color.red);
                g2d.draw(object.getHitbox(size));
            }

            drawLines(object.getMatrix(), pos.x, pos.y, g2d);
        }

        if (!objects.isEmpty()) {
            g2d.setFont(new Font("serif", Font.PLAIN, TEXT_SIZE));
            g2d.drawString(String.valueOf(((Rocket) objects.get(0)).getScore()), SCORE_OFFSET, SCORE_OFFSET + TEXT_SIZE);
            g2d.drawString("Lives: " + ((Rocket) objects.get(0)).getHealth(), SCORE_OFFSET, (SCORE_OFFSET + TEXT_SIZE) * 2);

        }

        super.paintComponent(g);
    }
}

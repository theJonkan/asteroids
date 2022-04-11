package se.liu.jonbj802;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * GameComponent renders lines between the points inside the matricies.
 */
public class GameComponent extends JComponent
{
    private final List<MoveableObject> objects;

    private final static int LINE_WIDTH = 1;
    private final static int SCALE_SIZE = 9;

    public GameComponent(final List<MoveableObject> objects) {
        this.objects = objects;
    }




    private void drawLines(final Matrix matrix, final int x, final int y, final Graphics2D g2d) {
        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(LINE_WIDTH));

        final int height = getSize().height;

        final int columns = matrix.getColumns();
        for (int i = 0; i < columns; i += 2) {
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
            final int scale = object.getSize();
            final Point pos = object.getPos();
            final int hitboxSize = scale * SCALE_SIZE;

            // g2d.setColor(Color.TRANSLUCENT);
            g2d.setColor(Color.red);
            Rectangle hitbox = new Rectangle(pos.x - hitboxSize/2, getSize().height - pos.y - hitboxSize/2, hitboxSize, hitboxSize);
            g2d.draw(hitbox);


            drawLines(object.getMatrix(), pos.x, pos.y, g2d);

        }

        super.paintComponent(g);
    }
}

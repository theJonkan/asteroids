package se.liu.jonbj802;

import se.liu.jonbj802.graphics.AbstractRendererComponent;
import se.liu.jonbj802.graphics.FileHandler;

import java.awt.*;

/**
 * StartScreenHandler contains all the logic for setting up and drawing the start screen.
 */
public class StartScreenComponent extends AbstractRendererComponent
{
    private static final int TEXT_SIZE = 30;
    private static final int WIDTH_OFFSET = 100;
    private static final int HEIGHT_OFFSET = 0;

    private final FileHandler fileHandler;

    protected StartScreenComponent(final FileHandler fileHandler) {
	super();
	this.fileHandler = fileHandler;
    }

    @Override protected void paintComponent(final Graphics g) {
	final Graphics2D g2d = (Graphics2D) g;
	final Dimension size = getSize();

	super.paintComponent(g2d);

	g2d.setColor(Color.WHITE);
	g2d.setFont(new Font("serif", Font.PLAIN, TEXT_SIZE));
	g2d.drawString("JAVA ASTEROIDS", size.width/2 - WIDTH_OFFSET, size.height / 2 + HEIGHT_OFFSET);
	g2d.drawString("Press enter to start the game", size.width/2 - WIDTH_OFFSET * 2, size.height / 2 + HEIGHT_OFFSET + TEXT_SIZE);
    }
}

package se.liu.jonbj802;

import se.liu.jonbj802.graphics.AbstractRendererComponent;
import se.liu.jonbj802.graphics.FileHandler;
import se.liu.jonbj802.graphics.TextObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * StartScreenComponent handles all the drawing of the start screen.
 */
public class StartScreenComponent extends AbstractRendererComponent
{
    private static final int TITLE_SIZE = 8;
    private static final int WIDTH_OFFSET = 20;
    private static final int HEIGHT_OFFSET = 40;

    protected StartScreenComponent(final FileHandler fileHandler) {
	super(fileHandler);
    }

    @Override protected void paintComponent(final Graphics g) {
	final Graphics2D g2d = (Graphics2D) g;
	final Dimension size = getSize();

	super.paintComponent(g2d);

	final List<TextObject> textObjects = new ArrayList<>();

	final String titleText = "asteroids";
	final Point titlePos = new Point(size.width / 2 - titleText.length() * WIDTH_OFFSET, size.height / 2 + HEIGHT_OFFSET);
	final TextObject title = new TextObject(titleText, titlePos, TITLE_SIZE, fileHandler);
	textObjects.add(title);

	final String descriptionText = "press enter to start";
	final Point descriptionPos = new Point(size.width / 2 - descriptionText.length() * WIDTH_OFFSET, size.height / 2 - HEIGHT_OFFSET);
	final TextObject description = new TextObject(descriptionText, descriptionPos, TITLE_SIZE, fileHandler);
	textObjects.add(description);

	paintObjects(g2d, textObjects);
    }
}

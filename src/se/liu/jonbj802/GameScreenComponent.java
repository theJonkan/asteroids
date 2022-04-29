package se.liu.jonbj802;

import se.liu.jonbj802.graphics.AbstractRendererComponent;
import se.liu.jonbj802.graphics.DisplayableObject;
import se.liu.jonbj802.graphics.FileHandler;
import se.liu.jonbj802.graphics.Matrix;
import se.liu.jonbj802.moveable_objects.MoveableObject;
import se.liu.jonbj802.moveable_objects.Rocket;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * GameScreenComponent renders the game screen with all the objects and text.
 */
public class GameScreenComponent extends AbstractRendererComponent
{
    private final static int SCORE_OFFSET = 25;
    private final static int TEXT_SIZE = 30;
    private final static int LIVES_HORIZONTAL_OFFSET = 35;
    private final static int LIVES_VERTICAL_OFFSET = 4 * SCORE_OFFSET;
    private final static int LIVES_SPACING = 30;


    private final Rocket rocket;
    private final List<MoveableObject> objects;
    private final FileHandler fileHandler;

    public GameScreenComponent(final List<MoveableObject> objects, final Rocket rocket, final FileHandler fileHandler) {
	super();
	this.objects = objects;
	this.rocket = rocket;
	this.fileHandler = fileHandler;
    }

    @Override protected void paintComponent(final Graphics g) {
	final Graphics2D g2d = (Graphics2D) g;

	super.paintComponent(g2d);
	paintObjects(g2d, objects);

	if (objects.isEmpty()) {
	    return;
	}

	g2d.setFont(new Font("serif", Font.PLAIN, TEXT_SIZE));
	g2d.drawString(String.valueOf(rocket.getScore()), SCORE_OFFSET, SCORE_OFFSET + TEXT_SIZE);

	final Dimension size = getSize();

	final Matrix rocketLifeMatrix = fileHandler.get("rocket_drifting").modify(3, Math.PI/2);
	final List<DisplayableObject> lives = new ArrayList<>();
	for (int i = 0; i < rocket.getHealth(); i++) {
	    final int offset = i * LIVES_SPACING;
	    lives.add(new DisplayableObject()
	    {
		@Override public Point getPos() {
		    return new Point(LIVES_HORIZONTAL_OFFSET + offset, size.height - LIVES_VERTICAL_OFFSET);
		}

		@Override public Matrix getMatrix() { return rocketLifeMatrix; }
	    });
	}

	paintObjects(g2d, lives);
    }
}

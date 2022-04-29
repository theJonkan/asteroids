package se.liu.jonbj802;

import se.liu.jonbj802.graphics.AbstractRendererComponent;
import se.liu.jonbj802.moveable_objects.MoveableObject;
import se.liu.jonbj802.moveable_objects.Rocket;

import java.awt.*;
import java.util.List;

public class GameScreenComponent extends AbstractRendererComponent
{
    private final static int SCORE_OFFSET = 25;
    private final static int TEXT_SIZE = 30;

    private final Rocket rocket;
    private final List<MoveableObject> objects;

    public GameScreenComponent(final List<MoveableObject> objects, final Rocket rocket) {
	super();
	this.objects = objects;
	this.rocket = rocket;
    }

    @Override protected void paintComponent(final Graphics g) {
	final Graphics2D g2d = (Graphics2D) g;

	super.paintComponent(g2d);
	paintObjects(g2d, objects);

	if (!objects.isEmpty()) {
	    g2d.setFont(new Font("serif", Font.PLAIN, TEXT_SIZE));
	    g2d.drawString(String.valueOf(rocket.getScore()), SCORE_OFFSET, SCORE_OFFSET + TEXT_SIZE);
	    g2d.drawString("Lives: " + rocket.getHealth(), SCORE_OFFSET, (SCORE_OFFSET + TEXT_SIZE) * 2);
	}
    }
}

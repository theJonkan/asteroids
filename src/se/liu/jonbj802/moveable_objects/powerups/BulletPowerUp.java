package se.liu.jonbj802.moveable_objects.powerups;

import se.liu.jonbj802.graphics.FileHandler;
import se.liu.jonbj802.graphics.Matrix;
import se.liu.jonbj802.moveable_objects.Rocket;

import java.awt.*;

/**
 * BulletPowerUp is a powerup that make the rocket shoot in a scattershot pattern.
 */
public class BulletPowerUp extends AbstractPowerUp
{
    private final Matrix matrix;

    public BulletPowerUp(final Dimension screenSize, final Rocket rocket, final FileHandler fileHandler)
    {
	super(screenSize, rocket, fileHandler);
	this.matrix = fileHandler.get("powerup_bullet").modify(size, angle);
    }

    @Override public void collided() {
	super.collided();
	rocket.addPowerUp();
	rocket.enableScattershot();
    }


    @Override public Matrix getMatrix() {
	return matrix;
    }
}

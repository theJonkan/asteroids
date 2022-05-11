package se.liu.jonbj802.moveable_objects.powerups;

import se.liu.jonbj802.graphics.FileHandler;
import se.liu.jonbj802.graphics.Matrix;
import se.liu.jonbj802.moveable_objects.Rocket;

import java.awt.*;

/**
 * SpeedPowerUp is a powerup that increases the max speed of the rocket.
 */
public class SpeedPowerUp extends AbstractPowerUp
{
    private final Matrix matrix;

    public SpeedPowerUp(final Dimension screenSize, final Rocket rocket, final FileHandler fileHandler)
    {
	super(screenSize, rocket, fileHandler);
	this.matrix = fileHandler.get("powerup_speed").modify(size, angle);
    }

    @Override public void collided() {
	super.collided();
	rocket.addPowerUp();
	rocket.speedUp();
    }

    @Override public Matrix getMatrix() {
	return matrix;
    }
}

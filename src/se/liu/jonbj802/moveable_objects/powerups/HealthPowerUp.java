package se.liu.jonbj802.moveable_objects.powerups;

import se.liu.jonbj802.graphics.FileHandler;
import se.liu.jonbj802.graphics.Matrix;
import se.liu.jonbj802.moveable_objects.Rocket;

import java.awt.*;

/**
 * HealthPowerUp is a powerup that gives the rocket one more life.
 */
public class HealthPowerUp extends AbstractPowerUp
{
    public HealthPowerUp(final Dimension screenSize, final Rocket rocket, final FileHandler fileHandler)
    {
	super(screenSize, rocket, fileHandler);
    }

    @Override public void collided() {
	super.collided();
	rocket.addPowerUp();
	rocket.setHealth(rocket.getHealth() + 1);
    }

    @Override public Matrix getMatrix() {
	return fileHandler.get("powerup_health", size, 0);
    }
}

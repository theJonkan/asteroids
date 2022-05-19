package se.liu.jonbj802.moveable_objects.powerups;

import se.liu.jonbj802.graphics.FileHandler;
import se.liu.jonbj802.moveable_objects.Rocket;

import java.awt.*;

/**
 * HealthPowerUp is a powerup that gives the rocket one more life.
 */
public class HealthPowerUp extends AbstractPowerUp
{
    public HealthPowerUp(final Dimension screenSize, final Rocket rocket, final FileHandler fileHandler)
    {
	super(screenSize, rocket, "powerup_health", fileHandler);
    }

    @Override public void collided() {
	super.collided();
	rocket.setHealth(rocket.getHealth() + 1);
    }
}

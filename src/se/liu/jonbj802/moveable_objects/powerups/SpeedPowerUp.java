package se.liu.jonbj802.moveable_objects.powerups;

import se.liu.jonbj802.graphics.FileHandler;
import se.liu.jonbj802.moveable_objects.Rocket;

import java.awt.*;

/**
 * SpeedPowerUp is a powerup that increases the max speed of the rocket.
 */
public class SpeedPowerUp extends AbstractPowerUp
{
    public SpeedPowerUp(final Dimension screenSize, final Rocket rocket, final FileHandler fileHandler)
    {
	super(screenSize, rocket, "powerup_speed", fileHandler);
    }

    @Override public void collided() {
	super.collided();
	rocket.speedUp();
    }
}

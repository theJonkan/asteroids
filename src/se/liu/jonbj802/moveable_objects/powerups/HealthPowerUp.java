package se.liu.jonbj802.moveable_objects.powerups;

import se.liu.jonbj802.graphics.FileHandler;
import se.liu.jonbj802.graphics.Matrix;
import se.liu.jonbj802.moveable_objects.Rocket;

import java.awt.*;

public class HealthPowerUp extends AbstractPowerUp
{

    public HealthPowerUp(final Dimension screenSize, final Rocket rocket, final FileHandler fileHandler)
    {
	super(screenSize, rocket, fileHandler);
    }

    @Override public void collided() {
	super.collided();
	rocket.setHealth(rocket.getHealth() + 1);
    }

    @Override public Matrix getMatrix() {
	return fileHandler.get("health_powerup").modify(size, angle);
    }
}

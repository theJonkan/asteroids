package se.liu.jonbj802;

import javax.swing.text.Position;
import java.awt.*;
import java.util.Random;

/**
 * AbstractEnemyObject is an abstraction for common code related to enemies.
 * The position and rotation are randomly generated to spawn at the edge and move inwards.
 */
public abstract class AbstractEnemyObject implements EnemyObject
{
    private final static Random RND = new Random();
    protected Point pos = null;
    protected double angle;

    protected AbstractEnemyObject(final Dimension screenSize) {
        generateRandomPosition(screenSize);
    }

    @Override public Point getPos() {
        return pos;
    }

    @Override public double getAngle() {
        return angle;
    }

    protected void move(final double distance){
        pos.x += (int)Math.round(Math.cos(angle) * distance);
        pos.y += (int)Math.round(Math.sin(angle) * distance);
    }

    protected void generateRandomPosition(final Dimension screenSize) {
        final Edge sector = Edge.get(RND.nextInt(Edge.size()));
        switch (sector) {
            case LEFT -> {
                this.pos = new Point(0, RND.nextInt(screenSize.height));
                this.angle = RND.nextDouble(-1.0 / 2, 1.0/2);
            }
            case  RIGHT -> {
                this.pos = new Point(screenSize.width, RND.nextInt(screenSize.height));
                this.angle = RND.nextDouble(1.0/2, 3.0/2);
            }
            case BOTTOM -> {
                this.pos = new Point(RND.nextInt(screenSize.width),0);
                this.angle = RND.nextDouble(1);
            }
            case TOP -> {
                this.pos = new Point(RND.nextInt(screenSize.width), screenSize.height);
                this.angle = RND.nextDouble(1, 2);
            }
        }
        this.angle *= Math.PI;
    }
}

package se.liu.jonbj802;

import java.awt.*;
import java.util.Random;

/**
 * AbstractEnemyObject is an abstraction for common code related to enemies.
 * The position and rotation are randomly generated to spawn at the edge and move inwards.
 */
public abstract class AbstractEnemyObject extends AbstractMoveableObject implements EnemyObject
{
    private final static Random RND = new Random();

    protected AbstractEnemyObject(final Dimension screenSize) {
        super();
        generateRandomPosition(screenSize);
    }

    @Override public boolean shouldDespawn(final Dimension screenSize, final int offset) {
        return pos.x > screenSize.width + offset || pos.y > screenSize.height + offset || pos.x < -offset || pos.y < -offset;
    }

    @Override public boolean shouldWrap(final Dimension screenSize, final int offset) {
        return false;
    }

    // TODO: Avoid entirely vertical or horizontal directions?

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

package se.liu.jonbj802;

import se.liu.jonbj802.collisions.CollisionType;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Asteroid is an enemy that breaks into smaller chunks when hit.
 */
public class Asteroid extends AbstractEnemyObject
{
    private final static Random RND = new Random();
    private double speed;

    private final static int SMALLEST_ASTEROID = 4;
    private final static int BIGGEST_ASTEROID = 15;

    private final static int POS_SEPARATION = 10;
    private final static double ANGLE_SEPARATION = 0.3;
    private final static int SIZE_DECREASE = 2;

    private boolean hasCollided;
    private SpawnListener spawner = null;

    private Matrix matrix;
    private final static double[][] VECTORS = new double[][] {
            { -1, 0, 0, 2, 2, 4, 4, 4, 4, 3, 3, 1, 1, 1, 1, -1, -1, -4, -4, -4, -4, -3, -3, -4, -4, -4, -4, -2, -2, -1 },
            { 5, 4, 4, 4, 4, 2, 2, 0, 0, -3, -3, -2, -2, -4, -4, -4, -4, -2, -2, -1, -1, 0, 0, 1, 1, 2, 2, 2, 2, 5 },
    };

    public Asteroid(final Dimension screenSize, final SpawnListener spawner) {
        super(screenSize, RND.nextInt(SMALLEST_ASTEROID, BIGGEST_ASTEROID));
        init(spawner);
    }

    public Asteroid(final Point pos, final int size, final double angle, final SpawnListener spawner) {
        super(pos, size, angle);
        init(spawner);
    }

    private void init(final SpawnListener spawner) {
        this.speed = 45.0 / size;
        this.matrix = new Matrix(VECTORS);
        this.matrix.modify(size, angle);
        this.spawner = spawner;
    }

    @Override public int getHealth() {
        return 0;
    }

    @Override public void setHealth() {

    }

    @Override public Matrix getMatrix() {
        return matrix;
    }

    @Override public void update() {
        move(speed);
    }

    @Override public void collided() {
        hasCollided = true;

        if (size - SMALLEST_ASTEROID <= 0) {
            return;
        }

        final Point pos1 = new Point(pos.x - POS_SEPARATION, pos.y - POS_SEPARATION);
        final Asteroid asteroid1 = new Asteroid(pos1, size - SIZE_DECREASE, angle - ANGLE_SEPARATION, spawner);

        final Point pos2 = new Point(pos.x + POS_SEPARATION, pos.y + POS_SEPARATION);
        final Asteroid asteroid2 = new Asteroid(pos2,size - SIZE_DECREASE, angle + ANGLE_SEPARATION, spawner);

        final List<MoveableObject> list = new ArrayList<>();
        list.add(asteroid1);
        list.add(asteroid2);
        spawner.spawn(list);
    }

    @Override public boolean shouldDespawn(final Dimension screenSize, final int offset) {
        return hasCollided || super.shouldDespawn(screenSize, offset);
    }

    @Override public CollisionType getCollisionType() {
        return CollisionType.ASTEROID;
    }
}

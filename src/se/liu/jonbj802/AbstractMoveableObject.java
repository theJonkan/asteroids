package se.liu.jonbj802;

import se.liu.jonbj802.graphics.FileHandler;

import java.awt.*;

/**
 * AbstractMoveableObject is an abstraction for common code related to all movable objects.
 */
public abstract class AbstractMoveableObject implements MoveableObject
{
    protected Point pos;
    protected double angle;
    protected int size;
    protected FileHandler fileHandler;

    protected final static int SCALE_SIZE = 9;

    protected AbstractMoveableObject(final Point pos, final double angle, final int size, final FileHandler fileHandler) {
        this.pos = pos;
        this.angle = angle;
        this.size = size;
        this.fileHandler = fileHandler;
    }

    @Override public Point getPos() {
        return pos;
    }

    @Override public void setPos(final int x, final int y) {
        pos.x = x;
        pos.y = y;
    }

    @Override public boolean shouldWrap(final Dimension screenSize, final int offset) {
        return true;
    }

    @Override public Rectangle getHitbox(final Dimension screenSize) {
        final int hitboxSize = SCALE_SIZE * size;
        return new Rectangle(pos.x - hitboxSize/2, screenSize.height - pos.y - hitboxSize/2, hitboxSize, hitboxSize);
    }

    protected void move(final double distance){
        pos.x += (int)Math.round(Math.cos(angle) * distance);
        pos.y += (int)Math.round(Math.sin(angle) * distance);
    }
}

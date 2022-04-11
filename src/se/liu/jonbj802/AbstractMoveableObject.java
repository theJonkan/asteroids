package se.liu.jonbj802;

import java.awt.*;

public abstract class AbstractMoveableObject implements MoveableObject
{
    protected Point pos = null;
    protected double angle;

    // TODO: Fix AbstractClasses position, angle etc. Next step implement hitbox.
    public AbstractMoveableObject(final Point pos, double angle) {
        this.pos = pos;
        this.angle = angle;
    }

    @Override public Point getPos() {
        return pos;
    }

    @Override public void setPos(final int x, final int y) {
        pos.x = x;
        pos.y = y;
    }
    @Override public double getAngle() {
        return angle;
    }

    protected void move(final double distance){
        pos.x += (int)Math.round(Math.cos(angle) * distance);
        pos.y += (int)Math.round(Math.sin(angle) * distance);
    }
}

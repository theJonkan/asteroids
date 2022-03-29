package se.liu.jonbj802;

import java.awt.*;

public class Rocket implements MoveableObject
{
    private boolean flying;
    private final static int SIZE = 20;



    @Override public int getSize() {
        return SIZE;
    }

    @Override public void setPos(final int x, final int y) {

    }

    @Override public int getX() {
        return 200;
    }

    @Override public int getY() {
        return 200;
    }

    @Override public int[][] getMatrix() {
        if (flying) {
            return new int[][] {
                    { -3, 0, 3, 0, -2, 2, 0, -1, 0, 1},
                    { -3, 5, -3, 5, -1, -1, -4, -1, -4, -1},
            };
        }

        return new int[][] { { -3, 0, 3, 0, -2, 2 }, { -3, 5, -3, 5, -1, -1 }, };
    }
}

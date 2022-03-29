package se.liu.jonbj802;

import javax.swing.*;
import java.awt.*;

public interface MoveableObject
{
    public int getSize();
    public void setPos(int x, int y);
    public int getX();
    public int getY();
    public int[][] getMatrix();

}

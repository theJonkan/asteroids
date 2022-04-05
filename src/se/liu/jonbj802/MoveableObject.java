package se.liu.jonbj802;

import javax.swing.*;
import java.awt.*;

public interface MoveableObject
{
    public double getAngle();
    public int getSize();
    public int getX();
    public int getY();
    public double[][] getMatrix();
    public void update();

}

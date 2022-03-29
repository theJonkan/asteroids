package se.liu.jonbj802;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameRunner
{

    public static void main(String[] args) {
        final List<MoveableObject> objects = new ArrayList<>();
        objects.add(new Rocket());

        final JFrame frame = new JFrame("Asteroids");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        GameComponent gameWindow = new GameComponent(objects);
        frame.add(gameWindow);

        frame.pack();
        frame.setVisible(true);
    }
}


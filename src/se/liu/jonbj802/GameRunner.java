package se.liu.jonbj802;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameRunner
{

    public static void main(String[] args) {
        List<MoveableObject> objects = new ArrayList<>();

        final JFrame frame = new JFrame("Asteroids");

        GameComponent gameWindow = new GameComponent();

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        frame.add(gameWindow, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }
}


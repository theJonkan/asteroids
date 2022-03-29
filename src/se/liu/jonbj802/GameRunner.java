package se.liu.jonbj802;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class GameRunner
{
    private final List<MoveableObject> objects;
    private GameComponent renderer = null;

    private class MoveAction extends AbstractAction {
        @Override public void actionPerformed(final ActionEvent e) {
            final Rocket rocket = (Rocket) objects.get(0);
            final int x = rocket.getX();
            final int y = rocket.getY();
            rocket.setPos(x, y + rocket.getSize());
            renderer.repaint();
        }
    }

    private class RotateAction extends AbstractAction {
        private final Direction direction;
        final Rocket rocket = (Rocket) objects.get(0);

        private RotateAction(final Direction direction) {
            this.direction = direction;
        }

        @Override public void actionPerformed(final ActionEvent e) {
            rocket.rotate(direction);
        }
    }

    public GameRunner(final List<MoveableObject> objects) {
        this.objects = objects;
    }

    private void show() {
        final JFrame frame = new JFrame("Asteroids");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        renderer = new GameComponent(objects);
        frame.add(renderer);
        setUpKeyMap(frame);

        frame.pack();
        frame.setVisible(true);
    }

    private void setUpKeyMap(final JFrame frame) {
        JComponent pane = frame.getRootPane();
        final InputMap in = pane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        in.put(KeyStroke.getKeyStroke("LEFT"), "LEFT");
        in.put(KeyStroke.getKeyStroke("RIGHT"), "RIGHT");
        in.put(KeyStroke.getKeyStroke("UP"), "UP");

        final ActionMap act = pane.getActionMap();
        //act.put("LEFT", new RotateAction(Direction.LEFT));
        //act.put("RIGHT", new RotateAction(Direction.RIGHT));
        act.put("UP", new MoveAction());
    }


    public static void main(String[] args) {
        final List<MoveableObject> objects = new ArrayList<>();
        objects.add(new Rocket());

        final GameRunner game = new GameRunner(objects);
        game.show();
    }
}


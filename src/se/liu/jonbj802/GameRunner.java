package se.liu.jonbj802;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameRunner
{
    private final List<MoveableObject> objects;
    private GameComponent renderer = null;
    private JFrame frame = null;

    private final static Random RND = new Random();
    private static final int DEFAULT_TIMER_DELAY = 2000;
    private static final int FRAME_TIME = 20; // 50 FPS => 20ms for each draw.

    private int saucerDelay;

    private class MoveAction extends AbstractAction {
        @Override public void actionPerformed(final ActionEvent e) {
            final Rocket rocket = (Rocket) objects.get(0);
            final int x = rocket.getX();
            final int y = rocket.getY();
            rocket.setPos(x - (int)Math.round(Math.sin(rocket.getAngle()) * rocket.getSize()), y + (int)Math.round(Math.cos(rocket.getAngle()) * rocket.getSize()));
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
        frame = new JFrame("Asteroids");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        renderer = new GameComponent(objects);
        frame.add(renderer);
        setUpKeyMap(frame);

        frame.pack();
        frame.setVisible(true);

        objects.add(new Asteroid(frame.getBounds().getSize()));
    }

    private void setUpKeyMap(final JFrame frame) {
        JComponent pane = frame.getRootPane();
        final InputMap in = pane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        in.put(KeyStroke.getKeyStroke("LEFT"), "LEFT");
        in.put(KeyStroke.getKeyStroke("RIGHT"), "RIGHT");
        in.put(KeyStroke.getKeyStroke("UP"), "UP");

        final ActionMap act = pane.getActionMap();
        act.put("LEFT", new RotateAction(Direction.LEFT));
        act.put("RIGHT", new RotateAction(Direction.RIGHT));
        act.put("UP", new MoveAction());
    }

    private void startSpawner() {
        final Timer timer = new Timer(DEFAULT_TIMER_DELAY, e -> {
            final Dimension windowSize = frame.getBounds().getSize();

            objects.add(new Asteroid(windowSize));

            if (RND.nextBoolean() && saucerDelay >= 10) {
                objects.add(new Saucer(windowSize));
                saucerDelay = 0;
            }

            saucerDelay++;
        });
        timer.setCoalesce(true);
        timer.start();
    }

    private void startRunner() {
        final Timer timer = new Timer(FRAME_TIME, e -> {
            renderer.repaint();
        });
        timer.setCoalesce(true);
        timer.start();
    }

    public static void main(String[] args) {
        final List<MoveableObject> objects = new ArrayList<>();
        objects.add(new Rocket());

        final GameRunner game = new GameRunner(objects);
        game.show();

        game.startRunner();
        game.startSpawner();
    }
}


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
    private static final int DEFAULT_TIMER_DELAY = 1500;
    private static final int FRAME_TIME = 20; // 50 FPS => 20ms for each draw.

    private int saucerDelay;

    private class UpPressAction extends AbstractAction {
        @Override public void actionPerformed(final ActionEvent e) {
            final Rocket rocket = (Rocket) objects.get(0);

            rocket.upPress();
        }
    }

    private class LeftRightPressAction extends AbstractAction {
        private final Direction direction;
        final Rocket rocket = (Rocket) objects.get(0);

        private LeftRightPressAction(final Direction direction) {
            this.direction = direction;
        }

        @Override public void actionPerformed(final ActionEvent e) {
            rocket.leftRightPress(direction);
        }
    }

    private class LeftRightReleaseAction extends AbstractAction{
        final Rocket rocket = (Rocket) objects.get(0);

        @Override public void actionPerformed(final ActionEvent e){
            rocket.leftRightRelease();
        }
    }

    private class UpReleaseAction extends AbstractAction{
        final Rocket rocket = (Rocket) objects.get(0);

        @Override public void actionPerformed(final ActionEvent e){
            rocket.upRelease();
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

        //See link for keycode list https://stackoverflow.com/questions/15313469/java-keyboard-keycodes-list
        in.put(KeyStroke.getKeyStroke(37, 0, true), "LEFT_RIGHT_RELEASE");
        in.put(KeyStroke.getKeyStroke(38, 0, true), "UP_RELEASE");
        in.put(KeyStroke.getKeyStroke(39, 0, true), "LEFT_RIGHT_RELEASE");


        final ActionMap act = pane.getActionMap();
        act.put("LEFT", new LeftRightPressAction(Direction.LEFT));
        act.put("RIGHT", new LeftRightPressAction(Direction.RIGHT));
        act.put("UP", new UpPressAction());
        act.put("LEFT_RIGHT_RELEASE", new LeftRightReleaseAction());
        act.put("UP_RELEASE", new UpReleaseAction());
    }

    private void startSpawner() {
        final Timer timer = new Timer(DEFAULT_TIMER_DELAY, e -> {
            final Dimension windowSize = frame.getBounds().getSize();

            objects.add(new Asteroid(windowSize));
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
            for (final MoveableObject object : objects) {
                object.update();

                final int x = object.getX();
                final int y = object.getY();
                final Dimension windowSize = frame.getBounds().getSize();


                /*if (object.getClass() == Asteroid.class && (x > windowSize.width || y > windowSize.height)){
                    objects.remove(object);
                }*/
            }

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


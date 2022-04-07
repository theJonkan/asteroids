package se.liu.jonbj802;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * GameRunner handles the window creation and game logic.
 */
public class GameRunner
{
    //TODO: File logging with java.logging for higher grade.

    private final List<MoveableObject> objects;
    private GameComponent renderer = null;
    private JFrame frame = null;

    private final static Random RND = new Random();
    private static final int FRAME_TIME = 20; // 50 FPS => 20ms for each draw.
    private static final int OFFSET_WRAP = 20;
    private static final int OFFSET_DELETE = 100;
    private static final int SAUCER_DELAY = 10;
    private static final int ASTEROID_DELAY = 3;

    private int frameCalls;
    private Rocket rocketPointer = null;

    private class UpAction extends AbstractAction {
        private final boolean release;

        private UpAction(final boolean release) {
            this.release = release;
        }

        @Override public void actionPerformed(final ActionEvent e) {
            if (release) {
                rocketPointer.upRelease();
                return;
            }

            rocketPointer.upPress();
        }
    }

    private class LeftRightAction extends AbstractAction {
        private final Direction direction;
        private final boolean release;


        private LeftRightAction(final Direction direction, final boolean release) {
            this.direction = direction;
            this.release = release;
        }

        @Override public void actionPerformed(final ActionEvent e) {
            if (release) {
                rocketPointer.leftRightRelease();
                return;
            }

            rocketPointer.leftRightPress(direction);
        }
    }

    private class SpacebarAction extends AbstractAction {
        @Override public void actionPerformed(final ActionEvent e) {
            final Bullet bullet = rocketPointer.shoot();
            if (bullet != null) {
                objects.add(bullet);
            }
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
    }

    private void setUpKeyMap(final JFrame frame) {
        JComponent pane = frame.getRootPane();
        final InputMap in = pane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        in.put(KeyStroke.getKeyStroke("LEFT"), "LEFT");
        in.put(KeyStroke.getKeyStroke("RIGHT"), "RIGHT");
        in.put(KeyStroke.getKeyStroke("UP"), "UP");
        in.put(KeyStroke.getKeyStroke("SPACE"), "SPACE");

        // See link for keycode list https://stackoverflow.com/questions/15313469/java-keyboard-keycodes-list.
        final int leftKey = 37;
        final int spacebarKey = 38;
        final int rightKey = 39;

        in.put(KeyStroke.getKeyStroke(leftKey, 0, true), "LEFT_RIGHT_RELEASE");
        in.put(KeyStroke.getKeyStroke(spacebarKey, 0, true), "UP_RELEASE");
        in.put(KeyStroke.getKeyStroke(rightKey, 0, true), "LEFT_RIGHT_RELEASE");


        final ActionMap act = pane.getActionMap();
        act.put("LEFT", new LeftRightAction(Direction.LEFT, false));
        act.put("RIGHT", new LeftRightAction(Direction.RIGHT, false));
        act.put("UP", new UpAction(false));
        act.put("SPACE", new SpacebarAction());
        act.put("LEFT_RIGHT_RELEASE", new LeftRightAction(null,true));
        act.put("UP_RELEASE", new UpAction(true));
    }

    private void spawnObjects() {
        final Dimension windowSize = frame.getBounds().getSize();

        double seconds = frameCalls/(1000.0/FRAME_TIME);

        if (seconds % ASTEROID_DELAY == 0){
            objects.add(new Asteroid(windowSize));
            objects.add(new Asteroid(windowSize));
        }

        // has a 50/50 chance to spawn a saucer each 10 seconds.
        if (seconds % SAUCER_DELAY == 0 && seconds != 0 && RND.nextBoolean()){

            objects.add(new Saucer(windowSize));
            frameCalls = 0; // Reset timer at longest duration.
        }

        frameCalls++;
    }

    private void updateObjects() {
        final List<MoveableObject> unwantedObjects = new ArrayList<>();
        final Dimension windowSize = frame.getBounds().getSize();


        for (final MoveableObject object : objects) {
            object.update();


            final int x = object.getX(), y = object.getY();

            // Deletes objects outside of bounds.
            if ((object.getClass() != Rocket.class) &&
                (x > windowSize.width + OFFSET_DELETE || y > windowSize.height + OFFSET_DELETE || x < -OFFSET_DELETE || y < -OFFSET_DELETE)) {
                unwantedObjects.add(object);
            }

            // Wraps around the rocket.
            if (object.getClass() == Rocket.class) {
                if (x > windowSize.width + OFFSET_WRAP) {
                    rocketPointer.setPos(-OFFSET_WRAP, y);
                } else if (y > windowSize.height + OFFSET_WRAP) {
                    rocketPointer.setPos(x, -OFFSET_WRAP);
                } else if (x < -OFFSET_WRAP) {
                    rocketPointer.setPos(windowSize.width + OFFSET_WRAP, y);
                } else if (y < -OFFSET_WRAP) {
                    rocketPointer.setPos(x, windowSize.height + OFFSET_WRAP);
                }
            }
        }

        // Remove objects that were found outside of bounds.
        for (final MoveableObject object: unwantedObjects){
            objects.remove(object);
        }


    }

    private void startRunner() {
        final Timer timer = new Timer(FRAME_TIME, e -> {
            spawnObjects();
            updateObjects();
            renderer.repaint();
        });

        timer.setCoalesce(true);
        timer.start();
    }

    public static void main(String[] args) {
        final List<MoveableObject> objects = new ArrayList<>();

        final GameRunner game = new GameRunner(objects);
        game.rocketPointer = new Rocket();
        objects.add(game.rocketPointer);
        game.show();

        game.startRunner();
    }
}


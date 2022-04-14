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
    private static final int FRAME_TIME = 20; /** 50 FPS => 20ms for each draw. */
    private static final int OFFSET_WRAP = 20;
    private static final int OFFSET_DELETE = 100;
    private static final int SAUCER_DELAY = 10;
    private static final int ASTEROID_DELAY = 3;

    private CollisionHandler collisionHandler;

    private int frameCalls;
    private Rocket rocketPointer = null;

    private class ForwardAction extends AbstractAction {
        private final boolean release;

        private ForwardAction(final boolean release) {
            this.release = release;
        }

        @Override public void actionPerformed(final ActionEvent e) {
            rocketPointer.setMovement(release);
        }
    }

    private class RotateAction extends AbstractAction {
        private final Direction direction;
        private final boolean release;


        private RotateAction(final Direction direction, final boolean release) {
            this.direction = direction;
            this.release = release;
        }

        @Override public void actionPerformed(final ActionEvent e) {
            rocketPointer.setRotation(release, direction);
        }
    }

    private class ShootAction extends AbstractAction {
        @Override public void actionPerformed(final ActionEvent e) {
            final Bullet bullet = rocketPointer.shoot();
            if (bullet != null) {
                objects.add(bullet);
            }
        }
    }

    public GameRunner(final List<MoveableObject> objects) {
        this.objects = objects;
        this.collisionHandler = new CollisionHandler();
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
        act.put("LEFT", new RotateAction(Direction.LEFT, false));
        act.put("RIGHT", new RotateAction(Direction.RIGHT, false));
        act.put("UP", new ForwardAction(false));
        act.put("SPACE", new ShootAction());
        act.put("LEFT_RIGHT_RELEASE", new RotateAction(null,true));
        act.put("UP_RELEASE", new ForwardAction(true));
    }

    private void spawnObjects() {
        final Dimension screenSize = frame.getBounds().getSize();

        double seconds = frameCalls/(1000.0/FRAME_TIME);

        if (seconds % ASTEROID_DELAY == 0){
            objects.add(new Asteroid(screenSize));
            objects.add(new Asteroid(screenSize));
            objects.add(new Asteroid(screenSize));
            objects.add(new Asteroid(screenSize));
        }

        // has a 50/50 chance to spawn a saucer each 10 seconds.
        if (seconds % SAUCER_DELAY == 0 && seconds != 0 && RND.nextBoolean()){
            objects.add(new Saucer(screenSize));
            frameCalls = 0; // Reset timer at longest duration.
        }

        frameCalls++;
    }

    private void checkCollision(){
        final Dimension screenSize = frame.getBounds().getSize();

        // TODO: use .hit() with the in parameter being different kinds of objects, have different methods for these different objects.
        for (int i = 0; i < objects.size(); i++) {
            for (int j = 0; j < objects.size(); j++) {
                final MoveableObject object = objects.get(i);
                final MoveableObject collider = objects.get(j);
                if (object.getClass() != collider.getClass()) {
                    final Rectangle objectHitbox = object.getHitbox(screenSize);
                    final Rectangle colliderHitbox = collider.getHitbox(screenSize);

                    if (i != j && objectHitbox.intersects(colliderHitbox)) {
                        object.collided();
                        collider.collided();

                        System.out.println("object: " + objects.get(i).getClass().getName());
                        System.out.println("collider: " + objects.get(j).getClass().getName());
                    }
                }
            }
        }
    }

    private void updateObjects() {
        final List<MoveableObject> unwantedObjects = new ArrayList<>();
        final Dimension screenSize = frame.getBounds().getSize();

        for (final MoveableObject object : objects) {
            object.update();

            final Point pos = object.getPos();

            // Deletes objects outside of bounds.
            if (object.shouldDespawn(screenSize, OFFSET_DELETE)) {
                unwantedObjects.add(object);
            }

            // Wraps around objects when going out of bounds.
            if (object.shouldWrap(screenSize, OFFSET_WRAP)) {
                if (pos.x > screenSize.width + OFFSET_WRAP) {
                    object.setPos(-OFFSET_WRAP, pos.y);
                } else if (pos.y > screenSize.height + OFFSET_WRAP) {
                    object.setPos(pos.x, -OFFSET_WRAP);
                } else if (pos.x < -OFFSET_WRAP) {
                    object.setPos(screenSize.width + OFFSET_WRAP, pos.y);
                } else if (pos.y < -OFFSET_WRAP) {
                    object.setPos(pos.x, screenSize.height + OFFSET_WRAP);
                }
            }
        }

        // Remove objects that were found outside of bounds.
        for (final MoveableObject object: unwantedObjects) {
            objects.remove(object);
        }
    }

    private void startRunner() {
        final Timer timer = new Timer(FRAME_TIME, e -> {
            spawnObjects();
            updateObjects();
            checkCollision();
            renderer.repaint();
        });

        timer.setCoalesce(true);
        timer.start();
    }

    public static void main(String[] args) {
        final List<MoveableObject> objects = new ArrayList<>();

        final GameRunner game = new GameRunner(objects);
        game.show();

        game.rocketPointer = new Rocket(game.frame.getBounds().getSize());
        objects.add(game.rocketPointer);

        game.startRunner();
    }
}


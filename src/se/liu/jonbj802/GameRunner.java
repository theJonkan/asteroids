package se.liu.jonbj802;

import se.liu.jonbj802.collisions.CollisionHandler;
import se.liu.jonbj802.collisions.CollisionType;
import se.liu.jonbj802.graphics.FileHandler;
import se.liu.jonbj802.graphics.TextObject;
import se.liu.jonbj802.moveable_objects.Asteroid;
import se.liu.jonbj802.moveable_objects.GameComponent;
import se.liu.jonbj802.moveable_objects.MoveableObject;
import se.liu.jonbj802.moveable_objects.Rocket;
import se.liu.jonbj802.moveable_objects.Saucer;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * GameRunner handles the window creation and game logic.
 */
public class GameRunner implements SpawnListener
{
    private final List<MoveableObject> objects;
    private final List<MoveableObject> addQueue = new ArrayList<>();
    private GameComponent renderer = null;
    private JFrame frame = null;
    private boolean gameRunning = true;

    private final static Random RND = new Random();
    private static final int FRAME_TIME = 20; /** 50 FPS => 20ms for each draw. */
    private static final int OFFSET_WRAP = 20;
    private static final int OFFSET_DELETE = 100;
    private static final int SAUCER_DELAY = 10;
    private static final int ASTEROID_DELAY = 3;

    // TODO: We need to model this better.
    private static final int ASTEROID_POINTS = 10;
    private static final int SAUCER_POINTS = 100;

    private CollisionHandler collisionHandler;
    private final FileHandler fileHandler;

    private int frameCalls;
    private Rocket rocketPointer = null;

    public GameRunner(final List<MoveableObject> objects) {
        this.objects = objects;
        this.collisionHandler = new CollisionHandler();
        this.fileHandler = new FileHandler();
    }

    private void show() {
        frame = new JFrame("Asteroids");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        renderer = new GameComponent(objects, false);
        frame.add(renderer);
        setUpKeyMap(frame);

        frame.pack();
        frame.setVisible(true);
    }

    @Override public void spawn(final List<MoveableObject> newObjects) {
        this.addQueue.addAll(newObjects);
    }

    private void setUpCollisions() {
        collisionHandler.register(CollisionType.BULLET_PLAYER, CollisionType.ASTEROID, () -> rocketPointer.increaseScore(ASTEROID_POINTS));
        collisionHandler.register(CollisionType.BULLET_PLAYER, CollisionType.SAUCER, () -> rocketPointer.increaseScore(SAUCER_POINTS));
        collisionHandler.register(CollisionType.ROCKET, CollisionType.ASTEROID);
        collisionHandler.register(CollisionType.ROCKET, CollisionType.SAUCER);
        collisionHandler.register(CollisionType.BULLET_ENEMY, CollisionType.ROCKET);
    }

    private void setUpAssets() throws IOException {
        fileHandler.loadAll();
    }

    private void setUpKeyMap() {
        frame.addKeyListener(rocketPointer);
    }

    private void spawnObjects() {
        final Dimension screenSize = frame.getBounds().getSize();

        double seconds = frameCalls/(1000.0/FRAME_TIME);

        if (seconds % ASTEROID_DELAY == 0){
            objects.add(new Asteroid(screenSize, this, fileHandler));
            objects.add(new Asteroid(screenSize, this, fileHandler));
            objects.add(new Asteroid(screenSize, this, fileHandler));
            objects.add(new Asteroid(screenSize, this, fileHandler));
        }

        // has a 50/50 chance to spawn a saucer each 10 seconds.
        if (seconds % SAUCER_DELAY == 0 && seconds != 0 && RND.nextBoolean()){
            objects.add(new Saucer(screenSize, rocketPointer, this, fileHandler));
            frameCalls = 0; // Reset timer at longest duration.
        }

        frameCalls++;
    }

    private void findCollisions(){
        final Dimension screenSize = frame.getBounds().getSize();

        /* Once we have checked one object against all others, we can increment start
        and stop checking that going forward. We also never need to check the last element
        as all possible pairs contianing thta one have been checked by the time we get there. */

        int start = 1;
        final int lastCheck = objects.size() - 1;
        for (int i = 0; i < lastCheck; i++) {
            final MoveableObject object = objects.get(i);
            final Rectangle objectHitbox = object.getHitbox(screenSize);

            for (int j = start; j < objects.size(); j++) {
                final MoveableObject collider = objects.get(j);
                final Rectangle colliderHitbox = collider.getHitbox(screenSize);

                if (objectHitbox.intersects(colliderHitbox)) {
                    final boolean collision = collisionHandler.collide(object.getCollisionType(), collider.getCollisionType());
                    if (collision) {
                        object.collided();
                        collider.collided();
                    }
                }
            }

            start++;
        }
    }

    private void updateObjects() {
        final List<MoveableObject> unwantedObjects = new ArrayList<>();
        final Dimension screenSize = frame.getBounds().getSize();

        // Avoids concurrent modification
        objects.addAll(addQueue);
        addQueue.clear();

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
            if (rocketPointer.getHealth() <= 0){
                gameRunning = false;
            }

            if (gameRunning) {
                spawnObjects();
                updateObjects();
                findCollisions();
                renderer.repaint();
            }
        });

        timer.setCoalesce(true);
        timer.start();
    }

    public static void main(String[] args) {
        final List<MoveableObject> objects = new ArrayList<>();

        final Logger logger = Logger.getLogger("AsteroidsLog");
        try {
            final java.util.logging.FileHandler fh = new java.util.logging.FileHandler("asteroids.log");
            fh.setFormatter(new SimpleFormatter());
            logger.addHandler(fh);
            logger.addHandler(fh);
        } catch (IOException e) {
            logger.log(Level.WARNING, "Failed to set up file handler");
            e.printStackTrace();
        }

        final GameRunner game = new GameRunner(objects);

        try {
            game.setUpAssets();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to load assets");
            e.printStackTrace();
            return;
        }
        game.show();

        game.rocketPointer = new Rocket(game.frame.getBounds().getSize(), game, game.fileHandler);
        objects.add(game.rocketPointer);
        game.setUpCollisions();
        game.setUpKeyMap();

        game.startRunner();
    }
}


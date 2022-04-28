package se.liu.jonbj802;

import se.liu.jonbj802.collisions.CollisionHandler;
import se.liu.jonbj802.collisions.CollisionType;
import se.liu.jonbj802.graphics.FileHandler;
import se.liu.jonbj802.moveable_objects.Asteroid;
import se.liu.jonbj802.moveable_objects.MoveableObject;
import se.liu.jonbj802.moveable_objects.Rocket;
import se.liu.jonbj802.moveable_objects.Saucer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * GameHandler handles all the game logic as well as how it should draw.
 */
public class GameHandler extends AbstractRendererComponent implements SpawnListener, KeyListener
{
    private final List<MoveableObject> objects = new ArrayList<>();
    private final List<MoveableObject> addQueue = new ArrayList<>();

    private final static int SCORE_OFFSET = 25;
    private final static int TEXT_SIZE = 30;

    private final static Random RND = new Random();
    private static final int FRAME_TIME = 20; /** 50 FPS => 20ms for each draw. */
    private static final int OFFSET_WRAP = 20;
    private static final int OFFSET_DELETE = 100;
    private static final int SAUCER_DELAY = 10;
    private static final int ASTEROID_DELAY = 3;

    // TODO: We need to model this better.
    private static final int ASTEROID_POINTS = 10;
    private static final int SAUCER_POINTS = 100;

    private final JFrame frame;
    private final Timer timer;

    private CollisionHandler collisionHandler = new CollisionHandler();
    private final FileHandler fileHandler;

    private int frameCalls;
    private Rocket rocketPointer = null;

    protected GameHandler(final JFrame frame, final FileHandler fileHandler) {
	super();
	this.frame = frame;
	this.fileHandler = fileHandler;

	this.timer = new Timer(FRAME_TIME, e -> {
	    if (rocketPointer.getHealth() <= 0){
		stop();
	    }

	    spawnObjects();
	    updateObjects();
	    findCollisions();

	    repaint();
	});
	timer.setCoalesce(true);
    }

    public void setUpCollisions() {
	collisionHandler.register(CollisionType.BULLET_PLAYER, CollisionType.ASTEROID, () -> rocketPointer.increaseScore(ASTEROID_POINTS));
	collisionHandler.register(CollisionType.BULLET_PLAYER, CollisionType.SAUCER, () -> rocketPointer.increaseScore(SAUCER_POINTS));
	collisionHandler.register(CollisionType.ROCKET, CollisionType.ASTEROID);
	collisionHandler.register(CollisionType.ROCKET, CollisionType.SAUCER);
	collisionHandler.register(CollisionType.BULLET_ENEMY, CollisionType.ROCKET);
    }

    public boolean hasFinished() {
	return rocketPointer.getHealth() <= 0;
    }

    public boolean isPaused() {
	return timer.isRunning();
    }

    public void setPaused(final boolean pause) {
	if (pause) {
	    timer.stop();
	} else {
	    timer.start();
	}
    }

    public void start() {
	rocketPointer = new Rocket(frame.getBounds().getSize(), this, fileHandler, collisionHandler);
	objects.add(rocketPointer);
	timer.start();
    }

    public void stop() {
	timer.stop();
	addQueue.clear();
	objects.clear();
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

	// Has a 50/50 chance to spawn a saucer each 10 seconds.
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

    @Override public void spawn(final MoveableObject... newObjects) {
	this.addQueue.addAll(List.of(newObjects));
    }

    @Override protected void paintComponent(final Graphics g) {
	final Graphics2D g2d = (Graphics2D) g;

	super.paintComponent(g2d);
	paintObjects(g2d, objects);

	if (!objects.isEmpty()) {
	    g2d.setFont(new Font("serif", Font.PLAIN, TEXT_SIZE));
	    g2d.drawString(String.valueOf(rocketPointer.getScore()), SCORE_OFFSET, SCORE_OFFSET + TEXT_SIZE);
	    g2d.drawString("Lives: " + rocketPointer.getHealth(), SCORE_OFFSET, (SCORE_OFFSET + TEXT_SIZE) * 2);
	}
    }

    @Override public void keyTyped(final KeyEvent e) {}

    @Override public void keyPressed(final KeyEvent e) {
	rocketPointer.keyPressed(e);
    }

    @Override public void keyReleased(final KeyEvent e) {
	rocketPointer.keyReleased(e);
    }
}
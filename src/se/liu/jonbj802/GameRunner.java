package se.liu.jonbj802;

import se.liu.jonbj802.graphics.FileHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;
import java.util.IllegalFormatWidthException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * GameRunner handles the initialisation, window creation as well as logic for switching between the start screen and the game screen.
 */
public class GameRunner extends KeyAdapter
{
    private JFrame frame = null;
    private GameHandler gameHandler = null;
    private StartScreenComponent startScreenComponent = null;

    private final FileHandler fileHandler = new FileHandler();

    private boolean gameRunning = false;

    private void createWindow() {
	frame = new JFrame("Asteroids");
	frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	frame.addKeyListener(this);

	final URL iconPath = ClassLoader.getSystemResource("images/icon.png");
	if (iconPath == null) {
	    final Logger logger = Logger.getLogger("AsteroidsLog");
	    logger.log(Level.WARNING, "Could not find the icon. Falling back to default icon.");
	    return;
	}

	final Image icon = new ImageIcon(iconPath).getImage();
	frame.setIconImage(icon);
    }

    private void start() {
	startScreenComponent = new StartScreenComponent(fileHandler);
	frame.add(startScreenComponent);
	frame.pack();

	frame.setVisible(true);
    }

    private void setUpAssets() throws IOException {
	fileHandler.loadAll();
    }

    private void showGame() {
	frame.remove(startScreenComponent);

	gameHandler = new GameHandler(frame, fileHandler);
	gameHandler.setUpCollisions();
	gameHandler.start();

	frame.add(gameHandler.getGameScreen());
	frame.pack();
    }

    private void showStartScreen() {
	gameHandler.stop();
	frame.remove(gameHandler.getGameScreen());
	start();
    }

    @Override public void keyPressed(final KeyEvent e) {
	if (gameRunning) {
	    if (gameHandler.hasFinished() && e.getKeyCode() == KeyEvent.VK_ENTER) {
		showStartScreen();
		gameRunning = false;
		return;
	    }

	    if (e.getKeyCode() == KeyEvent.VK_ESCAPE && !gameHandler.hasFinished()) {
		gameHandler.setPaused(gameHandler.isPaused());
		return;
	    }

	    gameHandler.keyPressed(e);
	} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
	    showGame();
	    gameRunning = true;
	}
    }

    @Override public void keyReleased(final KeyEvent e) {
	if (gameRunning) {
	    gameHandler.keyReleased(e);
	}
    }

    public static void main(final String[] args) {
	final Logger logger = Logger.getLogger("AsteroidsLog");
	try {
	    final java.util.logging.FileHandler fh = new java.util.logging.FileHandler("asteroids.log");
	    fh.setFormatter(new SimpleFormatter());
	    logger.addHandler(fh);
	} catch (final IOException e) {
	    logger.log(Level.WARNING, "Failed to set up file handler");
	    e.printStackTrace();
	    // A failed file logger means that we only print to the terminal. Does not break the game.
	    // No return statement needs to be used.
	}

	final GameRunner game = new GameRunner();
	game.createWindow();

	boolean tryAgain = false;
	do {
	    try {
		game.setUpAssets();
	    } catch (final IOException e) {
		logger.log(Level.SEVERE, "Failed to load assets");
		e.printStackTrace();
		tryAgain = JOptionPane.showConfirmDialog(null, "Do you want to try again?", "Failed to load assets",
							 JOptionPane.YES_NO_OPTION) == 0;
		if (!tryAgain) {
		    return; // This is not a Catch Fallthrough. We give the user the option to try again.
		}
	    } catch (final IllegalFormatWidthException e) {
		logger.log(Level.SEVERE, "Incorrect matrix length: " + e.getWidth());
		e.printStackTrace();
		return; // We can not continue. One or more loaded assets are invalid.
	    }
	} while (tryAgain);

	game.start();
    }
}


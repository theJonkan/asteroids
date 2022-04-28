package se.liu.jonbj802;

import se.liu.jonbj802.graphics.FileHandler;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * GameRunner handles the initialisation, window creation as well as logic for
 * switching between the start screen and the game screen.
 */
public class GameRunner extends KeyAdapter
{
    private JFrame frame = null;
    private GameHandler gameHandler = null;
    private StartScreenHandler startScreenHandler = null;

    private final FileHandler fileHandler = new FileHandler();

    private boolean gameRunning = false;

    private void start() {
        frame = new JFrame("Asteroids");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        startScreenHandler = new StartScreenHandler(fileHandler);
        frame.add(startScreenHandler);
        frame.pack();

        frame.addKeyListener(this);
        frame.setVisible(true);
    }

    private void setUpAssets() throws IOException {
        fileHandler.loadAll();
    }

    private void showGame() {
        frame.remove(startScreenHandler);

        gameHandler = new GameHandler(frame, fileHandler);
        gameHandler.setUpCollisions();
        gameHandler.start();

        frame.add(gameHandler);
        frame.pack();
    }

    private void showStartScreen() {
        gameHandler.stop();
        frame.remove(gameHandler);

        startScreenHandler = new StartScreenHandler(fileHandler);
        frame.add(startScreenHandler);
        frame.pack();
    }

    @Override public void keyPressed(final KeyEvent e) {
        if (gameRunning) {
            if (gameHandler.hasFinished() && e.getKeyCode() == KeyEvent.VK_ENTER) {
                showStartScreen();
                gameRunning = false;
                return;
            }

            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                gameHandler.setPaused(gameHandler.isPaused());
                return;
            }

            gameHandler.keyPressed(e);
        } else if (e.getKeyCode() == KeyEvent.VK_ENTER){
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
        } catch (IOException e) {
            logger.log(Level.WARNING, "Failed to set up file handler");
            e.printStackTrace();
        }

        final GameRunner game = new GameRunner();
        try {
            game.setUpAssets();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to load assets");
            e.printStackTrace();
            return;
        }

        game.start();
    }
}


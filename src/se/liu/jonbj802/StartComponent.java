package se.liu.jonbj802;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class StartComponent extends JComponent
{
    public StartComponent(){}

    @Override public Dimension getPreferredSize() {
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	double preferredHeight = screenSize.getHeight();
	double preferredWidth = screenSize.getWidth();

	return new Dimension((int)preferredWidth, (int)preferredHeight);

    }


    @Override protected void paintComponent(final Graphics g) {
	final Graphics2D g2d = (Graphics2D) g;
	final Dimension size = getSize();

	final int textSize = 30;
	final int widthOffset = 100;
	final int heightOffset = 0;

	// Draw a black background.
	g2d.setColor(Color.BLACK);
	g2d.fillRect(0, 0, size.width, size.height);

	// Change rendering hints for better line quality.
	g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
	g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

	g2d.setColor(Color.WHITE);
	g2d.setFont(new Font("serif", Font.PLAIN, textSize));
	g2d.drawString("JAVA ASTEROIDS", size.width/2 - widthOffset, size.height/2 + heightOffset);
	g2d.drawString("Press enter to start the game", size.width/2 - widthOffset * 2, size.height/2 + heightOffset + textSize);

	super.paintComponent(g);
    }
}

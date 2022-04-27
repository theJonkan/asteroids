package se.liu.jonbj802;

import javax.swing.*;
import java.awt.*;

public class BackgroundComponent extends JComponent
{
    @Override public Dimension getPreferredSize() {
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	double preferredHeight = screenSize.getHeight();
	double preferredWidth = screenSize.getWidth();

	return new Dimension((int)preferredWidth, (int)preferredHeight);

    }

    @Override protected void paintComponent(final Graphics g) {
	final Graphics2D g2d = (Graphics2D) g;

	final Dimension size = getSize();

	// Draw a black background.
	g2d.setColor(Color.BLACK);
	g2d.fillRect(0, 0, size.width, size.height);

	super.paintComponent(g);
    }
}

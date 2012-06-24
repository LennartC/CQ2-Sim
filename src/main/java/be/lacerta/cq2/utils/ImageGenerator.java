package be.lacerta.cq2.utils;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class ImageGenerator {
	public static BufferedImage htmlToImage(String text) {
		JEditorPane pane = new JEditorPane();
		pane.setEditable(false);
		pane.setContentType("text/html");
		pane.setText(text);
		Dimension size = pane.getPreferredSize();
		size.setSize(size.getWidth()-6, size.getHeight()-6);
		pane.setSize(size);
		pane.setBorder(null);

		BufferedImage image = new BufferedImage(pane.getWidth(), pane.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = image.createGraphics();

		JPanel container = new JPanel();
		SwingUtilities.paintComponent(g, pane, container, 0, 0, image.getWidth(), image.getHeight());
		g.dispose();
		return image;
	}
}

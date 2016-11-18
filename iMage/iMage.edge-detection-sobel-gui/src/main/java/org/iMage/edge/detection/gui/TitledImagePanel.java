package org.iMage.edge.detection.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * Image with title
 * 
 * @author Hans-Peter Lehmann
 * @version 1.0
 */
public class TitledImagePanel extends JPanel {
	private static final long serialVersionUID = 3457291181454449945L;
	private ImagePanel imagePanel;
	private static final int PICTURE_SIZE = 300;

	/**
	 * Creates a new image panel
	 * 
	 * @param title
	 *            Title of the image
	 */
	public TitledImagePanel(String title) {
		JLabel titleLabel = new JLabel(title);
		titleLabel.setFont(new Font("Serif", Font.BOLD, 20));
		titleLabel.setAlignmentX(0.5f);
		titleLabel.setBorder(new EmptyBorder(0, 0, 10, 0));
		imagePanel = new ImagePanel();
		imagePanel.setBackground(Color.decode("#f2f2f2"));
		imagePanel.setPreferredSize(new Dimension(PICTURE_SIZE, PICTURE_SIZE));
		setLayout(new FlowLayout());
		add(titleLabel, BorderLayout.PAGE_START);
		add(imagePanel, BorderLayout.PAGE_END);
		setOpaque(false);
	}

	/**
	 * Sets the image
	 * 
	 * @param image
	 *            The image to set
	 */
	public void setImage(BufferedImage image) {

		int dWidth = 1;
		int dHeight = 1;
		
		if (image.getWidth() > image.getHeight()) {
			dWidth = PICTURE_SIZE;
			dHeight = (int) ((float) PICTURE_SIZE / (float) image.getWidth() * (float) image.getHeight());
		} else {
			dHeight = PICTURE_SIZE;
			dWidth = (int) ((float) PICTURE_SIZE / (float) image.getHeight() * (float) image.getWidth());
		}

		float fWidth = (float) dWidth / (float) image.getWidth();
		float fHeight = (float) dHeight / (float) image.getHeight();

		BufferedImage scaled = new BufferedImage(dWidth, dHeight, image.getType());
		Graphics2D g = scaled.createGraphics();
		AffineTransform at = AffineTransform.getScaleInstance(fWidth, fHeight);
		g.drawRenderedImage(image, at);

		imagePanel.image = scaled;
		imagePanel.repaint();
		imagePanel.revalidate();
	}

	/**
	 * Returns the scaled that is used as preview
	 * @return The scaled preview image
	 */
	public BufferedImage getPreviewImage() {
		return imagePanel.image;
	}

	private class ImagePanel extends JPanel {
		private static final long serialVersionUID = -4941554551767713030L;
		private BufferedImage image = null;

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);

			if (null != image) {
				g.drawImage(image, (PICTURE_SIZE - image.getWidth()) / 2, (PICTURE_SIZE - image.getHeight()) / 2, null);
			}
		}

	}
}

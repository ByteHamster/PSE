package org.iMage.edge.detection.gui;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.iMage.edge.detection.base.ImageFilter;
import org.jis.options.Options;

/**
 * Provides main window for edge detection Plugin
 * 
 * @author Hans-Peter Lehmann
 * @version 1.0
 */
public class GuiFrameForPlugin extends JFrame {
	private static final long serialVersionUID = 2919659984444506990L;

	private PreviewUpdateThread previewUpdateThread;
	private EdgeDetectionLayoutHolder layoutHolder;
	private BufferedImage fullImage;

	/**
	 * Creates a new window
	 */
	public GuiFrameForPlugin() {
		super();

		initWindow();
		buildLayout();
		loadDefaultImage();
		previewUpdateThread = new PreviewUpdateThread(layoutHolder);
		previewUpdateThread.start();
	}

	private void initWindow() {
		try {
			UIManager.setLookAndFeel(Options.getInstance().getLookAndFeel());
			SwingUtilities.updateComponentTreeUI(this);
		} catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException
				| IllegalAccessException ignored) {
			// Using default theme
		}

		this.setSize(630, 550);
		this.setResizable(false);
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.setTitle("EDGuy");
		WindowTools.centerWindow(this);
	}

	private void buildLayout() {
		layoutHolder = new EdgeDetectionLayoutHolder(EdgeDetectionFilterHelpers.getAvailableFilters());
		layoutHolder.setValuesChangedListener(new EdgeDetectionLayoutHolder.ValuesChangedListener() {
			@Override
			public void onValuesChanged() {
				previewUpdateThread.requestUpdate();
			}
		});
		getContentPane().add(layoutHolder);
	}

	private void loadDefaultImage() {
		try {
			InputStream is = getClass().getResourceAsStream("girl.png");
			ImageInputStream iis = ImageIO.createImageInputStream(is);
			ImageReader reader = ImageIO.getImageReadersByFormatName("png").next();
			reader.setInput(iis, true);
			ImageReadParam params = reader.getDefaultReadParam();
			fullImage = reader.read(0, params);
			layoutHolder.setOriginalImage(fullImage);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return List of the filters to apply
	 */
	public ArrayList<ImageFilter> getFilters() {
		return EdgeDetectionFilterHelpers.calculateFilters(layoutHolder);
	}

	/**
	 * @return The currently selected settings
	 */
	public String getConfiguration() {
		String result = "";

		result += "Blur: " + layoutHolder.isBlur() + "\n";
		result += "Filter: " + layoutHolder.getSelectedFilter() + "\n";

		if (layoutHolder.isThreshold()) {
			result += "Threshold: " + layoutHolder.getThreshold();
		} else {
			result += "Threshold: disabled";
		}
		return result;
	}
}

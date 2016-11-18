package org.iMage.edge.detection.gui;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.iMage.edge.detection.base.ImageFilter;

/**
 * Thread that updates the preview images
 * @author Hans-Peter Lehmann
 * @version 1.0
 */
public class PreviewUpdateThread extends Thread {
	private boolean previewShouldBeUpdated = true;
	private EdgeDetectionLayoutHolder layoutHolder;

	/**
	 * Creates a new updater thread
	 * @param layoutHolder The layout to update
	 */
	public PreviewUpdateThread(EdgeDetectionLayoutHolder layoutHolder) {
		this.layoutHolder = layoutHolder;
	}

	/**
	 * Request an update because data was changed
	 */
	public void requestUpdate() {
		previewShouldBeUpdated = true;
	}

	@Override
	public void run() {
		while (true) {
			if (previewShouldBeUpdated) {
				previewShouldBeUpdated = false;

				ArrayList<ImageFilter> filters = EdgeDetectionFilterHelpers.calculateFilters(layoutHolder);
				BufferedImage renderedImage = layoutHolder.getPreviewImage();
				for (int i = 0; i < filters.size(); i++) {
					renderedImage = filters.get(i).applyFilter(renderedImage);
				}
				layoutHolder.setRenderedImage(renderedImage);
			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				break;
			}
		}
	}
}

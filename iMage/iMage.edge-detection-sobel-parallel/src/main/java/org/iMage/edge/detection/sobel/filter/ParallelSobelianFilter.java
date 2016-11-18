package org.iMage.edge.detection.sobel.filter;

import java.awt.Color;
import java.awt.image.BufferedImage;

import org.iMage.edge.detection.SquareMatrix;
import org.iMage.edge.detection.base.EdgeDetectionImageFilter;

/**
 * Detects edges via the Sobel filter operator. (parallel implementation)
 */
abstract class ParallelSobelianFilter implements EdgeDetectionImageFilter {

	private SquareMatrix	operatorX;
	private SquareMatrix	operatorY;

	private int				threadCount	= 2;
	private int				padding		= 1;
	private int				matrixSize	= 1;

	@Override
	public BufferedImage applyFilter(final BufferedImage image) {
		if (null == image) {
			return null;
		}

		final BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
		padding = (getOperatorX().getSize() - 1) / 2;
		matrixSize = getOperatorX().getSize();
		Thread[] threads = new Thread[threadCount];

		int steps = (int) Math.ceil((float) image.getWidth() / threadCount);
		for (int i = 0; i < threadCount; i++) {
			final int from = (i * steps);
			final int to = Math.min((i + 1) * steps, image.getWidth());
			
			threads[i] = new Thread() {
				public void run() {
					calculateColumns(image, newImage, from, to);
				}
			};
			threads[i].start();
		}
		
		for (int i = 0; i < threadCount; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return newImage;
	}

	private void calculateColumns(BufferedImage image, BufferedImage newImage, int from, int to) {
		for (int x = from; x < to; x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				if (x < padding || x >= image.getWidth() - padding || y < padding || y >= image.getHeight() - padding) {
					// Keep original color on border
					newImage.setRGB(x, y, image.getRGB(x, y));
				} else {
					BufferedImage subImage = image.getSubimage(x - padding, y - padding, matrixSize, matrixSize);

					// Channel does not matter (grayscale). Using red here.
					int filterX = BoxFilter.calculatePixel(getOperatorX(),
							BoxFilter.imageToMatrix(subImage, BoxFilter.CHANNEL_RED));
					int filterY = BoxFilter.calculatePixel(getOperatorY(),
							BoxFilter.imageToMatrix(subImage, BoxFilter.CHANNEL_RED));
					int alpha = new Color(image.getRGB(x, y)).getAlpha();

					int finalColor = (int) Math.floor(Math.sqrt(Math.pow(filterX, 2) + Math.pow(filterY, 2)));

					if (finalColor > 255) {
						finalColor = 255;
					}

					newImage.setRGB(x, y, new Color(finalColor, finalColor, finalColor, alpha).getRGB());
				}
			}
		}
	}

	/**
	 * @return the operatorX
	 */
	public SquareMatrix getOperatorX() {
		return operatorX;
	}

	/**
	 * @param operatorX
	 *            the operatorX to set
	 */
	public void setOperatorX(SquareMatrix operatorX) {
		this.operatorX = operatorX;
	}

	/**
	 * @return the operatorY
	 */
	public SquareMatrix getOperatorY() {
		return operatorY;
	}

	/**
	 * @param operatorY
	 *            the operatorY to set
	 */
	public void setOperatorY(SquareMatrix operatorY) {
		this.operatorY = operatorY;
	}

	/**
	 * @param threads
	 *            Number of threads to use
	 */
	public void setThreadCount(int threads) {
		this.threadCount = threads;
	}

	/**
	 * @return Number of threads to use
	 */
	public int getThreadCount() {
		return this.threadCount;
	}
}
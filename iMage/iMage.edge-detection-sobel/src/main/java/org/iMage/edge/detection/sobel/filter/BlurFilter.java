package org.iMage.edge.detection.sobel.filter;

import java.awt.Color;
import java.awt.image.BufferedImage;

import org.iMage.edge.detection.SquareMatrix;
import org.iMage.edge.detection.base.ImageFilter;

/**
 * Implements the blur filter as requested on worksheet 2.
 */
public class BlurFilter implements ImageFilter {
    
    private SquareMatrix weightMatrix;

    /** Default constructor must be available! */
	public BlurFilter() {
	    double n = 1d / 9d;
	    weightMatrix = new SquareMatrix(new double[][] {
            {n, n, n},
            {n, n, n},
            {n, n, n},
	    });
	}
	
	/**
     * @return The weightMatrix
     */
    public SquareMatrix getWeightMatrix() {
        return weightMatrix;
    }

    /**
     * @param weightMatrix The weightMatrix to set
     */
    public void setWeightMatrix(SquareMatrix weightMatrix) {
        if (weightMatrix.getSize() % 2 != 0) {
            throw new IllegalArgumentException("Matrix size must be uneven");
        }
        this.weightMatrix = weightMatrix;
    }

	@Override
	public BufferedImage applyFilter(BufferedImage image) {
        if (null == image) {
            return null;
        }
        
        BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        final int padding = (getWeightMatrix().getSize() - 1) / 2;
        final int matrixSize = getWeightMatrix().getSize();
        
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                if (x < padding || x >= image.getWidth() - padding 
                        || y < padding || y >= image.getHeight() - padding) {
                    // Keep original color on border
                    newImage.setRGB(x, y, image.getRGB(x, y));
                } else {
                    BufferedImage subImage = image.getSubimage(x - padding, y - padding, matrixSize, matrixSize);
                    int r = BoxFilter.calculatePixel(getWeightMatrix(), 
                            BoxFilter.imageToMatrix(subImage, BoxFilter.CHANNEL_RED));
                    int g = BoxFilter.calculatePixel(getWeightMatrix(),
                            BoxFilter.imageToMatrix(subImage, BoxFilter.CHANNEL_GREEN));
                    int b = BoxFilter.calculatePixel(getWeightMatrix(),
                            BoxFilter.imageToMatrix(subImage, BoxFilter.CHANNEL_BLUE));
                    int a = BoxFilter.calculatePixel(getWeightMatrix(),
                            BoxFilter.imageToMatrix(subImage, BoxFilter.CHANNEL_ALPHA));
                    newImage.setRGB(x, y, new Color(r, g, b, a).getRGB());
                }
            }
        }
		return newImage;
	}

}

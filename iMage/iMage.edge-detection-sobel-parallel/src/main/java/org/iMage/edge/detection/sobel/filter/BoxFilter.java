package org.iMage.edge.detection.sobel.filter;

import java.awt.Color;
import java.awt.image.BufferedImage;

import org.iMage.edge.detection.SquareMatrix;

/**
 * Provides basic functions for a box filter
 * @author Hans-Peter
 * @version 1.0
 */
public final class BoxFilter {

    /**
     * The red channel
     */
    public static final int CHANNEL_RED = 0;
    
    /**
     * The green channel
     */
    public static final int CHANNEL_GREEN = 1;
    
    /**
     * The blue channel
     */
    public static final int CHANNEL_BLUE = 2;
    
    /**
     * The alpha channel
     */
    public static final int CHANNEL_ALPHA = 3;
    
    private BoxFilter() {
        // Must not be initialized
    }

    /**
     * Converts a square image to a Matrix
     * @param image The image to convert
     * @param channel The color channel to use
     * @return The pixel values
     */
    public static SquareMatrix imageToMatrix(BufferedImage image, int channel) {
        if (image.getWidth() != image.getHeight()) {
            throw new IllegalArgumentException("Image is not a square");
        }
        SquareMatrix matrix = new SquareMatrix(image.getWidth());
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                
                Color color = new Color(image.getRGB(x, y));
                switch (channel) {
                    case CHANNEL_RED:
                        matrix.set(x, y, color.getRed());
                        break;
                    case CHANNEL_GREEN:
                        matrix.set(x, y, color.getGreen());
                        break;
                    case CHANNEL_BLUE:
                        matrix.set(x, y, color.getBlue());
                        break;
                    case CHANNEL_ALPHA:
                        matrix.set(x, y, color.getAlpha());
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid channel");
                }
            }
        }
        return matrix;
    }

    /**
     * Calculates the new pixel
     * @param weight Weighting
     * @param surroundings Surroundings of the pixel
     * @return The calculated color
     */
    public static int calculatePixel(SquareMatrix weight, SquareMatrix surroundings) {
        SquareMatrix weightedSurroundings = surroundings.multiplyElementWise(weight);
        double pixelValue = weightedSurroundings.valueSum();
        return (int) pixelValue;
    }
}

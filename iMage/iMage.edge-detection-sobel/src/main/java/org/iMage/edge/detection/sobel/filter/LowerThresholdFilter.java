package org.iMage.edge.detection.sobel.filter;

import java.awt.Color;

/**
 * Filters all pixels that have a grayscale color below a certain threshold and sets them to 0 (makes them black).
 * Pixels above the threshold are converted to grayscale normally (as defined in {@link GrayScaleFilter}).
 */
public class LowerThresholdFilter extends GrayScaleFilter {
    
    private int threshold = 127;

	/** Default constructor must be available! */
	public LowerThresholdFilter() {
	    
	}
	
	/**
	 * Initializes a new LowerThresholdFilter
	 * @param threshold The threshold to use
	 */
	public LowerThresholdFilter(int threshold) {
	    this.threshold = threshold;
	}

    @Override
    int calculatePixel(int colorIn) {
        Color c = new Color(colorIn);
        int rgbSum = c.getRed() + c.getGreen() + c.getBlue();
        int newColor = Math.round(rgbSum / 3);
        
        if (newColor < threshold) {
            newColor = 0;
        }
        
        return new Color(newColor, newColor, newColor, c.getAlpha()).getRGB();
    }

}

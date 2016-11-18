package org.iMage.edge.detection.sobel.filter;

import java.awt.Color;
import java.awt.image.BufferedImage;

import org.iMage.edge.detection.base.ImageFilter;

/**
 * Implements the GrayScaleFilter as requested on worksheet 2.
 */
public class GrayScaleFilter implements ImageFilter {

	/** Default constructor must be available! */
	public GrayScaleFilter() {
	    
	}

	@Override
	public BufferedImage applyFilter(BufferedImage image) {
	    if (null == image) {
	        return null;
	    }
	    
	    BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
	    
		for (int x = 0; x < image.getWidth(); x++) {
		    for (int y = 0; y < image.getHeight(); y++) {
	            int origColor = image.getRGB(x, y);
	            newImage.setRGB(x, y, calculatePixel(origColor));
	        }
		}
		
		return newImage;
	}
	
	/**
	 * Calculates the gray pixel from a colorful pixel
	 * @param colorIn The color of the pixel
	 * @return The gray equivalent
	 */
	int calculatePixel(int colorIn) {
	    Color c = new Color(colorIn);
	    int rgbSum = c.getRed() + c.getGreen() + c.getBlue();
	    int newColor = Math.round(rgbSum / 3);
	    return new Color(newColor, newColor, newColor, c.getAlpha()).getRGB();
	}
}

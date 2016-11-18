package org.iMage.edge.detection.sobel.filter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.awt.image.BufferedImage;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests the LowerThresholdFilter as requested on worksheet 2.
 */
public class LowerThresholdFilterTest {

    private LowerThresholdFilter lowerThresholdFilter;

    /**
     * Sets up the filter
     */
    @Before
    public void setUp() {
        lowerThresholdFilter = new LowerThresholdFilter();
    }

    /**
     * Verifies that the filter can handle a null image
     */
    @Test
    public void testNullImage() {
        assertNull(lowerThresholdFilter.applyFilter(null));
    }
    
    /**
     * Verifies that the r,g,b values never get below the threshold
     */
    @Test
    public void testThresholdValuesImage() {
        BufferedImage image = ImageUtils.getImageRessource("camera_obscura.png", "png", this.getClass());
        BufferedImage outImage = lowerThresholdFilter.applyFilter(image);
        
        assertNotNull(outImage);
        
        for (int x = 0; x < outImage.getWidth(); x++) {
            for (int y = 0; y < outImage.getHeight(); y++) {
                Color color = new Color(outImage.getRGB(x, y));
                assertEquals(color.getRed(), color.getBlue());
                assertEquals(color.getBlue(), color.getGreen());

                assertTrue(color.getRed() >= 127 || color.getRed() == 0);
            }
        }
        
        ImageUtils.saveTestImage(outImage, "png", "camera_obscura_grayscale.png");
    }
    
    /**
     * Verifies that the alpha channel is kept
     */
    @Test
    public void testAlphaImage() {
        BufferedImage image = ImageUtils.getImageRessource("girl_alpha.png", "png", this.getClass());
        BufferedImage outImage = lowerThresholdFilter.applyFilter(image);
        
        assertNotNull(outImage);
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                Color inColor = new Color(image.getRGB(x, y));
                Color outColor = new Color(outImage.getRGB(x, y));
                assertEquals(inColor.getAlpha(), outColor.getAlpha());
            }
        }
    }

}

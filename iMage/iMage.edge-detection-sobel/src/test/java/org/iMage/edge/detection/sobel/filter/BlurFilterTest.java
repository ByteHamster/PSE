package org.iMage.edge.detection.sobel.filter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.awt.Color;
import java.awt.image.BufferedImage;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests the BlurFilter as requested on worksheet 2.
 */
public class BlurFilterTest {

    private BlurFilter blurFilter;

    /**
     * Sets up the filter
     */
    @Before
    public void setUp() {
        blurFilter = new BlurFilter();
    }

    /**
     * Verifies that the filter can handle a null image
     */
    @Test
    public void testNullImage() {
        assertNull(blurFilter.applyFilter(null));
    }
    
    /**
     * Tests if the outer border stays untouched
     */
    @Test
    public void testOuterBorder() {
        BufferedImage image = ImageUtils.getImageRessource("camera_obscura.png", "png", this.getClass());
        BufferedImage outImage = blurFilter.applyFilter(image);
        
        assertNotNull(outImage);
        int padding = (blurFilter.getWeightMatrix().getSize() - 1) / 2;
        
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < padding; y++) {
                Color inColor = new Color(image.getRGB(x, y));
                Color outColor = new Color(outImage.getRGB(x, y));
                // The outer padding must stay untouched
                assertEquals(inColor, outColor);
            }
        }
        
        ImageUtils.saveTestImage(outImage, "png", "camera_obscura_blur.png");
    }

}

package org.iMage.edge.detection.sobel.filter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.awt.Color;
import java.awt.image.BufferedImage;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests the PrewittFilter as requested on worksheet 2.
 */
public class PrewittFilterTest {

    private PrewittFilter prewittFilter;
    private GrayScaleFilter grayScaleFilter;
    private LowerThresholdFilter lowerThresholdFilter;

    /**
     * Sets up the filter
     */
    @Before
    public void setUp() {
        prewittFilter = new PrewittFilter();
        grayScaleFilter = new GrayScaleFilter();
        lowerThresholdFilter = new LowerThresholdFilter();
    }

    /**
     * Verifies that the filter can handle a null image
     */
    @Test
    public void testNullImage() {
        assertNull(prewittFilter.applyFilter(null));
    }
    
    /**
     * Tests line detection of a colorful image by applying a grayscale filter first
     */
    @Test
    public void testColorfulImage() {
        BufferedImage image = ImageUtils.getImageRessource("girl.png", "png", this.getClass());
        BufferedImage grayImage = grayScaleFilter.applyFilter(image);
        BufferedImage outImage = prewittFilter.applyFilter(grayImage);
        
        assertNotNull(outImage);
        int padding = (prewittFilter.getOperatorX().getSize() - 1) / 2;
        
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < padding; y++) {
                Color grayColor = new Color(grayImage.getRGB(x, y));
                Color outColor = new Color(outImage.getRGB(x, y));
                // The outer padding must stay untouched
                assertEquals(grayColor, outColor);
            }
        }
        
        ImageUtils.saveTestImage(outImage, "png", "girl_prewitt.png");
    }
    
    /**
     * Tests line detection of the camera obscura picture, applying a lowerThresholdFilter afterwards
     */
    @Test
    public void testCameraObscura() {
        BufferedImage image = ImageUtils.getImageRessource("camera_obscura.png", "png", this.getClass());
        BufferedImage grayImage = grayScaleFilter.applyFilter(image);
        BufferedImage filterImage = prewittFilter.applyFilter(grayImage);
        BufferedImage outImage = lowerThresholdFilter.applyFilter(filterImage);
        
        assertNotNull(outImage);
        int padding = (prewittFilter.getOperatorX().getSize() - 1) / 2;
        
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < padding; y++) {
                Color grayColor = new Color(grayImage.getRGB(x, y));
                Color outColor = new Color(outImage.getRGB(x, y));
                // The outer padding must stay untouched
                assertEquals(grayColor, outColor);
            }
        }
        
        ImageUtils.saveTestImage(outImage, "png", "camera_obscura_prewitt.png");
    }
    
    /**
     * Verifies that the alpha channel is kept
     */
    @Test
    public void testAlphaImage() {
        BufferedImage image = ImageUtils.getImageRessource("girl_alpha.png", "png", this.getClass());
        BufferedImage outImage = prewittFilter.applyFilter(image);
        
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

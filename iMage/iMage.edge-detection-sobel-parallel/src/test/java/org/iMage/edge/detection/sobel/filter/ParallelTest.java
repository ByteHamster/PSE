package org.iMage.edge.detection.sobel.filter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Test;

/**
 * Tests if the parallel and the standard filters create the same image
 * @author Hans-Peter Lehmann
 * @version 1.0
 */
public class ParallelTest {
    /**
     * Tests if the parallel and the standard filters create the same image
     */
    @Test
    public void testOldFilterEquals() {
        BufferedImage image;
        try {
            image = ImageIO.read(new File("src/test/resources/girl.png"));
        } catch (IOException e) {
            e.printStackTrace();
            fail("Unable to load image");
            return;
        }
        BufferedImage outParallel = new ParallelSobelFilter().applyFilter(image);
        BufferedImage outStandard = new SobelFilter().applyFilter(image);
        
        for (int x = 0; x < outParallel.getWidth(); x++) {
            for (int y = 0; y < outParallel.getHeight(); y++) {
                assertEquals(outParallel.getRGB(x, y), outStandard.getRGB(x, y));
            }
        }
    }
}

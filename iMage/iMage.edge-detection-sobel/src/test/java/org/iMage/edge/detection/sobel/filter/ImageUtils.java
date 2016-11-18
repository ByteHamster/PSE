package org.iMage.edge.detection.sobel.filter;

import static org.junit.Assert.fail;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

/**
 * Small class that allows to load ressource images easily
 * @author Hans-Peter Lehmann
 * @version 1.0
 */
public final class ImageUtils {
    
    private ImageUtils() {
        // Must not be initialized
    }
    
    /**
     * Loads the requested image resource
     * @param filename File to load
     * @param format Format of the file
     * @param callingClass The calling class
     * @return A BufferedImage containing the loaded file
     */
    public static BufferedImage getImageRessource(String filename, String format, Class<?> callingClass) {
        try {
            InputStream is = callingClass.getResourceAsStream(filename);
            ImageInputStream iis = ImageIO.createImageInputStream(is);
            ImageReader reader = ImageIO.getImageReadersByFormatName(format).next();
            reader.setInput(iis, true);
            ImageReadParam params = reader.getDefaultReadParam();
            return reader.read(0, params);
        } catch (IOException e) {
            fail("Error while reading test picture");
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Saves a bufferedImage to target/testData to make it easier to verify the filter output
     * @param image The image to save
     * @param format Format of the image to save
     * @param filename Filename target/testData
     */
    public static void saveTestImage(BufferedImage image, String format, String filename) {
        try {
            File output = new File("target/testData/" + filename);
            output.mkdirs();
            ImageIO.write(image, format, output);
        } catch (IOException e) {
            fail("Error while saving test picture");
            e.printStackTrace();
        }
    }
}

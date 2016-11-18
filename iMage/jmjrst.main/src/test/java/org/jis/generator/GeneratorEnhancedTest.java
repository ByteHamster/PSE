package org.jis.generator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;
import java.util.zip.ZipInputStream;

import org.jis.options.Options;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Enhanced tests for Generator.java
 *
 * @author Hans-Peter Lehmann
 * @version 1.0
 */
public class GeneratorEnhancedTest {

    private Generator generator;

    /**
     * Set up the test environment
     */
    @Before
    public void setUp() {
        generator = new Generator(null, 0);
        Options.getInstance().setCopyright(false);
        Options.getInstance().setModus(Options.MODUS_QUALITY);
        Options.getInstance().setAntialiasing(true);
    }

    /**
     * Test generating a scaled version of a huge image
     */
    @Test
    public void testScalingHugeImage() {
        try {
            File input = ImageUtils.generateTestImage(5000, 2500, 0x0);
            File out = new File("./target/testData/testPictureOut.jpg");
            generator.generateImage(input, out, false, 100, 100, "");
            BufferedImage generated = ImageUtils.loadImage(out);
            assertEquals(generated.getWidth(), 100);
            assertEquals(generated.getHeight(), 50);
        } catch (IOException e) {
            fail("Unable to generate image");
            e.printStackTrace();
        }
    }

    /**
     * Test rotating an image file
     */
    @Test
    @Ignore
    public void testRotatingImageFile() {
        try {
            File input = ImageUtils.generateTestImage(100, 50, 0x0);
            generator.rotate(input);
            BufferedImage generated = ImageUtils.loadImage(input);
            assertEquals(generated.getWidth(), 50);
            assertEquals(generated.getHeight(), 100);
        } catch (IOException e) {
            fail("Unable to generate image");
            e.printStackTrace();
        }
    }

    /**
     * Test rotating an image file by 180 degrees
     */
    @Test
    @Ignore
    public void testRotatingImageFile180() {
        try {
            File input = ImageUtils.generateTestImage(100, 50, 0x0);
            generator.rotate(input, 180);
            BufferedImage generated = ImageUtils.loadImage(input);
            assertEquals(generated.getWidth(), 100);
            assertEquals(generated.getHeight(), 50);
        } catch (IOException e) {
            fail("Unable to generate image");
            e.printStackTrace();
        }
    }

    /**
     * Test rotating an image file by 360 degrees - must be the same again
     */
    @Test
    @Ignore
    public void testRotatingImageFile360Degrees() {
        try {
            File file = ImageUtils.generateTestImage(100, 50, 0x0);
            BufferedImage oldImage = ImageUtils.loadImage(file);
            generator.rotate(file, 360);
            BufferedImage newImage = ImageUtils.loadImage(file);

            for (int y = 0; y < oldImage.getHeight(); y++) {
                for (int x = 0; x < oldImage.getWidth(); x++) {
                    if (oldImage.getRGB(x, y) != newImage.getRGB(x, y)) {
                        fail("Images are not equal");
                        return;
                    }
                }
            }
        } catch (IOException e) {
            fail("Unable to generate image");
            e.printStackTrace();
        }
    }

    /**
     * Test generating a zip file with images. Verifying that the files exist
     * within the zip
     */
    @Test
    public void testCreatingZip() {
        try {
            File input1 = ImageUtils.generateTestImage(2000, 1000, 0xcc0000);
            File input2 = ImageUtils.generateTestImage(2000, 1000, 0x880000);
            File zipFile = new File("./target/testData/zipFile.zip");
            Vector<File> entries = new Vector<>();
            entries.addElement(input1);
            entries.addElement(input2);

            generator.createZip(zipFile, entries);

            InputStream theFile = new FileInputStream(zipFile);
            ZipInputStream stream = new ZipInputStream(theFile);
            String possibleInputs = input1.getName() + "|" + input2.getName();
            String file1 = stream.getNextEntry().getName();
            file1 = file1.substring(file1.lastIndexOf(File.separator) + 1);
            String file2 = stream.getNextEntry().getName();
            file2 = file2.substring(file2.lastIndexOf(File.separator) + 1);
            
            assertTrue(possibleInputs.contains(file1));
            assertTrue(possibleInputs.contains(file2));
            assertEquals(null, stream.getNextEntry());
            stream.close();
        } catch (IOException e) {
            fail("Unable to generate test image");
            e.printStackTrace();
        }
    }

    /**
     * Test generating a scaled version of an image, including a copyright
     * notice
     */
    @Test
    public void testCopyrightImage() {
        try {
            Options.getInstance().setCopyright(true);
            Options.getInstance().setCopyrightText("Test Copyright");
            File input = ImageUtils.generateTestImage(1000, 2000, 0x0);
            generator.generateImage(input, input, false, 1000, 1000, "");
            BufferedImage generated = ImageUtils.loadImage(input);
            assertEquals(generated.getWidth(), 500);
            assertEquals(generated.getHeight(), 1000);
        } catch (IOException e) {
            fail("Unable to generate image");
            e.printStackTrace();
        }
    }

    /**
     * Test rotating a null file
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRotateNullFile() {
        generator.rotate(null);
    }

    /**
     * Test rotating a null file by 180 degrees
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRotateNullFile180() {
        generator.rotate(null, 180);
    }

    /**
     * Test rotating a file that does not exist
     */
    @Test(expected = IOException.class)
    @Ignore
    public void testRotateNonExistingFile() {
        generator.rotate(new File("non-existing"), 180);
    }

    /**
     * Test rotating an image file by 20 degrees (invalid angle)
     */
    @Test(expected = IllegalArgumentException.class)
    @Ignore
    public void testRotatingImageFile20Degrees() {
        try {
            File input = ImageUtils.generateTestImage(100, 50, 0x0);
            generator.rotate(input, 20);
        } catch (IOException e) {
            fail("Unable to generate image");
            e.printStackTrace();
        }
    }

    /**
     * Test if speed modus without antialiasing generates correctly sized images
     */
    @Test
    public void testModusSpeed() {
        Options.getInstance().setModus(Options.MODUS_SPEED);
        Options.getInstance().setAntialiasing(false);
        try {
            File input = ImageUtils.generateTestImage(1000, 2000, 0x0);
            generator.generateImage(input, input, false, 1000, 1000, "");
            BufferedImage generated = ImageUtils.loadImage(input);
            assertEquals(generated.getWidth(), 500);
            assertEquals(generated.getHeight(), 1000);
        } catch (IOException e) {
            fail("Unable to generate image");
            e.printStackTrace();
        }
    }

    /**
     * Test if default modus generates correctly sized images
     */
    @Test
    public void testModusDefault() {
        Options.getInstance().setModus(Options.MODUS_DEFAULT);
        try {
            File input = ImageUtils.generateTestImage(1000, 2000, 0x0);
            generator.generateImage(input, input, false, 1000, 1000, "");
            BufferedImage generated = ImageUtils.loadImage(input);
            assertEquals(generated.getWidth(), 500);
            assertEquals(generated.getHeight(), 1000);
        } catch (IOException e) {
            fail("Unable to generate image");
            e.printStackTrace();
        }
    }
}

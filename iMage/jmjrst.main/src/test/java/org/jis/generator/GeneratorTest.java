package org.jis.generator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for Generator.java
 * 
 * @author Hans-Peter Lehmann
 * @version 1.0
 */
public class GeneratorTest {

	private Generator generator;
	private BufferedImage testPicture = null;
	private BufferedImage rotatedPicture = null;
	private final SimpleDateFormat exportFilenameDateFormat = new SimpleDateFormat("HHmmss_SSS");

	/**
	 * Set up the test environment
	 */
	@Before
	public void setUp() {
		generator = new Generator(null, 0);
		rotatedPicture = null;

		try {
			InputStream is = this.getClass().getResourceAsStream("picture.jpg");
			ImageInputStream iis = ImageIO.createImageInputStream(is);
			ImageReader reader = ImageIO.getImageReadersByFormatName("jpg").next();
			reader.setInput(iis, true);
			ImageReadParam params = reader.getDefaultReadParam();
			testPicture = reader.read(0, params);
		} catch (IOException e) {
			fail("Error while reading test picture");
			e.printStackTrace();
			return;
		}
	}

	/**
	 * Saving the rotated images after each test (if the image is not null)
	 */
	@After
	public void tearDown() {
		if (rotatedPicture != null) {
			File testDataFolder = new File("./target/testData");
			testDataFolder.mkdirs();
			final String name = "rotatedPicture_" + exportFilenameDateFormat.format(new Date()) + ".jpg";
			File imageFile = new File(testDataFolder, name);

			try {
				ImageIO.write(rotatedPicture, "jpg", imageFile);
			} catch (IOException e) {
				fail("Unable to save output file");
				e.printStackTrace();
			}
		}
	}

	/**
	 * Tests if rotateImage has problems with null images
	 */
	@Test
	public void testRotateImageNull() {
		rotatedPicture = generator.rotateImage(null, 0.0);
		assertEquals(null, rotatedPicture);
	}

	/**
	 * Tests if rotateImage changes an image that is rotated by 0 degrees
	 */
	@Test
	public void testRotateImageUnchanged() {
		BufferedImage img = new BufferedImage(50, 50, BufferedImage.TYPE_INT_RGB);
		rotatedPicture = generator.rotateImage(img, 0.0);
		assertEquals(img, rotatedPicture);
	}

	/**
	 * Tests if rotateImage throws an Exception when trying to rotate by 360
	 * degrees
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testRotate360Degrees() {
		generator.rotateImage(testPicture, 1.0);
	}

	/**
	 * Tests if rotateImage throws an Exception when trying to rotate by 360
	 * degrees, even if the image is null
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testRotate360DegreesWithNullImage() {
		generator.rotateImage(null, 1.0);
	}

	/**
	 * Rotates testPicture by 90 degrees and checks if it still is the same
	 * image
	 */
	@Test
	public void testRotate90SameImage() {
		rotatedPicture = generator.rotateImage(testPicture, Math.toRadians(90));
		assertEquals(testPicture.getWidth(), rotatedPicture.getHeight());
		assertEquals(testPicture.getHeight(), rotatedPicture.getWidth());

		for (int y = 0; y < testPicture.getHeight(); y++) {
			for (int x = 0; x < testPicture.getWidth(); x++) {
				if (testPicture.getRGB(x, y) != rotatedPicture.getRGB(rotatedPicture.getWidth() - 1 - y, x)) {
					fail("Images are not equal");
					return;
				}
			}
		}
	}

	/**
	 * Rotates testPicture by 270 degrees and checks if it still is the same
	 * image
	 */
	@Test
	public void testRotate270SameImage() {
		rotatedPicture = generator.rotateImage(testPicture, Math.toRadians(270));
		assertEquals(testPicture.getWidth(), rotatedPicture.getHeight());
		assertEquals(testPicture.getHeight(), rotatedPicture.getWidth());

		for (int y = 0; y < testPicture.getHeight(); y++) {
			for (int x = 0; x < testPicture.getWidth(); x++) {
				if (testPicture.getRGB(x, y) != rotatedPicture.getRGB(y, rotatedPicture.getHeight() - 1 - x)) {
					fail("Images are not equal");
					return;
				}
			}
		}
	}
}

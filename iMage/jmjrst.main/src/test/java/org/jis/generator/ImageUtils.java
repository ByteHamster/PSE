package org.jis.generator;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

/**
 * Utility class for simple ImageIO operations that can be used in unit tests
 *
 * @author Hans-Peter Lehmann
 * @version 1.0
 */
public final class ImageUtils {
	private static final SimpleDateFormat EXPORT_DATE_FORMAT = new SimpleDateFormat("HHmmss_SSS");

	private ImageUtils() {
		// Utility class must not be initialized
	}

	/**
	 * Generates an image file with test colors
	 *
	 * @param width Width of the generated image
	 * @param height Height of the generated image
	 * @param seed Slightly change colors of test image
	 * @return The image file
	 * @throws IOException In case the file can not be saved
	 */
	public static File generateTestImage(int width, int height, int seed) throws IOException {
		return generateTestImage(width, height, seed, "./target/testData");
	}

	/**
	 * Generates an image file with test colors
	 *
	 * @param width Width of the generated image
	 * @param height Height of the generated image
	 * @param seed Slightly change colors of test image
	 * @param path Path of the test file
	 * @return The image file
	 * @throws IOException In case the file can not be saved
	 */
	public static File generateTestImage(int width, int height, int seed, String path) throws IOException {
		BufferedImage randomTestPicture = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int y = 0; y < randomTestPicture.getHeight(); y++) {
			for (int x = 0; x < randomTestPicture.getWidth(); x++) {
				randomTestPicture.setRGB(x, y, x % 0x100 + (y % 0x100) * 0x100 + seed);
			}
		}

		File testDataFolder = new File(path);
		testDataFolder.mkdirs();
		final String name = "testPicture_" + EXPORT_DATE_FORMAT.format(new Date()) + ".jpg";
		File randomTestFile = new File(testDataFolder, name);
		ImageIO.write(randomTestPicture, "jpg", randomTestFile);
		return randomTestFile;
	}

	/**
	 * Loads a jpg image from the file system
	 *
	 * @param file The image to load
	 * @return A BufferedImage object that contains the image
	 * @throws IOException If the file can not be read
	 */
	public static BufferedImage loadImage(File file) throws IOException {
		ImageInputStream iis = ImageIO.createImageInputStream(file);
		ImageReader reader = ImageIO.getImageReadersByFormatName("jpg").next();
		reader.setInput(iis, true);
		ImageReadParam params = reader.getDefaultReadParam();
		return reader.read(0, params);
	}
}

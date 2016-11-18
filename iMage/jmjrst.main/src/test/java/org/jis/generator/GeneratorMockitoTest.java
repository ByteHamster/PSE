package org.jis.generator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Locale;

import org.jis.Main;
import org.jis.Messages;
import org.jis.view.List;
import org.jis.view.Status;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Mockito tests for Generator.java
 *
 * @author Hans-Peter Lehmann
 * @version 1.0
 */
@RunWith(MockitoJUnitRunner.class)
public class GeneratorMockitoTest {

	@Mock
	Main main;

	/**
	 * Set up the test environment
	 */
	@Before
	public void setUp() {
		main = mock(Main.class);
		main.mes = new Messages(new Locale("en"));
		main.generator = new Generator(main, 0);
		main.list = mock(List.class);
		main.status = new Status(main);
	}

	/**
	 * Test generateText function using a folder
	 */
	@Test
	@Ignore
	public void testGenerateTextFolder() {
		try {
			File inDir = new File("./target/testData/inFolder");
			deleteDirectory(inDir);
			inDir.mkdirs();
			ImageUtils.generateTestImage(1000, 500, 0xcc0000, inDir.getPath());
			ImageUtils.generateTestImage(500, 1000, 0xcc0000, inDir.getPath());

			File outDir = new File("./target/testData/outFolder");
			deleteDirectory(outDir);
			outDir.mkdirs();

			main.generator.generateText(inDir, outDir, 50, 50);

			for (File f : outDir.listFiles()) {
				BufferedImage img = ImageUtils.loadImage(f);
				assertTrue("Image width & height must be <= 50", img.getHeight() <= 50 && img.getWidth() <= 50);
			}
		} catch (IOException e) {
			fail("Unable to create test files");
			e.printStackTrace();
		}
	}

	/**
	 * Test generateText function using a file
	 */
	@Test
	@Ignore
	public void testGenerateTextSingleFile() {
		try {
			File inFile = ImageUtils.generateTestImage(1000, 500, 0xcc0000);
			File out = new File("./target/testData/testPictureOut.jpg");
			main.generator.generateText(inFile, out, 100, 100);
			BufferedImage img = ImageUtils.loadImage(out);
			assertEquals(100, img.getWidth());
			assertEquals(50, img.getHeight());
		} catch (IOException e) {
			fail("Unable to create test files");
			e.printStackTrace();
		}
	}
	
	/**
	 * Delete a directory recursively
	 *
	 * @param directory The directory to delete
	 */
	private static void deleteDirectory(File directory) {
		if (directory.exists()) {
			File[] files = directory.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					deleteDirectory(files[i]);
				} else {
					files[i].delete();
				}
			}
		}
		directory.delete();
	}
}

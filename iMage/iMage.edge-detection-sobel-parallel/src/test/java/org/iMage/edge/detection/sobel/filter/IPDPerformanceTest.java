package org.iMage.edge.detection.sobel.filter;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

import org.apache.commons.lang.time.StopWatch;
import org.iMage.edge.detection.base.ImageFilter;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Performanzermittlung
 * 
 * @author tk
 * @author Mathias
 */
public class IPDPerformanceTest {

	/** Anzahl der Messläufe. */
	private static final int NUMLOOPS = 1;

	/** Alle zu testenden Fadenanzahlen. */
	private Integer[] threads = { 8 };
	// Na ja, sieht so aus als wäre meine Implementierung nicht so arg performant...
	// Mit den vorgegebenen Tests rechnet mein PC 23 Minuten...
	// Die Verbesserung durch multi threading ist vorhanden,
	// aber da lässt sich wohl auch nichts mehr rausholen

	private static BufferedImage testImage;

	@BeforeClass
	public static void setUp() throws IOException {
		testImage = ImageIO.read(new File("src/test/resources/Luxury-villa.jpg"));
	}

	@Test
	public void testPerformance() {
		long startTime, endTime;
		long[] measurements = new long[threads.length];

		System.out.println("Performanzmessung");
		for (int t = 0; t < threads.length; t++) {
			System.out.println("Starte Messung für " + threads[t] + " Fäden");
			int curThreadCount = threads[t];
			ImageFilter filter = new ParallelSobelFilter(curThreadCount);

			// Für mehr Messgenauigkeit :)
			StopWatch sw = new StopWatch();
			for (int loop = 0; loop < NUMLOOPS; loop++) {
				// Start der Messung
				sw.reset();
				sw.start();
				BufferedImage destImage = filter.applyFilter(testImage);

				// Ende der Messung
				sw.stop();
				measurements[t] += sw.getTime();
			}
			measurements[t] /= NUMLOOPS;
		}
		System.out.println("Messergebnisse:");
		System.out.println(Arrays.toString(measurements));
	}
}

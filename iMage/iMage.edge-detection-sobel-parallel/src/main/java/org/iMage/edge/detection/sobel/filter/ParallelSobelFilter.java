package org.iMage.edge.detection.sobel.filter;

import org.iMage.edge.detection.SquareMatrix;
import org.iMage.edge.detection.base.EdgeDetectionImageFilter;
import org.kohsuke.MetaInfServices;

/**
 * Detects edges via the Sobel filter operator. (parallel implementation)
 */
@MetaInfServices(EdgeDetectionImageFilter.class)
public class ParallelSobelFilter extends ParallelSobelianFilter {

	public ParallelSobelFilter() {
        this(Runtime.getRuntime().availableProcessors());
	}

	/** Default constructor must be available! */
	public ParallelSobelFilter(int threads) {
		setThreadCount(threads);
        setOperatorX(new SquareMatrix(new double[][] {
            {-1, 0, 1},
            {-2, 0, 2},
            {-1, 0, 1},
        }));
        setOperatorY(new SquareMatrix(new double[][] {
            {-1, -2, -1},
            {0,   0,  0},
            {1,   2,  1},
        }));
	}
}
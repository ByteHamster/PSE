package org.iMage.edge.detection.sobel.filter;

import org.iMage.edge.detection.SquareMatrix;
import org.iMage.edge.detection.base.EdgeDetectionImageFilter;
import org.kohsuke.MetaInfServices;

/**
 * Detects edges via the Prewitt filter operator. (parallel implementation)
 */
@MetaInfServices(EdgeDetectionImageFilter.class)
public class ParallelPrewittFilter extends ParallelSobelianFilter {

	public ParallelPrewittFilter() {
        this(Runtime.getRuntime().availableProcessors());
	}
	
	
	public ParallelPrewittFilter(int threads) {
		setThreadCount(threads);
        setOperatorX(new SquareMatrix(new double[][] {
            {-1, 0, 1},
            {-1, 0, 1},
            {-1, 0, 1},
        }));
        setOperatorY(new SquareMatrix(new double[][] {
            {-1, -1, -1},
            {0,   0,  0},
            {1,   1,  1},
        }));
	}
}

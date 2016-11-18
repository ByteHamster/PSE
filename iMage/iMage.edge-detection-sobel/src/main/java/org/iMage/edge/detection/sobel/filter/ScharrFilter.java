package org.iMage.edge.detection.sobel.filter;

import org.iMage.edge.detection.SquareMatrix;
import org.iMage.edge.detection.base.EdgeDetectionImageFilter;
import org.kohsuke.MetaInfServices;

/**
 * Detects edges via the Prewitt filter operator.
 */
@MetaInfServices(EdgeDetectionImageFilter.class)
public class ScharrFilter extends SobelianFilter {

	/** Default constructor must be available! */
	public ScharrFilter() {
        setOperatorX(new SquareMatrix(new double[][] {
            { 3, 0,  -3},
            {10, 0, -10},
            { 3, 0,  -3},
        }));
        setOperatorY(new SquareMatrix(new double[][] {
            { 3,  10,  3},
            { 0,   0,  0},
            {-3, -10, -3},
        }));
	}
}

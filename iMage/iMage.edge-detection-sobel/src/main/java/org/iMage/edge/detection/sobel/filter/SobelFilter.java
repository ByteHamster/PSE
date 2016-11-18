package org.iMage.edge.detection.sobel.filter;

import java.awt.Color;
import java.awt.image.BufferedImage;

import org.iMage.edge.detection.SquareMatrix;
import org.iMage.edge.detection.base.EdgeDetectionImageFilter;
import org.kohsuke.MetaInfServices;

/**
 * Detects edges via the Sobel filter operator.
 */
@MetaInfServices(EdgeDetectionImageFilter.class)
public class SobelFilter extends SobelianFilter {

    private SquareMatrix operatorX;
    private SquareMatrix operatorY;

	/** Default constructor must be available! */
	public SobelFilter() {
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

    @Override
	public BufferedImage applyFilter(BufferedImage image) {
        if (null == image) {
            return null;
        }
        
        BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        final int padding = (getOperatorX().getSize() - 1) / 2;
        final int matrixSize = getOperatorX().getSize();
        
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                if (x < padding || x >= image.getWidth() - padding 
                        || y < padding || y >= image.getHeight() - padding) {
                    // Keep original color on border
                    newImage.setRGB(x, y, image.getRGB(x, y));
                } else {
                    BufferedImage subImage = image.getSubimage(x - padding, y - padding, matrixSize, matrixSize);
                    
                    // Channel does not matter (grayscale). Using red here.
                    int filterX = BoxFilter.calculatePixel(getOperatorX(), 
                            BoxFilter.imageToMatrix(subImage, BoxFilter.CHANNEL_RED));
                    int filterY = BoxFilter.calculatePixel(getOperatorY(),
                            BoxFilter.imageToMatrix(subImage, BoxFilter.CHANNEL_RED));
                    int alpha = new Color(image.getRGB(x, y)).getAlpha();
                    
                    int finalColor = (int) Math.floor(Math.sqrt(Math.pow(filterX, 2) + Math.pow(filterY, 2)));
                    
                    if (finalColor > 255) {
                        finalColor = 255;
                    }
                    
                    newImage.setRGB(x, y, new Color(finalColor, finalColor, finalColor, alpha).getRGB());
                }
            }
        }
        return newImage;
	}

    /**
     * @return the operatorX
     */
    public SquareMatrix getOperatorX() {
        return operatorX;
    }

    /**
     * @param operatorX the operatorX to set
     */
    public void setOperatorX(SquareMatrix operatorX) {
        this.operatorX = operatorX;
    }

    /**
     * @return the operatorY
     */
    public SquareMatrix getOperatorY() {
        return operatorY;
    }

    /**
     * @param operatorY the operatorY to set
     */
    public void setOperatorY(SquareMatrix operatorY) {
        this.operatorY = operatorY;
    }
}
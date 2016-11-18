package org.iMage.edge.detection.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ServiceLoader;
import java.util.SortedSet;
import java.util.TreeSet;

import org.iMage.edge.detection.base.EdgeDetectionImageFilter;
import org.iMage.edge.detection.base.ImageFilter;
import org.iMage.edge.detection.sobel.filter.BlurFilter;
import org.iMage.edge.detection.sobel.filter.GrayScaleFilter;
import org.iMage.edge.detection.sobel.filter.LowerThresholdFilter;

/**
 * Utility functions to handle available filters
 * 
 * @author Hans-Peter Lehmann
 * @version 1.0
 */
public final class EdgeDetectionFilterHelpers {
	private static HashMap<String, EdgeDetectionImageFilter> availableFilters = loadFilterServices();

	private EdgeDetectionFilterHelpers() {
		// Must not be instantiated
	}

	private static HashMap<String, EdgeDetectionImageFilter> loadFilterServices() {
	    HashMap<String, EdgeDetectionImageFilter> result = new HashMap<String, EdgeDetectionImageFilter>();
	    
	    ServiceLoader<EdgeDetectionImageFilter> filters = ServiceLoader.load(EdgeDetectionImageFilter.class);
        for (EdgeDetectionImageFilter filter : filters) {
            result.put(filter.getClass().getSimpleName().replaceAll("(.)([A-Z])", "$1 - $2"), filter);
        }
        return result;
    }

    /**
	 * @return A list of the filters that the app supports
	 */
	public static String[] getAvailableFilters() {
	    SortedSet<String> keys = new TreeSet<String>(availableFilters.keySet());
	    String[] result = new String[keys.size()];
	    int i = 0;
	    for (String key : keys) { 
	       result[i] = key;
	       i++;
	    }
	    return result;
	}

	/**
	 * Returns an array of filters to apply
	 * 
	 * @param layoutHolder
	 *            The layout that contains the settings
	 * @return Array list of the filters to be applied under the given settings
	 */
	public static ArrayList<ImageFilter> calculateFilters(EdgeDetectionLayoutHolder layoutHolder) {
		ArrayList<ImageFilter> filters = new ArrayList<>();
		filters.add(new GrayScaleFilter());

		if (layoutHolder.isBlur()) {
			filters.add(new BlurFilter());
		}

		filters.add(availableFilters.get(layoutHolder.getSelectedFilter()));

		if (layoutHolder.isThreshold()) {
			filters.add(new LowerThresholdFilter(layoutHolder.getThreshold()));
		}

		return filters;
	}

}

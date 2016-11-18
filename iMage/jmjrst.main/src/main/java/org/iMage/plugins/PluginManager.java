package org.iMage.plugins;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;

/**
 * Knows all available plugins and is responsible for using the service loader
 * API to detect them.
 *
 */
public final class PluginManager {

	/**
	 * No constructor for utility class.
	 */
	private PluginManager() {
	}

	/**
	 * @return all available plugins sorted alphabetically by their name in
	 *         ascending order.
	 */
	public static List<JmjrstPlugin> getPlugins() {
	    ServiceLoader<JmjrstPlugin> loader = ServiceLoader.load(JmjrstPlugin.class);
	    List<JmjrstPlugin> list = new ArrayList<>();

        try {
            Iterator<JmjrstPlugin> dictionaries = loader.iterator();
            while (dictionaries.hasNext()) {
                list.add(dictionaries.next());
            }
        } catch (ServiceConfigurationError serviceError) {
            serviceError.printStackTrace();

        }
        
        Collections.sort(list);
		return list;
	}
}

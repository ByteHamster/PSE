package org.iMage.plugins;

/**
 * Abstract parent class for plug ins for JMJRST
 */
public abstract class JmjrstPlugin implements Comparable<JmjrstPlugin> {

	/**
	 * @return The text for the menu labels for the plugin
	 */
	public abstract String getMenuText();

	/**
	 * 
	 * @return the name of the plug in
	 */
	public abstract String getName();

	/**
	 * JMJRST pushes the main application to every subclass - so plugins are
	 * allowed to look at Main as well.
	 * 
	 * @param main
	 *            JMJRST main application
	 */
	public abstract void init(org.jis.Main main);

	/**
	 * Run plugin
	 */
	public abstract void run();

	/**
	 * @return true if the plugin can be configured.
	 */
	public abstract boolean isConfigurable();

	/**
	 * Open a configuration dialogue.
	 */
	public abstract void configure();

	/**
	 * Implement comparator functionality - plug-ins are compared on basis of
	 * their names.
	 * 
	 * Compares this plugin with the specified plugin for order. Returns a
	 * negative integer, zero, or a positive integer as this object is less
	 * than, equal to, or greater than the specified object.
	 * 
	 * @param otherPlugin
	 *            the plugin that we should compare us to
	 * 
	 */
	@Override
	public int compareTo(JmjrstPlugin otherPlugin) {
		return getName().compareTo(otherPlugin.getName());
	}
}

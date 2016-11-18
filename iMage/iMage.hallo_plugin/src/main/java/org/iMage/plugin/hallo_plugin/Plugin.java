package org.iMage.plugin.hallo_plugin;

import org.iMage.plugins.JmjrstPlugin;
import org.jis.Main;
import org.kohsuke.MetaInfServices;

/**
 * A first "hello world" plugin
 * 
 * @author Hans-Peter Lehmann
 * @version 1.0
 */
@MetaInfServices
public class Plugin extends JmjrstPlugin {

    Main main;

    @Override
    public void configure() {
        new ConfigurationDialog(main);
    }

    @Override
    public String getMenuText() {
        return getName();
    }

    @Override
    public String getName() {
        return "Hallo-SWT1-Plugin";
    }

    @Override
    public void init(Main arg0) {
        main = arg0;
        System.out.println("Ich initialisiere mich fleißig!");
    }

    @Override
    public boolean isConfigurable() {
        return true;
    }

    @Override
    public void run() {
        System.err.println("iMage - nur echt mit JMJRST!");
    }

}

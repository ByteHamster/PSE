/*
 * Copyright 2007 - 2009 Johannes Geppert 
 * 
 * Licensed under the GPL, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License.
 * 
 * You may obtain a copy of the License at
 * http://www.fsf.org/licensing/licenses/gpl.txt 
 * 
 * Unless required by applicable law or agreed to in writing, 
 * software distributed under the License is distributed on 
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the 
 * specific language governing permissions and limitations under the License.
 */
package org.jis.view;

import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;

import org.jis.Main;
import org.jis.listner.MenuListner;

/**
 * @author <a href="http://www.jgeppert.com">Johannes Geppert</a>
 * 
 * <p>
 * This is Toolbar of the GUI
 * </p>
 */
public class Toolbar extends JToolBar {
    private static final long serialVersionUID = 1232107393895691717L;

    public JButton            gener;
    public JButton            zippen;
    public JButton            gallerie;
    public JButton            preferences;

    /**
     * @param m a reference to the Main class
     */
    public Toolbar(Main m) {
        super();

        gener = new JButton(m.mes.getString("Menu.4"));
        URL url = ClassLoader.getSystemResource("icons/media-playback-start.png");
        gener.setIcon(new ImageIcon(url));

        zippen = new JButton(m.mes.getString("Menu.13"));
        url = ClassLoader.getSystemResource("icons/package-x-generic.png");
        zippen.setIcon(new ImageIcon(url));

        gallerie = new JButton(m.mes.getString("Menu.14"));
        url = ClassLoader.getSystemResource("icons/text-html.png");
        gallerie.setIcon(new ImageIcon(url));

        preferences = new JButton(m.mes.getString("Menu.6"));
        url = ClassLoader.getSystemResource("icons/preferences-system.png");
        preferences.setIcon(new ImageIcon(url));

        gener.setEnabled(false);
        zippen.setEnabled(false);
        gallerie.setEnabled(false);

        this.add(gener);
        this.add(zippen);
        this.add(gallerie);
        this.add(preferences);

        MenuListner al = new MenuListner(m, m.menu);
        gener.addActionListener(al);
        zippen.addActionListener(al);
        gallerie.addActionListener(al);
        preferences.addActionListener(al);
    }
}

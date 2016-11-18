/*
 * Copyright 2007 Johannes Geppert 
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
package org.jis.listner;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.jis.Main;
import org.jis.options.Options;
import org.jis.view.Menu;
import org.jis.view.dialog.AboutBox;
import org.jis.view.dialog.GallerieDialog;

/**
 * @author <a href="http://www.jgeppert.com">Johannes Geppert</a>
 * 
 * <p>
 * The Listner for the MenuItems.
 * </p>
 */
public class MenuListner implements ActionListener {
    static String metalClassName      = "javax.swing.plaf.metal.MetalLookAndFeel";
    static String motifClassName      = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
    static String nimbusClassName     = "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel";
    static String winClassName        = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
    static String winClassicClassName = "com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel";
    static String gtkClassName        = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";

    Options       o;
    Main          m;
    Menu          menu;

    /**
     * @param m the Main Class
     * @param me the Menu
     */
    public MenuListner(Main m, Menu me) {
        super();
        this.m = m;
        this.o = Options.getInstance();
        menu = me;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == menu.info) {
            new AboutBox(m);
            System.gc();
        } else if (e.getSource() == menu.gener || e.getSource() == m.toolBar.gener)
            m.generator.generate(false);
        else if (e.getSource() == menu.zippen || e.getSource() == m.toolBar.zippen)
            m.generator.generate(true);
        else if (e.getSource() == menu.gallerie || e.getSource() == m.toolBar.gallerie)
            new GallerieDialog(m);
        else if (e.getSource() == menu.exit)
            System.exit(0);
        else if (e.getSource() == menu.set_quality || e.getSource() == m.toolBar.preferences)
            m.openOptions();
        else if (e.getSource() == menu.look_windows) {
            m.setLookFeel(winClassName);
            o.setLookAndFeel(winClassName);
        } else if (e.getSource() == menu.look_windows_classic) {
            m.setLookFeel(winClassicClassName);
            o.setLookAndFeel(winClassicClassName);
        } else if (e.getSource() == menu.look_nimbus) {
            m.setLookFeel(nimbusClassName);
            o.setLookAndFeel(nimbusClassName);
        } else if (e.getSource() == menu.look_metal) {
            m.setLookFeel(metalClassName);
            o.setLookAndFeel(metalClassName);
        } else if (e.getSource() == menu.look_motif) {
            m.setLookFeel(motifClassName);
            o.setLookAndFeel(motifClassName);
        } else if (e.getSource() == menu.look_gtk) {
            m.setLookFeel(gtkClassName);
            o.setLookAndFeel(gtkClassName);
        } else if (e.getSource() == menu.update_check) {
            try {
                URL url = new URL("http://www.jgeppert.com/jmjrst/jmjrst_version.txt");
                URLConnection uc = url.openConnection();
                uc.connect();
                InputStream is = uc.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String line;
                line = br.readLine();

                br.close();
                isr.close();
                is.close();

                if (line != null) {
                    System.out.println(line);
                    if (!line.equalsIgnoreCase(m.mes.getString("Version"))) {
                        JLabel tf = new JLabel();
                        tf.setText(m.mes.getString("Messages.0"));
                        JOptionPane.showMessageDialog(m, tf, m.mes.getString("Menu.15"),
                                JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JLabel tf = new JLabel();
                        tf.setText(m.mes.getString("Messages.1"));
                        JOptionPane.showMessageDialog(m, tf, m.mes.getString("Menu.15"),
                                JOptionPane.INFORMATION_MESSAGE);
                    }

                } else {
                    JLabel tf = new JLabel();
                    tf.setText(m.mes.getString("Messages.2"));
                    JOptionPane.showMessageDialog(m, tf, m.mes.getString("Menu.15"), JOptionPane.INFORMATION_MESSAGE);
                }

            } catch (Exception ex) {
                JLabel tf = new JLabel();
                tf.setText(m.mes.getString("Messages.2"));
                JOptionPane.showMessageDialog(m, tf, m.mes.getString("Menu.15"), JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}

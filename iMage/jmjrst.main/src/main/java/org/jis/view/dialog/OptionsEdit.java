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
package org.jis.view.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;
import java.util.Hashtable;
import java.util.Locale;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileSystemView;

import org.jis.Main;
import org.jis.listner.CloseListner;
import org.jis.options.Options;

/**
 * @author <a href="http://www.jgeppert.com">Johannes Geppert</a>
 * 
 * <p>
 * A Dialog to edit the Options
 * </p>
 */
public class OptionsEdit {
    JDialog    f;

    String     locals[]       = { "Deutsch", "English" };
    String     sizes[]        = { "320 x 240", "640 x 480", "800 x 600", "1024 x 768", "1280 x 1024", "1600 x 1200" };
    JComboBox  list_local     = new JComboBox(locals);
    JComboBox  list_sizes     = new JComboBox(sizes);
    JTextField t_output       = new JTextField();
    JTextField t_copyright    = new JTextField();
    JTextField t_maxw         = new JTextField();
    JTextField t_maxh         = new JTextField();
    JCheckBox  c_antialiasing = new JCheckBox();
    JCheckBox  c_metadata     = new JCheckBox();
    JCheckBox  c_textbox      = new JCheckBox();
    JCheckBox  c_copyright    = new JCheckBox();
    JPanel     p_copyright    = new JPanel();
    JButton    b_output       = new JButton();
    JButton    b_copyright    = new JButton();
    JButton    b_ok           = new JButton();
    JButton    b_exit         = new JButton();

    JSlider    s_quality      = new JSlider(0, 100);
    JSlider    s_rendermodus  = new JSlider(0, 2);
    JLabel     l_qual         = new JLabel(s_quality.getValue() + "%");                                               //$NON-NLS-1$

    Options    opts;
    Main       main;
    File       file;

    /**
     * shows the Options Edit Dialog
     */
    public void edit() {
        f = new JDialog(main, main.mes.getString("OptionsEdit.8"));
        JLabel l_local = new JLabel(main.mes.getString("OptionsEdit.7"));
        JLabel l_textbox = new JLabel(main.mes.getString("OptionsEdit.10"));
        JLabel l_antialiasing = new JLabel(main.mes.getString("OptionsEdit.15"));
        JLabel l_metadata = new JLabel(main.mes.getString("OptionsEdit.20"));
        JLabel l_output = new JLabel(main.mes.getString("OptionsEdit.0"));
        JLabel l_slider = new JLabel(main.mes.getString("OptionsEdit.1"));
        JLabel l_maxw = new JLabel(main.mes.getString("OptionsEdit.2"));
        JLabel l_maxh = new JLabel(main.mes.getString("OptionsEdit.3"));
        JLabel l_sizes = new JLabel(main.mes.getString("OptionsEdit.9"));
        JLabel l_copyright = new JLabel(main.mes.getString("OptionsEdit.11"));
        JLabel l_copyrightText = new JLabel(main.mes.getString("OptionsEdit.13"));
        JLabel l_copyrightFarbe = new JLabel(main.mes.getString("OptionsEdit.12"));
        JLabel l_rendermodus = new JLabel(main.mes.getString("OptionsEdit.16"));

        b_output = new JButton(main.mes.getString("OptionsEdit.4"));
        URL url = ClassLoader.getSystemResource("icons/folder.png");
        b_output.setIcon(new ImageIcon(url));

        b_copyright = new JButton(main.mes.getString("OptionsEdit.14"));
        URL url2 = ClassLoader.getSystemResource("icons/applications-graphics.png");
        b_copyright.setIcon(new ImageIcon(url2));

        b_ok = new JButton(main.mes.getString("OptionsEdit.5"));
        b_exit = new JButton(main.mes.getString("OptionsEdit.6"));

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] gs = ge.getScreenDevices();
        GraphicsDevice gd = gs[0];
        GraphicsConfiguration gc = gd.getDefaultConfiguration();
        try {
            UIManager.setLookAndFeel(opts.getLookAndFeel());
            SwingUtilities.updateComponentTreeUI(f);
        } catch (Exception l) {
            System.err.println(l);
        }
        Rectangle bounds = gc.getBounds();
        f.setLocation((bounds.width / 2) - 250, (bounds.height / 2) - 233);
        f.setSize(500, 465);
        f.setResizable(false);
        f.addWindowListener(new CloseListner());
        Container c = f.getContentPane();
        c.setLayout(new BorderLayout());
        JPanel ojp = new JPanel();
        ojp.setLayout(null);
        // ojp.set
        // slider.setValue(o.g)
        // t_input.setText(opts.getInput_dir());
        t_output.setEditable(false);

        if (opts != null) {
            t_output.setText(opts.getOutput_dir());
            t_maxw.setText("" + opts.getHmax());
            t_maxh.setText("" + opts.getVmax());
            c_textbox.setSelected(opts.isTextbox());
            c_antialiasing.setSelected(opts.isAntialiasing());
            c_metadata.setSelected(opts.isCopyMetadata());
            c_copyright.setSelected(opts.isCopyright());
            p_copyright.setBackground(new Color(opts.getCopyright_r(), opts.getCopyright_g(), opts.getCopyright_b()));
            p_copyright.setBorder(new BevelBorder(2));
            t_copyright.setText(opts.getCopyrightText());

            s_quality.setValue(Float.valueOf((opts.getQuality() * 100.0F)).intValue());
            s_rendermodus.setValue(opts.getModus());
            if (opts.getLocal().getLanguage().equalsIgnoreCase("de"))
                list_local.setSelectedIndex(0);
            if (opts.getLocal().getLanguage().equalsIgnoreCase("en"))
                list_local.setSelectedIndex(1);
        } else {
            t_output.setText(FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath());
            t_maxw.setText("" + 1024);
            t_maxh.setText("" + 768);
            s_quality.setValue(80);
            s_rendermodus.setValue(2);
        }

        l_local.setBounds(10, 15, 130, 25);
        list_local.setBounds(145, 15, 180, 25);
        l_output.setBounds(10, 45, 130, 25);
        t_output.setBounds(145, 45, 180, 25);
        b_output.setBounds(335, 45, 150, 25);
        l_slider.setBounds(10, 75, 130, 25);
        s_quality.setBounds(145, 75, 180, 25);
        l_qual.setBounds(340, 75, 80, 25);

        l_rendermodus.setBounds(10, 105, 130, 45);
        s_rendermodus.setBounds(145, 105, 180, 45);

        l_maxw.setBounds(10, 155, 130, 25);
        t_maxw.setBounds(145, 155, 80, 25);

        l_maxh.setBounds(10, 185, 130, 25);
        t_maxh.setBounds(145, 185, 80, 25);

        l_sizes.setBounds(245, 155, 120, 25);
        list_sizes.setBounds(245, 185, 120, 25);
        list_sizes.setSelectedIndex(3);

        l_textbox.setBounds(10, 225, 130, 25);
        c_textbox.setBounds(145, 225, 130, 25);

        l_antialiasing.setBounds(10, 255, 130, 25);
        c_antialiasing.setBounds(145, 255, 130, 25);

        l_metadata.setBounds(10, 285, 130, 25);
        c_metadata.setBounds(145, 285, 130, 25);

        l_copyright.setBounds(10, 315, 130, 25);
        c_copyright.setBounds(145, 315, 130, 25);

        l_copyrightText.setBounds(10, 345, 130, 25);
        t_copyright.setBounds(145, 345, 130, 25);

        l_copyrightFarbe.setBounds(10, 375, 130, 25);
        p_copyright.setBounds(145, 375, 130, 25);
        b_copyright.setBounds(335, 375, 150, 25);

        b_ok.setBounds(120, 405, 120, 25);
        b_exit.setBounds(260, 405, 120, 25);

        Hashtable<Integer, JLabel> rendermodus_labels = new Hashtable<Integer, JLabel>();
        rendermodus_labels.put(0, new JLabel(main.mes.getString("OptionsEdit.17")));
        rendermodus_labels.put(1, new JLabel(main.mes.getString("OptionsEdit.18")));
        rendermodus_labels.put(2, new JLabel(main.mes.getString("OptionsEdit.19")));

        s_rendermodus.setOrientation(JSlider.VERTICAL);
        s_rendermodus.setLabelTable(rendermodus_labels);
        s_rendermodus.setMajorTickSpacing(1);
        s_rendermodus.setPaintTicks(true);
        s_rendermodus.setPaintLabels(true);
        s_rendermodus.setSnapToTicks(true);

        s_quality.setMinimum(10);
        l_qual.setText(s_quality.getValue() + "%");

        list_sizes.addActionListener(al_sizes);
        s_quality.addChangeListener(cl);
        b_ok.addActionListener(al);
        b_exit.addActionListener(al);
        b_output.addActionListener(al);
        b_copyright.addActionListener(al);

        t_output.setCaretPosition(0);

        ojp.add(l_local);
        ojp.add(l_output);
        ojp.add(list_local);
        ojp.add(t_output);
        ojp.add(b_output);
        ojp.add(l_slider);
        ojp.add(s_quality);
        ojp.add(l_qual);
        ojp.add(l_rendermodus);
        ojp.add(s_rendermodus);
        ojp.add(l_maxw);
        ojp.add(l_maxh);
        ojp.add(t_maxw);
        ojp.add(l_sizes);
        ojp.add(list_sizes);
        ojp.add(t_maxh);
        ojp.add(l_textbox);
        ojp.add(c_textbox);
        ojp.add(l_antialiasing);
        ojp.add(c_antialiasing);
        ojp.add(l_metadata);
        ojp.add(c_metadata);
        ojp.add(l_copyright);
        ojp.add(c_copyright);
        ojp.add(l_copyrightText);
        ojp.add(t_copyright);
        ojp.add(l_copyrightFarbe);
        ojp.add(p_copyright);
        ojp.add(b_copyright);
        ojp.add(b_ok);
        ojp.add(b_exit);
        c.add(ojp, BorderLayout.CENTER);
        f.setVisible(true);
    }

    /**
     * @param m a reference to the Main Class
     */
    public OptionsEdit(Main m) {
        super();
        opts = Options.getInstance();
        main = m;
    }

    ChangeListener cl       = new ChangeListener() {
                                public void stateChanged(ChangeEvent arg0) {
                                    if (arg0.getSource() == s_quality)
                                        l_qual.setText(s_quality.getValue() + "%");                                  //$NON-NLS-1$
                                }
                            };

    ActionListener al_sizes = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    String val = ((JComboBox) e.getSource()).getSelectedItem().toString();
                                    if (val.substring(0, 2).equalsIgnoreCase("32")) {
                                        t_maxw.setText("320");
                                        t_maxh.setText("240");
                                    } else if (val.substring(0, 2).equalsIgnoreCase("64")) {
                                        t_maxw.setText("640");
                                        t_maxh.setText("480");
                                    } else if (val.substring(0, 2).equalsIgnoreCase("80")) {
                                        t_maxw.setText("800");
                                        t_maxh.setText("600");
                                    } else if (val.substring(0, 2).equalsIgnoreCase("10")) {
                                        t_maxw.setText("1024");
                                        t_maxh.setText("768");
                                    } else if (val.substring(0, 2).equalsIgnoreCase("12")) {
                                        t_maxw.setText("1280");
                                        t_maxh.setText("1024");
                                    } else if (val.substring(0, 2).equalsIgnoreCase("16")) {
                                        t_maxw.setText("1600");
                                        t_maxh.setText("1200");
                                    }
                                }
                            };

    ActionListener al       = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    if (e.getSource() == b_exit) {
                                        f.dispose();
                                        // f.setVisible(false);
                                    } else if (e.getSource() == b_ok) {
                                        boolean restart = false;
                                        float fl = s_quality.getValue();
                                        opts.setQuality(fl / 100.0F);
                                        opts.setModus(s_rendermodus.getValue());

                                        if (opts.isTextbox() != c_textbox.isSelected())
                                            restart = true;

                                        if (c_textbox.isSelected())
                                            opts.setTextbox(true);
                                        else
                                            opts.setTextbox(false);

                                        if (c_antialiasing.isSelected())
                                            opts.setAntialiasing(true);
                                        else
                                            opts.setAntialiasing(false);

                                        if (c_metadata.isSelected())
                                            opts.setCopyMetadata(true);
                                        else
                                            opts.setCopyMetadata(false);

                                        if (c_copyright.isSelected())
                                            opts.setCopyright(true);
                                        else
                                            opts.setCopyright(false);

                                        opts.setCopyrightText(t_copyright.getText());
                                        opts.setCopyright_r(p_copyright.getBackground().getRed());
                                        opts.setCopyright_g(p_copyright.getBackground().getGreen());
                                        opts.setCopyright_b(p_copyright.getBackground().getBlue());

                                        File fo = new File(t_output.getText());
                                        if (fo.exists())
                                            opts.setOutput_dir(fo.toString());
                                        if (t_maxw.getText().equalsIgnoreCase(""))                                   //$NON-NLS-1$
                                            opts.setHmax(0);
                                        else
                                            opts.setHmax(Integer.valueOf(t_maxw.getText()).intValue());

                                        if (t_maxh.getText().equalsIgnoreCase(""))                                   //$NON-NLS-1$
                                            opts.setVmax(0);
                                        else
                                            opts.setVmax(Integer.valueOf(t_maxh.getText()).intValue());

                                        if (list_local.getSelectedItem().toString().equalsIgnoreCase("Deutsch")
                                                && !(opts.getLocal().getLanguage().equalsIgnoreCase("de")))
                                            restart = true;
                                        if (list_local.getSelectedItem().toString().equalsIgnoreCase("English")
                                                && !(opts.getLocal().getLanguage().equalsIgnoreCase("en")))
                                            restart = true;

                                        if (list_local.getSelectedItem().toString().equalsIgnoreCase("Deutsch"))
                                            opts.setLocal(Locale.GERMAN);
                                        else
                                            opts.setLocal(Locale.ENGLISH);

                                        opts.saveOptions();
                                        if (restart) {
                                            main.restart();
                                        }
                                        f.dispose();
                                        // f.setVisible(false);
                                    } else if (e.getSource() == b_copyright) {
                                        Color c = JColorChooser.showDialog(f, main.mes.getString("GallerieDialog.6"),
                                                Color.DARK_GRAY);
                                        if (c != null) {
                                            p_copyright.setOpaque(true);
                                            p_copyright.setBackground(c);
                                        }
                                    } else if (e.getSource() == b_output) {
                                        JFileChooser fc = new JFileChooser();
                                        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                                        File f = new File(opts.getOutput_dir());
                                        if (f.exists())
                                            fc.setCurrentDirectory(f);
                                        else
                                            fc.setCurrentDirectory(
                                                    FileSystemView.getFileSystemView().getHomeDirectory());
                                        int returnVal = fc.showOpenDialog(null);

                                        if (returnVal == JFileChooser.APPROVE_OPTION)
                                            if (file == null) {
                                                file = fc.getSelectedFile();
                                                t_output.setText(file.toString());
                                                file = null;
                                            }
                                    }
                                }
                            };
}

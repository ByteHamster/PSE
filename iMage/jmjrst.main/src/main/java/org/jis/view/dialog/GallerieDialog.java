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
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneLayout;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileSystemView;

import org.jis.Main;
import org.jis.generator.LayoutGalerie;
import org.jis.generator.TableGalerie;
import org.jis.listner.CloseListner;
import org.jis.options.Options;
import org.jis.view.Thumbnail;

/**
 * @author <a href="http://www.jgeppert.com">Johannes Geppert</a>
 * 
 * <p>
 * The Dialog for creating a Web Gallerie
 * </p>
 */
public class GallerieDialog extends JDialog {
    JButton                   b_output         = new JButton();
    JButton                   b_color          = new JButton();
    JButton                   b_t_color        = new JButton();
    JButton                   b_ok             = new JButton();
    JButton                   b_exit           = new JButton();
    JPanel                    p_color          = new JPanel();
    JPanel                    p_t_color        = new JPanel();
    JPanel                    table            = new JPanel();
    JPanel                    layout           = new JPanel();
    JTextField                t_output;
    JComboBox                 c_tab_width;
    JComboBox                 c_tab_heigth;
    JComboBox                 c_types;
    JComboBox                 c_layouts;
    JList                     l_layouts;
    JTextPane                 t_desc           = new JTextPane();
    JTextField                t_titel;
    JTextField                t_sub_titel;
    JDialog                   gallerieDialog;
    Main                      m;
    Options                   o;
    private static final long serialVersionUID = -6983342868655569763L;
    private Thumbnail         preview;
    private Layout            selectedLayout;
    private JPanel            listPanel        = new JPanel();
    private JScrollPane       jsp              = new JScrollPane();

    /**
     * @param m a reference to the Main Class.
     * @throws HeadlessException
     */
    public GallerieDialog(Main m) throws HeadlessException {
        super(m, m.mes.getString("Menu.14"), true);
        o = Options.getInstance();
        this.gallerieDialog = this;
        this.m = m;

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] gs = ge.getScreenDevices();
        GraphicsDevice gd = gs[0];
        GraphicsConfiguration gc = gd.getDefaultConfiguration();
        Rectangle bounds = gc.getBounds();
        setLocation((bounds.width / 2) - 275, (bounds.height / 2) - 135);
        setSize(650, 470);
        // setResizable(false);
        addWindowListener(new CloseListner());

        JLabel l_output = new JLabel(m.mes.getString("OptionsEdit.0"));
        JLabel l_type = new JLabel(m.mes.getString("GallerieDialog.9"));
        JLabel l_titel = new JLabel(m.mes.getString("GallerieDialog.0"));
        // JLabel l_sub_titel = new
        // JLabel(m.mes.getString("GallerieDialog.11"));
        JLabel l_width = new JLabel(m.mes.getString("GallerieDialog.1"));
        JLabel l_heigth = new JLabel(m.mes.getString("GallerieDialog.2"));

        String types[] = { m.mes.getString("GallerieDialog.7"), m.mes.getString("GallerieDialog.8") };
        c_types = new JComboBox(types);
        c_types.addActionListener(al);

        t_titel = new JTextField(o.getGallerieTitle());
        t_sub_titel = new JTextField(o.getGallerieSubTitle());
        t_sub_titel.setEditable(false);
        t_sub_titel.setMaximumSize(new Dimension(300, 20));

        String width[] = { "2", "3", "4", "5" };
        c_tab_width = new JComboBox(width);
        String heigth[] = { "3", "4", "5", "6" };
        c_tab_heigth = new JComboBox(heigth);
        c_tab_heigth.setSelectedItem("" + o.getGallerieHeigth());
        c_tab_width.setSelectedItem("" + o.getGallerieWidth());

        // c_layouts = new JComboBox(initLayouts());
        jsp.setAutoscrolls(true);
        jsp.setLayout(new ScrollPaneLayout());
        l_layouts = new JList();
        l_layouts.setListData(initLayouts());
        t_desc.setEditable(false);
        jsp.add(l_layouts);
        jsp.setViewportView(l_layouts);

        // l_layouts = new JList(initLayouts());

        JLabel l_bcolor = new JLabel(m.mes.getString("GallerieDialog.3"));
        JLabel l_t_color = new JLabel(m.mes.getString("GallerieDialog.4"));
        b_color = new JButton(m.mes.getString("GallerieDialog.5"));
        URL url = ClassLoader.getSystemResource("icons/applications-graphics.png");
        b_color.setIcon(new ImageIcon(url));

        b_t_color = new JButton(m.mes.getString("GallerieDialog.6"));
        url = ClassLoader.getSystemResource("icons/applications-graphics.png");
        b_t_color.setIcon(new ImageIcon(url));

        b_output = new JButton(m.mes.getString("OptionsEdit.4"));
        url = ClassLoader.getSystemResource("icons/folder.png");
        b_output.setIcon(new ImageIcon(url));

        b_ok = new JButton(m.mes.getString("OptionsEdit.5"));
        b_ok.setEnabled(false);
        b_exit = new JButton(m.mes.getString("OptionsEdit.6"));
        t_output = new JTextField(o.getOutput_dir_gallerie());
        t_output.setEditable(false);
        t_output.setCaretPosition(0);

        b_ok.addActionListener(al);
        l_layouts.addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                JList l = (JList) e.getSource();
                setSelectedLayout((Layout) l.getSelectedValue());
                t_desc.setText(getSelectedLayout().getDescription());

                Thread x = new Thread(new Runnable() {

                    public void run() {
                        try {
                            if (getSelectedLayout().getPreviewImage() != null)
                                setPreview(new Thumbnail(getSelectedLayout().getPreviewImage(), 300, 240));
                            else
                                setPreview(new Thumbnail(300, 240));
                            // getGallerieDialog().repaint();
                            // getGallerieDialog().validate();
                        } catch (Exception ex) {
                            System.out.println("Fehler beim erstellen der Vorschau!");
                            ex.printStackTrace();
                        }
                    }

                });

                x.start();

                b_ok.setEnabled(true);
                if (getSelectedLayout().isSubTitle())
                    t_sub_titel.setEditable(true);
                else
                    t_sub_titel.setEditable(false);
            }

        });
        b_exit.addActionListener(al);
        b_output.addActionListener(al);
        b_color.addActionListener(al);
        b_t_color.addActionListener(al);
        p_color.setBackground(new Color(o.getBackground_r(), o.getBackground_g(), o.getBackground_b()));
        p_t_color.setBackground(new Color(o.getForeground_r(), o.getForeground_g(), o.getForeground_b()));

        BorderLayout bl = new BorderLayout();
        bl.setHgap(3);
        bl.setVgap(3);
        this.setLayout(bl);

        // GridLayout gl2 = new GridLayout(3, 3);
        // gl2.setHgap(5);
        // gl2.setVgap(5);
        GridBagLayout gbl1 = new GridBagLayout();
        JPanel select = new JPanel();
        select.setLayout(gbl1);

        GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.fill = GridBagConstraints.BOTH;
        gbc1.insets = new Insets(3, 3, 3, 3);

        setConstraints(gbc1, 0, 0, 1, 1, 10, 10);
        gbl1.setConstraints(l_type, gbc1);
        select.add(l_type);

        setConstraints(gbc1, 1, 0, 1, 1, 70, 10);
        gbl1.setConstraints(c_types, gbc1);
        select.add(c_types);

        JLabel emp1 = new JLabel();
        setConstraints(gbc1, 2, 0, 1, 1, 20, 10);
        gbl1.setConstraints(emp1, gbc1);
        select.add(emp1);

        setConstraints(gbc1, 0, 1, 1, 1, 10, 10);
        gbl1.setConstraints(l_titel, gbc1);
        select.add(l_titel);

        setConstraints(gbc1, 1, 1, 1, 1, 70, 10);
        gbl1.setConstraints(t_titel, gbc1);
        select.add(t_titel);

        JLabel emp2 = new JLabel();
        setConstraints(gbc1, 2, 1, 1, 1, 20, 10);
        gbl1.setConstraints(emp2, gbc1);
        select.add(emp2);

        setConstraints(gbc1, 0, 2, 1, 1, 10, 10);
        gbl1.setConstraints(l_output, gbc1);
        select.add(l_output);

        setConstraints(gbc1, 1, 2, 1, 1, 70, 10);
        gbl1.setConstraints(t_output, gbc1);
        select.add(t_output);

        setConstraints(gbc1, 2, 2, 1, 1, 20, 10);
        gbl1.setConstraints(b_output, gbc1);
        select.add(b_output);

        GridLayout gl = new GridLayout(4, 3);
        gl.setHgap(5);
        gl.setVgap(5);

        table = new JPanel();
        table.setLayout(gl);
        table.add(l_width);
        table.add(c_tab_width);
        table.add(new JLabel());

        table.add(l_heigth);
        table.add(c_tab_heigth);
        table.add(new JLabel());

        table.add(l_bcolor);
        table.add(p_color);
        table.add(b_color);

        table.add(l_t_color);
        table.add(p_t_color);
        table.add(b_t_color);

        // layout = new JPanel();
        // GridBagLayout gl3 = new GridBagLayout();
        // layout.setLayout(gl3);
        //
        // GridBagConstraints gbc = new GridBagConstraints();
        // gbc.fill = GridBagConstraints.BOTH;
        // gbc.insets = new Insets(3, 3, 3, 3);
        //
        // setConstraints(gbc, 0, 0, 1, 1, 10, 10);
        // gl3.setConstraints(l_sub_titel, gbc);
        // layout.add(l_sub_titel);
        //
        // setConstraints(gbc, 1, 0, 2, 1, 80, 10);
        // gl3.setConstraints(t_sub_titel, gbc);
        // layout.add(t_sub_titel);
        // // layout.add(new JLabel());
        //
        // JLabel l_layout = new JLabel(m.mes.getString("GallerieDialog.10"));
        // l_layout.setSize(100, 20);
        //
        // setConstraints(gbc, 0, 1, 1, 1, 10, 90);
        // gl3.setConstraints(l_layout, gbc);
        // layout.add(l_layout);
        //
        // setConstraints(gbc, 1, 1, 1, 1, 50, 90);
        // jsp.setSize(100, 100);
        // gl3.setConstraints(jsp, gbc);
        // layout.add(jsp);
        //
        // setConstraints(gbc, 2, 1, 1, 1, 40, 90);
        //// gl3.setConstraints(t_desc, gbc);
        //// layout.add(t_desc);
        // gl3.setConstraints(preview, gbc);
        // layout.add(preview);
        GridBagConstraints gbc = new GridBagConstraints();
        try {
            setPreview(new Thumbnail(300, 240));
        } catch (IOException e1) {
        }

        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        JPanel buttons = new JPanel();
        buttons.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttons.add(b_ok);
        buttons.add(b_exit);

        add(select, BorderLayout.NORTH);
        add(table, BorderLayout.CENTER);
        add(layout, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);

        l_layouts.setSelectedIndex(0);

        setVisible(true);
    }

    private void swit(int s) {
        if (s == 0) {
            remove(table);
            add(layout, BorderLayout.CENTER);
        } else {
            remove(layout);
            add(table, BorderLayout.CENTER);
            table.repaint();
        }
        repaint();
        m.repaint();
    }

    private Object[] initLayouts() {
        File folder = new File(".");
        File templates = new File(folder, "templates");

        Vector<Layout> vl = new Vector<Layout>();

        if (templates.exists() && templates.isDirectory()) {
            File[] files = templates.listFiles();

            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    File settings = new File(files[i], "settings.properties");
                    File preview = new File(files[i], "preview.jpg");
                    if (settings.exists() && settings.isFile() && settings.canRead()) {
                        try {
                            Properties p = new Properties();
                            p.load(new FileInputStream(settings));
                            Layout x = new Layout();
                            x.setName(p.getProperty("name"));
                            x.setDescription(p.getProperty("description"));
                            x.setType(p.getProperty("type"));
                            x.setListType(p.getProperty("listType"));
                            x.setFooter(p.getProperty("footer"));
                            x.setOnclickSmall(p.getProperty("onclickSmall"));
                            x.setOnclickMedium(p.getProperty("onclickMedium"));
                            x.setPrefix(p.getProperty("prefix"));
                            x.setPreviewBack(p.getProperty("previewBack"));
                            x.setPreviewNext(p.getProperty("previewNext"));
                            x.setPreviewHome(p.getProperty("previewHome"));
                            try {
                                x.setAProperty(p.getProperty("aProperty"));
                            } catch (Exception e) {
                                x.setAProperty(" ");
                            }

                            x.setSmallWidth(Integer.parseInt(p.getProperty("smallWidth")));
                            x.setSmallHeight(Integer.parseInt(p.getProperty("smallHeight")));
                            x.setMediumWidth(Integer.parseInt(p.getProperty("mediumWidth")));
                            x.setMediumHeight(Integer.parseInt(p.getProperty("mediumHeight")));
                            x.setBigWidth(Integer.parseInt(p.getProperty("bigWidth")));
                            x.setBigHeight(Integer.parseInt(p.getProperty("bigHeight")));

                            if (p.getProperty("mediumCreate").equalsIgnoreCase("true"))
                                x.setMediumCreate(true);
                            else
                                x.setMediumCreate(false);

                            if (p.getProperty("bigCreate").equalsIgnoreCase("true"))
                                x.setBigCreate(true);
                            else
                                x.setBigCreate(false);

                            if (p.getProperty("subTitle").equalsIgnoreCase("true"))
                                x.setSubTitle(true);
                            else
                                x.setSubTitle(false);

                            x.setMax_pictures_on_site(Integer.parseInt(p.getProperty("max_pictures_on_site")));
                            x.setFile(files[i]);

                            if (preview.exists() && preview.isFile()) {
                                x.setPreviewImage(preview);
                            }

                            vl.addElement(x);
                        } catch (Exception e3) {
                            System.out.println("Error while loading Layout " + files[i].getName());
                            System.err.println(e3.getLocalizedMessage());
                        }
                    }
                }
            }
        }
        return vl.toArray();
    }

    private void setConstraints(GridBagConstraints gbc, int gx, int gy, int gw, int gh, int wx, int wy) {
        gbc.gridx = gx;
        gbc.gridy = gy;
        gbc.gridwidth = gw;
        gbc.gridheight = gh;
        gbc.weightx = wx;
        gbc.weighty = wy;
    }

    ActionListener al = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == b_exit)
                gallerieDialog.setVisible(false);
            else if (e.getSource() == b_color) {
                Color c = JColorChooser.showDialog(gallerieDialog, m.mes.getString("GallerieDialog.5"),
                        Color.DARK_GRAY);
                if (c != null) {
                    p_color.setOpaque(true);
                    p_color.setBackground(c);
                }
            } else if (e.getSource() == b_t_color) {
                Color c = JColorChooser.showDialog(gallerieDialog, m.mes.getString("GallerieDialog.6"),
                        Color.DARK_GRAY);
                if (c != null) {
                    p_t_color.setOpaque(true);
                    p_t_color.setBackground(c);
                }
            } else if (e.getSource() == b_ok) {
                o.setBackground_r(p_color.getBackground().getRed());
                o.setBackground_g(p_color.getBackground().getGreen());
                o.setBackground_b(p_color.getBackground().getBlue());
                o.setForeground_r(p_t_color.getBackground().getRed());
                o.setForeground_g(p_t_color.getBackground().getGreen());
                o.setForeground_b(p_t_color.getBackground().getBlue());
                o.setGallerieWidth(Integer.parseInt(c_tab_width.getSelectedItem().toString()));
                o.setGallerieHeigth(Integer.parseInt(c_tab_heigth.getSelectedItem().toString()));
                o.setGallerieTitle(t_titel.getText());
                o.setGallerieSubTitle(t_sub_titel.getText());
                o.setOutput_dir_gallerie(t_output.getText());

                if (c_types.getSelectedIndex() == 0) {
                    Layout l = (Layout) l_layouts.getSelectedValue();
                    new LayoutGalerie(m, l);
                } else {
                    new TableGalerie(m);
                }

                gallerieDialog.setVisible(false);
            } else if (e.getSource() == b_output) {
                JFileChooser fc = new JFileChooser();
                fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                File f = new File(o.getOutput_dir_gallerie());
                if (f.exists())
                    fc.setCurrentDirectory(f);
                else
                    fc.setCurrentDirectory(FileSystemView.getFileSystemView().getHomeDirectory());
                int returnVal = fc.showOpenDialog(null);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    t_output.setText(fc.getSelectedFile().toString());
                }
            } else if (e.getSource() == c_types) {
                if (c_types.getSelectedIndex() == 0) {
                    if (l_layouts.isSelectionEmpty())
                        b_ok.setEnabled(false);
                    else
                        b_ok.setEnabled(true);

                    swit(0);
                } else {
                    b_ok.setEnabled(true);
                    swit(1);
                }
                repaint();
                validate();
            }
        }
    };

    public Thumbnail getPreview() {
        return preview;
    }

    public void setPreview(Thumbnail preview) {
        this.preview = preview;
        this.remove(layout);

        layout = new JPanel();
        JLabel l_sub_titel = new JLabel(m.mes.getString("GallerieDialog.11"));
        GridBagLayout gl3 = new GridBagLayout();
        layout.setLayout(gl3);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(3, 3, 3, 3);

        setConstraints(gbc, 0, 0, 1, 1, 10, 10);
        gl3.setConstraints(l_sub_titel, gbc);
        layout.add(l_sub_titel);

        setConstraints(gbc, 1, 0, 2, 1, 80, 10);
        gl3.setConstraints(t_sub_titel, gbc);
        layout.add(t_sub_titel);
        // layout.add(new JLabel());

        JLabel l_layout = new JLabel(m.mes.getString("GallerieDialog.10"));
        l_layout.setSize(100, 20);

        setConstraints(gbc, 0, 1, 1, 1, 10, 90);
        gl3.setConstraints(l_layout, gbc);
        layout.add(l_layout);

        listPanel.setLayout(new BorderLayout());
        Dimension listPanelDimension = new Dimension(230, 250);
        Dimension jspDimension = new Dimension(230, 130);
        Dimension descDimension = new Dimension(230, 100);
        listPanel.setSize(listPanelDimension);
        listPanel.setPreferredSize(listPanelDimension);
        listPanel.setMaximumSize(listPanelDimension);
        jsp.setSize(jspDimension);
        jsp.setPreferredSize(jspDimension);
        jsp.setMaximumSize(jspDimension);
        t_desc.setSize(descDimension);
        t_desc.setPreferredSize(descDimension);
        t_desc.setMaximumSize(descDimension);
        listPanel.add(jsp, BorderLayout.NORTH);
        listPanel.add(t_desc, BorderLayout.SOUTH);

        setConstraints(gbc, 1, 1, 1, 1, 200, 240);
        gl3.setConstraints(listPanel, gbc);
        layout.add(listPanel);

        // preview.setSize(previewDimension);
        // preview.setPreferredSize(previewDimension);
        // preview.setMaximumSize(previewDimension);
        setConstraints(gbc, 2, 1, 1, 1, 40, 90);
        // gl3.setConstraints(t_desc, gbc);
        // layout.add(t_desc);
        gl3.setConstraints(preview, gbc);
        layout.add(preview);

        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        add(layout);
        this.repaint();
        this.validate();
    }

    public JDialog getGallerieDialog() {
        return gallerieDialog;
    }

    public Layout getSelectedLayout() {
        return selectedLayout;
    }

    public void setSelectedLayout(Layout selectedLayout) {
        this.selectedLayout = selectedLayout;
    }

}

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
package org.jis;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.io.File;
import java.net.URL;
import java.util.Locale;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.ProgressMonitor;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import org.jis.generator.Generator;
import org.jis.listner.ExitListner;
import org.jis.options.Options;
import org.jis.view.FileTree;
import org.jis.view.List;
import org.jis.view.Menu;
import org.jis.view.Preview;
import org.jis.view.Status;
import org.jis.view.Toolbar;
import org.jis.view.dialog.OptionsEdit;

/**
 * @author <a href="http://www.jgeppert.com">Johannes Geppert</a>
 * 
 * <p>
 * The Main Class is the Entry Point of the Application and build the GUI
 * </p>
 */
public class Main extends JFrame {
    private static final long serialVersionUID = 5124271743719044219L;

    private Options           o;
    private Preview           preview          = null;
    private JScrollPane       jsp              = new JScrollPane();
    public JTextPane          text             = new JTextPane();
    public List               list             = new List(preview);
    public Menu               menu             = null;
    public Toolbar            toolBar          = null;
    public Generator          generator;
    public Status             status           = null;
    public boolean            error            = false;
    public Messages           mes              = null;
    public StyledDocument     jOutputDoc;
    public SimpleAttributeSet outputAtr        = new SimpleAttributeSet();
    public SimpleAttributeSet readyAtr         = new SimpleAttributeSet();
    public SimpleAttributeSet errorAtr         = new SimpleAttributeSet();
    public SimpleAttributeSet fileAtr          = new SimpleAttributeSet();
    public ProgressMonitor    p_monitor        = null;

    private Main() {
        super();
        o = Options.getInstance();
        mes = new Messages(o.getLocal());
        setTitle(mes.getString("Main.0"));

        generator = new Generator(this, o.getQuality());
        preview = new Preview(this);
        list = new List(preview);

        init();
    }

    private void init() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] gs = ge.getScreenDevices();
        GraphicsDevice gd = gs[0];
        GraphicsConfiguration gc = gd.getDefaultConfiguration();

        try {
            UIManager.setLookAndFeel(o.getLookAndFeel());
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception l) {
        }

        Rectangle bounds = gc.getBounds();
        this.setLocation((bounds.width / 2) - 400, (bounds.height / 2) - 300);
        this.setSize(800, 600);
        URL url = ClassLoader.getSystemResource("icons/jmjrst48.png");
        this.setIconImage(new ImageIcon(url).getImage());
        setMinimumSize(new Dimension(800, 600));
        setPreferredSize(new Dimension(800, 600));
        setResizable(true);
        addWindowListener(new ExitListner());

        menu = new Menu(this);
        setJMenuBar(menu);

        toolBar = new Toolbar(this);

        Container c = getContentPane();
        c.setLayout(new BorderLayout());

        text.setVisible(true);

        text.setEditable(false);
        Font font = new Font(mes.getString("Main.1"), Font.BOLD, 12);
        text.setForeground(Color.black);
        text.setFont(font);
        jOutputDoc = text.getStyledDocument();

        StyleConstants.setForeground(outputAtr, Color.BLACK);
        StyleConstants.setForeground(readyAtr, Color.BLUE);
        StyleConstants.setForeground(errorAtr, Color.RED);
        StyleConstants.setForeground(fileAtr, Color.GREEN);
        try {
            jOutputDoc.insertString(jOutputDoc.getLength(), mes.getString("Main.2") + Options.ls, readyAtr);
            text.setCaretPosition(jOutputDoc.getLength());
        } catch (Exception e) {
        }

        jsp = new JScrollPane(text);
        jsp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jsp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        jsp.add(text);
        jsp.setViewportView(text);

        status = new Status(this);

        FileTree iv = new FileTree(this, list);
        iv.setMinimumSize(new Dimension(300, 600));

        JScrollPane jsp2 = new JScrollPane(iv);

        JSplitPane obenrechts = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, list, preview);
        obenrechts.setContinuousLayout(true);
        obenrechts.setOneTouchExpandable(true);
        obenrechts.setResizeWeight(0.6D);

        JSplitPane oben = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, jsp2, obenrechts);
        oben.setContinuousLayout(true);
        oben.setOneTouchExpandable(true);
        oben.setResizeWeight(0.5D);

        JSplitPane jsplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, oben, jsp);
        jsplit.setContinuousLayout(true);
        jsplit.setOneTouchExpandable(true);
        jsplit.setResizeWeight(0.7D);

        if (o.isTextbox()) {
            c.add(jsplit, BorderLayout.CENTER);
        } else {
            c.add(oben, BorderLayout.CENTER);
        }
        c.add(status, BorderLayout.SOUTH);
        c.add(toolBar, BorderLayout.NORTH);
        setVisible(true);

        // after the first start of the Application open the Options Dialog
        if (o.isInitial()) {
            openOptions();
            o.setInitial(false);
        }
    }

    /**
     * Main function
     * 
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        if (args.length == 0)
            new Main();
        else {
            Messages mes = new Messages(Locale.ENGLISH);
            if (args.length == 1 && args[0].equalsIgnoreCase(mes.getString("Main.3"))) {
                System.out.println(System.getProperty("line.separator") + mes.getString("Main.5")
                        + System.getProperty("line.separator") + mes.getString("Main.7"));
                System.out.println(mes.getString("Main.8"));
                System.out.println(mes.getString("Main.9"));
                System.out.println();
                System.out.println(mes.getString("Main.10"));
                System.out.println(mes.getString("Main.11"));
                System.out.println(mes.getString("Main.12"));
                System.out.println(mes.getString("Main.13"));
                System.out.println(mes.getString("Main.14"));
                System.out.println(mes.getString("Main.15"));
            }

            String input = null;
            String output = null;
            String quality = null;
            String hmax = null;
            String vmax = null;
            File fi = null;
            File fo = null;
            int q = 75;
            int h = 0;
            int v = 0;

            for (int i = 0; i < args.length; i++) {
                System.out.println(args[i]);
                try {
                    if (args[i].substring(0, 4).equalsIgnoreCase(mes.getString("hmax")))
                        hmax = args[i].substring(args[i].lastIndexOf(mes.getString("=")) + 1, args[i].length()).trim();
                    if (args[i].substring(0, 4).equalsIgnoreCase(mes.getString("vmax")))
                        vmax = args[i].substring(args[i].lastIndexOf(mes.getString("=")) + 1, args[i].length()).trim();
                    if (args[i].substring(0, 5).equalsIgnoreCase(mes.getString("input")))
                        input = args[i].substring(args[i].lastIndexOf(mes.getString("=")) + 1, args[i].length()).trim();
                    if (args[i].substring(0, 6).equalsIgnoreCase(mes.getString("output")))
                        output = args[i].substring(args[i].lastIndexOf(mes.getString("=")) + 1, args[i].length())
                                .trim();
                    if (args[i].substring(0, 7).equalsIgnoreCase(mes.getString("quality")))
                        quality = args[i].substring(args[i].lastIndexOf(mes.getString("=")) + 1, args[i].length())
                                .trim();
                } catch (Exception e) {
                }
            }
            if (input == null) {
                System.out.println(mes.getString("Main.26"));
                System.exit(0);
            } else
                fi = new File(input);
            if (output == null) {
                System.out.println(mes.getString("Main.27"));
                System.exit(0);
            } else
                fo = new File(output);
            if (quality == null) {
                System.out.println(mes.getString("Main.28"));
                System.exit(0);
            } else
                q = Integer.parseInt(quality);
            if (hmax == null && vmax == null) {
                System.out.println(mes.getString("Main.29"));
                System.exit(0);
            } else {
                if (hmax != null)
                    h = Integer.parseInt(hmax);
                if (vmax != null)
                    v = Integer.parseInt(vmax);
            }
            System.out.println(mes.getString("Main.30") + fi.toString() + mes.getString("Main.31") + fo.toString()
                    + mes.getString("Main.32") + q); //$NON-NLS-1$
            new Generator(null, q / 100.0F).generateText(fi, fo, h, v);
        }
    }

    /**
     * <p>
     * set the Look and Feel of this Application
     * </p>
     * 
     * @param look the LookAndFeel classname
     */
    public void setLookFeel(String look) {
        try {
            UIManager.setLookAndFeel(look);
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception l) {
            try {
                jOutputDoc.insertString(jOutputDoc.getLength(), mes.getString("Main.33") + look + Options.ls, errorAtr);
                text.setCaretPosition(jOutputDoc.getLength());
            } catch (Exception e) {
                System.err.println(mes.getString("Main.33") + look);
            }
        }
    }

    /**
     * open the OptionsEdit Dialog
     */
    public void openOptions() {
        setEnabled(false);
        OptionsEdit oe = new OptionsEdit(this);
        oe.edit();
        setEnabled(true);
    }

    /**
     * restart the GUI, after changing the Language
     */
    public void restart() {
        this.dispose();
        new Main();
    }

}

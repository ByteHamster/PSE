package org.iMage.plugin.hallo_plugin;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

import org.jis.Main;

/**
 * Configuration dialog for the "hello world" plugin
 * 
 * @author Hans-Peter Lehmann
 * @version 1.0
 */
public class ConfigurationDialog extends JDialog {
    private static final long serialVersionUID = -8382863114139648271L;

    /**
     * Displays the dialog
     * 
     * @param frame Where to display the dialog
     */
    public ConfigurationDialog(Main frame) {
        super(frame, "Hallo-SWT1-Plugin", true);

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] gs = ge.getScreenDevices();
        GraphicsDevice gd = gs[0];
        GraphicsConfiguration gc = gd.getDefaultConfiguration();
        Rectangle bounds = gc.getBounds();
        this.setLocation((bounds.width / 2) - (450 / 2), (bounds.height / 2) - (210 / 2));

        this.setSize(450, 210);
        GridLayout layout = new GridLayout(7, 1);
        layout.setHgap(3);
        layout.setVgap(3);

        setLayout(layout);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.add(new JLabel("Hallo-SWT1-Plugin"));

        JButton close = new JButton(frame.mes.getString("AboutBox.15"));
        close.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                dispose();
            }
        });
        this.add(close);
        setVisible(true);
    }

}

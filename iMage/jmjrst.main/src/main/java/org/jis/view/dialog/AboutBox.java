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

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

import org.jis.Main;

/**
 * @author <a href="http://www.jgeppert.com">Johannes Geppert</a>
 * 
 * <p>
 * The AboutBox shows main informations about this program
 * </p>
 */
public class AboutBox extends JDialog {
    private static final long serialVersionUID = 2036493299703182824L;

    /**
     * <p>
     * shows the Dialog
     * </p>
     * 
     * @param frame a reference to the Main Class
     * @throws HeadlessException
     */
    public AboutBox(Main frame) throws HeadlessException {
        super(frame, frame.mes.getString("AboutBox.1"), true);

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
        this.add(new JLabel(frame.mes.getString("AboutBox.3")));
        this.add(new JLabel(frame.mes.getString("AboutBox.22") + " " + frame.mes.getString("Version")));
        this.add(new JLabel(frame.mes.getString("AboutBox.5") + " : " + frame.mes.getString("AboutBox.6")));
        this.add(new JLabel(frame.mes.getString("AboutBox.4")));
        // this.add(new JLabel("<html><head></head><body><b>Web: </b> <a
        // href=\"http://jmjrst.sourceforge.net\">http://jmjrst.sourceforge.net</a><body></html>"));
        this.add(new JLabel(frame.mes.getString("AboutBox.2")));
        this.add(new JLabel(frame.mes.getString("AboutBox.20")));
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

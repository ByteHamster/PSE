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
package org.jis.view;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import org.jis.Main;

/**
 * @author <a href="http://www.jgeppert.com">Johannes Geppert</a>
 * 
 * <p>
 * The Status Panel of the GUI
 * </p>
 */
public class Status extends JPanel {
    private static final long serialVersionUID = 3066478234356371570L;

    private JProgressBar      status;

    public Status(Main m) {
        super(new BorderLayout());
        JLabel version = new JLabel(" " + m.mes.getString("AboutBox.22") + " " + m.mes.getString("Version"));
        Thread t = new Thread() {
            public void run() {
                try {
                    status = new JProgressBar(0, 10);
                    status.setValue(0);
                    add(status, BorderLayout.EAST);
                } catch (Exception e) {
                }
            }
        };
        t.start();
        add(version, BorderLayout.WEST);

    }

    /**
     * Set the status of the ProgressBar on
     */
    public void setStatusOn() {
        status.setIndeterminate(true);
    }

    /**
     * Set the status of the ProgressBar off
     */
    public void setStatusOff() {
        status.setIndeterminate(false);
    }
}

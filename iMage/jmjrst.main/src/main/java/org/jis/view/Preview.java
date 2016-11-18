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
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.jis.Main;
import org.jis.generator.Generator;

public class Preview extends JPanel {
    private static final long serialVersionUID = 5219232341968203639L;

    Thumbnail                 thumb;
    Main                      main;
    JButton                   generieren       = new JButton();
    JButton                   rotate_rechts    = new JButton();
    JButton                   rotate_links     = new JButton();

    public Thumbnail getThumb() {
        return thumb;
    }

    public void setThumb(Thumbnail thumb) {
        this.thumb = thumb;

        removeAll();

        JPanel south = new JPanel();
        south.setLayout(new BorderLayout());
        south.add(generieren, BorderLayout.CENTER);
        south.add(rotate_links, BorderLayout.WEST);
        south.add(rotate_rechts, BorderLayout.EAST);
        add(thumb, BorderLayout.CENTER);
        add(south, BorderLayout.SOUTH);
        repaint();
        validate();
    }

    public Preview(Main m) {
        super();
        setPreferredSize(new Dimension(220, 250));
        setMinimumSize(new Dimension(220, 250));
        main = m;
        generieren = new JButton(m.mes.getString("Preview.0"));
        URL url = ClassLoader.getSystemResource("icons/image-x-generic.png");
        generieren.setIcon(new ImageIcon(url));

        generieren.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                main.generator.generateSingle(thumb.getFile(), thumb.getImage());
            }
        });

        rotate_links = new JButton("");
        URL urll = ClassLoader.getSystemResource("icons/go-jump-mirror.png");
        rotate_links.setIcon(new ImageIcon(urll));

        rotate_links.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                thumb.setImage(main.generator.rotateImage(thumb.getImage(), Generator.ROTATE_270));
                thumb.repaint();
            }
        });

        rotate_rechts = new JButton("");
        URL urlr = ClassLoader.getSystemResource("icons/go-jump.png");
        rotate_rechts.setIcon(new ImageIcon(urlr));

        rotate_rechts.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                thumb.setImage(main.generator.rotateImage(thumb.getImage(), Generator.ROTATE_90));
                thumb.repaint();
            }
        });
        setLayout(new BorderLayout());

        if (thumb != null)
            add(thumb);

        JLabel no_image = new JLabel("", SwingConstants.CENTER);
        url = ClassLoader.getSystemResource("icons/image-missing.png");
        no_image.setIcon(new ImageIcon(url));

        add(no_image, BorderLayout.CENTER);
    }
}

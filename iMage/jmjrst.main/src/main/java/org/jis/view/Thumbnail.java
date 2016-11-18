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
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Thumbnail extends JPanel {
    private static final long serialVersionUID = 8236547612309540341L;

    private BufferedImage     previewImage;
    private File              file;
    private int               maxWidth;
    private int               maxHeight;

    public Thumbnail(final String filename, int maxWidth, int maxHeight) throws IOException {
        super();
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
        if (filename == null)
            throw new IllegalArgumentException("Argument filename is null.");
        file = new File(filename);
        this.previewImage = ImageIO.read(file);
        initialize();
    }

    public Thumbnail(File file, int maxWidth, int maxHeight) throws IOException {
        super();
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
        this.previewImage = ImageIO.read(file);
        initialize();
    }

    public Thumbnail(int maxWidth, int maxHeight) throws IOException {
        super();
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
        JLabel no_image = new JLabel("", SwingConstants.CENTER);
        URL url = ClassLoader.getSystemResource("icons/image-missing.png");
        no_image.setIcon(new ImageIcon(url));

        add(no_image, BorderLayout.CENTER);
    }

    private void initialize() throws IOException {
        Dimension thumbDimension = new Dimension(maxWidth, maxHeight);
        setMinimumSize(thumbDimension);
        setPreferredSize(thumbDimension);
        setMaximumSize(thumbDimension);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (this.previewImage == null)
            return;

        Graphics2D g2 = (Graphics2D) g;

        boolean quality = true;
        Object interpolating = RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR;
        Object rendering = RenderingHints.VALUE_RENDER_SPEED;
        Object colorRendering = RenderingHints.VALUE_COLOR_RENDER_SPEED;
        Object antialiasing = RenderingHints.VALUE_ANTIALIAS_OFF;
        Object dithering = RenderingHints.VALUE_DITHER_DISABLE;

        if (quality) {
            interpolating = RenderingHints.VALUE_INTERPOLATION_BICUBIC;
            rendering = RenderingHints.VALUE_RENDER_QUALITY;
            colorRendering = RenderingHints.VALUE_COLOR_RENDER_QUALITY;
            antialiasing = RenderingHints.VALUE_ANTIALIAS_ON;
            dithering = RenderingHints.VALUE_DITHER_ENABLE;
        }

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, interpolating);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, rendering);
        g2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, colorRendering);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, antialiasing);
        g2.setRenderingHint(RenderingHints.KEY_DITHERING, dithering);

        int start_x = 5;
        int start_y = 5;

        int w = previewImage.getWidth();
        int h = previewImage.getHeight();
        if (w > h) // 220,165
        {
            Image bi = previewImage.getScaledInstance(maxWidth, maxHeight, Image.SCALE_FAST);
            start_x = (getWidth() / 2) - (maxWidth / 2);
            start_y = (getHeight() / 2) - (maxHeight / 2);
            g2.drawImage(bi, start_x, start_y, maxWidth, maxHeight, null);
        } else {
            Image bi = previewImage.getScaledInstance(maxHeight, maxWidth, Image.SCALE_FAST);
            start_x = (getWidth() / 2) - (maxHeight / 2);
            start_y = (getHeight() / 2) - (maxWidth / 2);
            g2.drawImage(bi, start_x, start_y, maxHeight, maxWidth, null);
        }
    }

    public File getFile() {
        return file;
    }

    public BufferedImage getImage() {
        return previewImage;
    }

    public void setImage(BufferedImage image) {
        this.previewImage = image;
    }
}

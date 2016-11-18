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
package org.jis.generator;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Locale;
import java.util.Vector;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataController;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.imageio.stream.MemoryCacheImageOutputStream;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.ProgressMonitor;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileSystemView;

import org.jis.Main;
import org.jis.options.Options;

/**
 * @author <a href="http://www.jgeppert.com">Johannes Geppert</a>
 * 
 * <p>
 * This class do the main work and resize the images.
 * </p>
 */
public class Generator {
    public final static double ROTATE_90  = Math.toRadians(90);
    public final static double ROTATE_270 = Math.toRadians(270);

    private Main               m;
    private Options            o;
    private File               zipFile    = null;
    private Vector<File>       zipIt;
    private boolean            zippen     = false;
    private float              quality;

    /**
     * @param m a reference to the Main Class.
     */
    public Generator(Main m, float quality) {
        super();
        this.m = m;
        this.o = Options.getInstance();
        this.quality = quality;
    }

    /**
     * @param zipFileName File, the Name of the new ZIP-File
     * @param selected Vector, the Images for the ZIP-File
     */
    public void createZip(File zipFileName, Vector<File> selected) {
        try {
            byte[] buffer = new byte[4096];

            // Create the new ZIP-Fiel and set the Options
            ZipOutputStream out = new ZipOutputStream(
                    new BufferedOutputStream(new FileOutputStream(zipFileName), 8096));
            out.setLevel(Deflater.BEST_COMPRESSION);
            out.setMethod(ZipOutputStream.DEFLATED);

            // Loop about the Files and put it into the new ZIP-File
            for (int i = 0; i < selected.size(); i++) {
                FileInputStream in = new FileInputStream(selected.get(i));

                String file = selected.get(i).getPath();
                if (file.indexOf("\\") != -1)
                    file = file.substring(file.lastIndexOf(Options.fs) + 1, file.length());

                ZipEntry ze = new ZipEntry(file);
                out.putNextEntry(ze);
                int len;
                while ((len = in.read(buffer)) > 0)
                    out.write(buffer, 0, len);
                out.closeEntry();
                in.close();

                // Delete the temp image
                selected.get(i).delete();
            }
            out.close();
        } catch (IllegalArgumentException iae) {
            iae.printStackTrace();
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * @param zip boolean, should the output zipped?
     */
    public void generate(boolean zip) {
        // check if folder empty
        if (!zip) {
            File outputDir = new File(o.getOutput_dir());

            if (outputDir.isDirectory() && outputDir.listFiles().length > 0) {
                int response = JOptionPane.showConfirmDialog(m.list,
                        m.mes.getString("Generator.53") + " " + o.getOutput_dir() + " "
                                + m.mes.getString("Generator.54"),
                        m.mes.getString("Generator.52"), JOptionPane.YES_NO_OPTION);
                if (response != JOptionPane.YES_OPTION) {
                    return;
                }
            }
        }

        this.quality = o.getQuality();
        try {
            zipIt = new Vector<File>();
            zippen = false;
            zipFile = null;

            // if zip true, get the ZIP-File
            if (zip) {
                zippen = true;
                JFileChooser fo = new JFileChooser();
                fo.setFileFilter(new FileFilter() {
                    public boolean accept(File f) {
                        return f.isDirectory() || f.getName().toLowerCase().endsWith(".zip");
                    }

                    public String getDescription() {
                        return "ZIP-Datei";
                    }
                });

                fo.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fo.setCurrentDirectory(
                        FileSystemView.getFileSystemView().getParentDirectory(new File(o.getOutput_dir())));
                int returnVal = fo.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION)
                    zipFile = fo.getSelectedFile();
            }

            File[] dir = new File[0];
            // generate only selected Images or the whole directory?
            if (m.list.getSelectedValues().size() == 0)
                dir = m.list.getPictures();
            else if (m.list.getSelectedValues().size() > 0
                    && m.list.getSelectedValues().size() < m.list.getPictures().length) {
                int response = JOptionPane.showConfirmDialog(m.list, m.mes.getString("Generator.23"),
                        m.mes.getString("Generator.24"), JOptionPane.YES_NO_CANCEL_OPTION);
                switch (response) {
                case JOptionPane.YES_OPTION:
                    Vector<File> vf = m.list.getSelectedValues();
                    dir = new File[vf.size()];
                    for (int i = 0; i < dir.length; i++)
                        dir[i] = vf.get(i);
                    ;
                    break; // generate only the selected images
                case JOptionPane.NO_OPTION:
                    dir = m.list.getPictures();
                    break; // generate the whole directory
                case JOptionPane.CANCEL_OPTION:
                    return; // do nothing
                case JOptionPane.CLOSED_OPTION:
                    return; // do nothing
                }
            } else {
                Vector<File> vf = m.list.getSelectedValues();
                dir = new File[vf.size()];
                for (int i = 0; i < dir.length; i++) {
                    dir[i] = vf.get(i);
                }
            }

            final File files[] = dir;
            Thread t = new Thread() {
                public void run() {
                    String p_titel = files.length + m.mes.getString("Generator.28") + files[0].getParent()
                            + m.mes.getString("Generator.29") + (Options.getInstance().getQuality() * 100)
                            + m.mes.getString("Generator.30");
                    m.p_monitor = new ProgressMonitor(m, p_titel, m.mes.getString("Generator.10"), 0, files.length);
                    m.p_monitor.setMillisToPopup(0);
                    m.p_monitor.setMillisToDecideToPopup(0);
                    m.status.setStatusOn();
                    Element[] elements = new Element[files.length];
                    for (int i = 0; i < files.length; i++)
                        elements[i] = new Element(i, files[i], Options.getInstance().getHmax(),
                                Options.getInstance().getVmax(), new File(Options.getInstance().getOutput_dir()));

                    Producer producer = new Producer(m, elements, m.mes.getString("Generator.22"));
                    Thread producerThread = new Thread(producer);
                    int cpus = Runtime.getRuntime().availableProcessors();
                    Thread consumerThreads[] = new Thread[cpus];
                    for (int i = 0; i < cpus; i++) {
                        consumerThreads[i] = new Thread(new Consumer(producer, m, zippen, zipIt));
                    }

                    producerThread.start();
                    for (int i = 0; i < cpus; i++) {
                        consumerThreads[i].start();
                    }

                    try {
                        producerThread.join();
                        for (int i = 0; i < cpus; i++) {
                            consumerThreads[i].join();
                        }
                    } catch (InterruptedException ignore) {
                    }

                    try {
                        m.jOutputDoc.insertString(m.jOutputDoc.getLength(), Options.ls + m.mes.getString("Generator.44")
                                + o.getOutput_dir() + m.mes.getString("Generator.45") + Options.ls, m.readyAtr);
                        m.text.setCaretPosition(m.jOutputDoc.getLength());
                    } catch (Exception e) {
                        System.out.println(Options.ls + m.mes.getString("Generator.46") + Options.ls);
                    }

                    // if zip, then zip
                    if (zippen && zipFile != null) {
                        m.p_monitor.setNote("Erstelle Zipdatei");
                        createZip(zipFile, zipIt);
                    }
                    m.status.setStatusOff();
                    m.p_monitor.close();
                }
            };
            t.start();

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    /**
     * <p>
     * scale the Image and write it to a specified Directory or File
     * </p>
     * 
     * @param file String, filename for the outputimage
     * @param image Image, the input image
     * @param iout File, the directory or file for the scaled image
     * @param print boolean, Logs for GUI
     * @param width int, width of the scaled image
     * @param height int, heigth of the scaled image
     * @return File
     * @throws IOException
     * @throws ImageFormatException
     */
    public File generateImage(File imageFile, File iout, boolean print, int width, int height, String praefix)
            throws IOException {

        // Output Image
        File fo = new File(iout, praefix + imageFile.getName());

        ImageInputStream iis = ImageIO.createImageInputStream(imageFile);
        Iterator<ImageReader> readers = ImageIO.getImageReaders(iis);
        ImageReader reader = readers.next();
        reader.setInput(iis, true);
        BufferedImage image = reader.read(0, null);

        IIOMetadata imageMetadata = reader.getImageMetadata(0);

        try {
            // get width and height of the origianl image
            int w = image.getWidth(null);
            int h = image.getHeight(null);

            // if image in landscape format?
            if ((w >= h || height == 0) && width > 0) {
                double tmp = (double) w / width;
                double h1 = h;
                height = (int) (h1 / tmp);
            } else {
                double tmp = (double) h / height;
                double w1 = w;
                width = (int) (w1 / tmp);
            }

            // Create new Image
            BufferedImage bimage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            // get graphics of the new Image
            Graphics2D g = bimage.createGraphics();

            // set quality of the new Image
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);

            switch (Options.getInstance().getModus()) {
            case Options.MODUS_QUALITY:
                g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
                break;

            case Options.MODUS_DEFAULT:
                g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_DEFAULT);
                g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_DEFAULT);
                break;

            case Options.MODUS_SPEED:
                g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
                g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_SPEED);
                break;

            default:
                g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_DEFAULT);
                g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_DEFAULT);
                break;
            }

            if (Options.getInstance().isAntialiasing()) {
                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            } else {
                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
            }
            g.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);

            // set white Background of the new Image
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, width, height);

            // create a scaled instance of the new Image
            Image scale;

            if (width < 300 || height < 300)
                scale = image.getScaledInstance(width, height, Image.SCALE_AREA_AVERAGING);
            else
                scale = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            g.drawImage(scale, 0, 0, null);

            int font_size = (int) (width * 0.02);

            if (o.isCopyright()) {
                g.setColor(new Color(o.getCopyright_r(), o.getCopyright_g(), o.getCopyright_b()));
                Font font1 = new Font("Helvetica", Font.BOLD, font_size);
                g.setFont(font1);
                g.drawString(o.getCopyrightText(), font_size, height - (2 * font_size));
            }

            // Print process info for the GUI
            if (print)
                try {
                    m.jOutputDoc.insertString(m.jOutputDoc.getLength(), m.mes.getString("Generator.20"), m.outputAtr);
                    m.text.setCaretPosition(m.jOutputDoc.getLength());

                } catch (Exception e) {
                    System.out.print(". . ");
                }

            // create new File for the new Image
            ImageWriter writer = ImageIO.getImageWritersByFormatName("jpg").next();
            ImageOutputStream ios;

            if (iout.isDirectory()) {
                ios = new MemoryCacheImageOutputStream(new FileOutputStream(fo));
            } else {
                ios = new MemoryCacheImageOutputStream(new FileOutputStream(iout));
            }
            writer.setOutput(ios);

            JPEGImageWriteParam iwparam = new JPEGImageWriteParam(Locale.getDefault());
            iwparam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);

            // set JPEG Quality
            iwparam.setCompressionQuality(o.getQuality());
            iwparam.setOptimizeHuffmanTables(true);

            // copy the metadata
            if (o.isCopyMetadata()) {
                writer.write(null, new IIOImage(bimage, null, imageMetadata), iwparam);
            } else {
                writer.write(null, new IIOImage(bimage, null, null), iwparam);
            }

            bimage.getGraphics().dispose();
            writer.dispose();

        } catch (Exception l) {
            m.error = true;
        }

        return fo;
    }

    /**
     * <p>
     * rotate the Image and write it to the File
     * </p>
     * 
     * @param file File
     */
    public void rotate(File file) {
        BufferedImage i = null;
        IIOMetadata imeta = null;

        try {
            ImageInputStream iis = ImageIO.createImageInputStream(file);
            ImageReader reader = ImageIO.getImageReadersByFormatName("jpg").next();
            reader.setInput(iis, true);
            ImageReadParam params = reader.getDefaultReadParam();
            i = reader.read(0, params);
            imeta = reader.getImageMetadata(0);
        } catch (IOException e) {
            System.err.println("Error while reading File: " + file.getAbsolutePath());
            e.printStackTrace();
            return;
        }

        try {
            // get width and height of the original image
            int w = i.getWidth(null);
            int h = i.getHeight(null);
            System.out.println("Width: " + w + " Height :" + h);

            System.out.println("Drehe Bild:" + file.getAbsolutePath());
            AffineTransform rotation = new AffineTransform();
            AffineTransformOp rotator;
            rotation.translate(h, 0);
            rotation.rotate(90.0 * Math.PI / 180.0);

            rotator = new AffineTransformOp(rotation, AffineTransformOp.TYPE_BICUBIC);
            i = rotator.filter(i, null);
            w = i.getWidth(null);
            h = i.getHeight(null);
            System.out.println("Width: " + w + " Height :" + h);

            System.out.println("Speichere Bild:" + file.getAbsolutePath());
            ImageWriter writer = ImageIO.getImageWritersByFormatName("jpg").next();

            writer.setOutput(new MemoryCacheImageOutputStream(new FileOutputStream(file)));
            ImageWriteParam iwparam = new JPEGImageWriteParam(Locale.getDefault());
            iwparam.setCompressionMode(ImageWriteParam.MODE_COPY_FROM_METADATA);
            IIOMetadata meta_convert = writer.convertImageMetadata(imeta, new ImageTypeSpecifier(i), iwparam);
            IIOMetadataController imc = meta_convert.getController();
            imc.activate(meta_convert);

            writer.write(meta_convert, new IIOImage(i, null, imeta), iwparam);
            writer.dispose();
            System.out.println("Bild gespeichert!");
        } catch (Exception l) {
            m.error = true;
        }
    }

    /**
     * <p>
     * Resize a single image
     * </p>
     * 
     * @param file File, input Image File
     * @param image BufferedImage, input Image
     */
    public void generateSingle(File file, BufferedImage image) {

        // where shuld the image saved?
        JOptionPane.showMessageDialog(null, m.mes.getString("Generator.15"));

        // select the output image
        JFileChooser fo = new JFileChooser();
        fo.setFileFilter(new FileFilter() {
            public boolean accept(File f) {
                return f.isDirectory() || f.getName().toLowerCase().endsWith(".jpg")
                        || f.getName().toLowerCase().endsWith(".jpeg");
            }

            public String getDescription() {
                return "JPEG-Datei";
            }
        });

        fo.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fo.setCurrentDirectory(FileSystemView.getFileSystemView().getParentDirectory(file));
        int returnVal = fo.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            m.status.setStatusOn();
            try {
                // resize the image
                generateImage(file, fo.getSelectedFile(), false, o.getHmax(), o.getVmax(), "");
            } catch (Exception e) {
                e.printStackTrace();
            }
            m.status.setStatusOff();

            // show success message
            JOptionPane.showMessageDialog(null, m.mes.getString("Generator.19"));
        }
    }

    /**
     * <p>
     * Resize the Images without the GUI, when the Programm is started with
     * Arguments
     * </p>
     * 
     * @param input File, the Input Directory
     * @param output File, the Output Directory
     * @param width int, width of the scaled image
     * @param height int, heigth of the scaled image
     */
    public void generateText(File input, File output, int width, int height) {

        // check if mass resize or single picture resize
        if (input.isDirectory() && output.isDirectory())
            try {

                // get all JPEGs of the directory
                File[] dir = input.listFiles();
                Vector<File> v = new Vector<File>();
                for (int i = 0; i < dir.length; i++)
                    // text.setText(text.getText() + dir[i].toString() + "\n");
                    try {
                        String end = dir[i].toString().substring(dir[i].toString().lastIndexOf(".") + 1,
                                dir[i].toString().length());
                        if (dir[i].isFile() && (end.equalsIgnoreCase("jpg") || end.equalsIgnoreCase("jpeg")))
                            v.addElement(dir[i]);
                    } catch (Exception st) {
                    }

                // print info message
                System.out.println(
                        v.size() + m.mes.getString("Generator.28") + input.toString() + m.mes.getString("Generator.29")
                                + quality + m.mes.getString("Generator.30") + Options.ls + Options.ls);

                // resize the images
                for (int i = 0; i < v.size(); i++) {
                    System.out.print(m.mes.getString("Generator.10") + v.elementAt(i).getName() + "\t . . . ");
                    generateImage(v.elementAt(i), output, true, o.getHmax(), o.getVmax(),
                            m.mes.getString("Generator.22"));
                    System.out.println(m.mes.getString("Generator.12"));
                }
                System.out.println(Options.ls + v.size() + m.mes.getString("Generator.46") + Options.ls);
            } catch (Exception ex) {
                System.out.println(ex);
            }
        else if (input.isFile()) {
            try {
                // resize single image
                generateImage(input, output, true, o.getHmax(), o.getVmax(), m.mes.getString("Generator.22"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * <p>
     * <b>Not used because loose of Metadata and Quality</b> rotate the Image
     * and write it to the File
     * </p>
     * 
     * @param file File
     */
    public void rotate(File file, int angel) {
        BufferedImage i = null;
        IIOMetadata imeta = null;

        try {
            ImageInputStream iis = ImageIO.createImageInputStream(file);
            ImageReader reader = ImageIO.getImageReadersByFormatName("jpg").next();
            reader.setInput(iis, true);
            ImageReadParam params = reader.getDefaultReadParam();
            i = reader.read(0, params);
            imeta = reader.getImageMetadata(0);
        } catch (IOException e) {
            System.err.println("Error while reading File: " + file.getAbsolutePath());
            e.printStackTrace();
            return;
        }

        try {
            // get width and height of the origianl image
            int w = i.getWidth(null);
            int h = i.getHeight(null);
            rotateImage(i, 90);
            System.out.println("Width: " + w + " Height :" + h);

            System.out.println("Drehe Bild:" + file.getAbsolutePath());
            i = rotateImage(i, angel);

            FileOutputStream fos = new FileOutputStream(file);

            ImageWriter writer = ImageIO.getImageWritersByFormatName("jpg").next();
            ImageOutputStream ios = ImageIO.createImageOutputStream(fos);
            writer.setOutput(ios);
            ImageWriteParam iwparam = new JPEGImageWriteParam(Locale.getDefault());
            iwparam.setCompressionMode(ImageWriteParam.MODE_COPY_FROM_METADATA);

            // set JPEG Quality
            iwparam.setCompressionQuality(0.92f);
            writer.write(imeta, new IIOImage(i, null, null), iwparam);
            ios.flush();
            writer.dispose();
            ios.close();
            fos.close();

            System.out.println("Bild gespeichert!");
        } catch (Exception l) {
            m.error = true;
        }
    }

    public BufferedImage rotateImage(BufferedImage image, double rotate) {
        if (rotate == 0)
            return image;

        if (rotate == 1) {
            throw new IllegalArgumentException("degree must not be 360 degrees!");
        }

        AffineTransform transform = new AffineTransform();

        // get width and height of the origianl image
        int width = image.getWidth(null);
        int height = image.getHeight(null);

        if (rotate == Generator.ROTATE_90) {
            transform.translate(height, 0);
            transform.rotate(Generator.ROTATE_90);
            width = image.getHeight(); // swap
            height = image.getWidth();
        } else if (rotate == Generator.ROTATE_270) {
            transform.translate(0, width);
            transform.rotate(Generator.ROTATE_270);
            width = image.getHeight(null); // swap
            height = image.getWidth(null);
        } else {
            throw new IllegalArgumentException("degree must be a mutiple of 90�!");
        }

        // Return a new Image
        BufferedImage returnImage = new BufferedImage(width, height, image.getColorModel().getColorSpace().getType());
        Graphics2D g = returnImage.createGraphics();
        g.drawImage(image, transform, null);

        return returnImage;
    }
}

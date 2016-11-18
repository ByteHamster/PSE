package org.iMage.edge.detection.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.iMage.edge.detection.base.ImageFilter;

/**
 * Provides main window for edge detection GUI
 * 
 * @author Hans-Peter Lehmann
 * @version 1.0
 */
public class GuiFrame extends JFrame {
    private static final long         serialVersionUID = 2919659984444506990L;

    private PreviewUpdateThread       previewUpdateThread;
    private EdgeDetectionLayoutHolder layoutHolder;
    private BufferedImage             fullImage;

    /**
     * Creates a new window
     * @param autoUpdateEnabled If the UI should update automatically instead of displaying an update button
     */
    public GuiFrame(boolean autoUpdateEnabled) {
        super();

        initWindow();
        initMenu();
        
        if (autoUpdateEnabled) {
            buildLayoutWithoutUpdateButton();
        } else {
            buildLayoutWithUpdateButton();
        }
        
        loadDefaultImage();
        previewUpdateThread = new PreviewUpdateThread(layoutHolder);
        previewUpdateThread.start();
    }

    private void initWindow() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(this);
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException
                | IllegalAccessException ignored) {
            // Using default theme
        }

        this.setSize(630, 550);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("EDGuy");
        WindowTools.centerWindow(this);
    }

    private void initMenu() {
        JMenuItem open = new JMenuItem("Open");
        open.addActionListener(openListener);

        JMenuItem save = new JMenuItem("Save");
        save.addActionListener(saveListener);

        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        JMenu file = new JMenu("File");
        file.setMnemonic(KeyEvent.VK_F);
        file.add(open);
        file.add(save);
        file.add(exit);

        JMenuItem aboutThisGui = new JMenuItem("Edge Detection GUI");
        aboutThisGui.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AboutDialog(GuiFrame.this).setVisible(true);
            }
        });

        JMenu about = new JMenu("About");
        about.setMnemonic(KeyEvent.VK_A);
        about.add(aboutThisGui);

        JMenuBar menu = new JMenuBar();
        menu.add(file);
        menu.add(about);
        setJMenuBar(menu);
    }

    private void buildLayoutWithUpdateButton() {
        layoutHolder = new EdgeDetectionLayoutHolder(EdgeDetectionFilterHelpers.getAvailableFilters());

        JButton updateButton = new JButton("Update preview");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                previewUpdateThread.requestUpdate();
            }
        });
        
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(layoutHolder);
        getContentPane().add(updateButton, BorderLayout.PAGE_END);
    }

    private void buildLayoutWithoutUpdateButton() {
        layoutHolder = new EdgeDetectionLayoutHolder(EdgeDetectionFilterHelpers.getAvailableFilters());
        layoutHolder.setValuesChangedListener(new EdgeDetectionLayoutHolder.ValuesChangedListener() {
            @Override
            public void onValuesChanged() {
                previewUpdateThread.requestUpdate();
            }
        });
        getContentPane().add(layoutHolder);
    }

    private void loadDefaultImage() {
        try {
            InputStream is = getClass().getResourceAsStream("girl.png");
            ImageInputStream iis = ImageIO.createImageInputStream(is);
            ImageReader reader = ImageIO.getImageReadersByFormatName("png").next();
            reader.setInput(iis, true);
            ImageReadParam params = reader.getDefaultReadParam();
            fullImage = reader.read(0, params);
            layoutHolder.setOriginalImage(fullImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    ActionListener openListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent event) {

            JFileChooser chooser = new JFileChooser();
            chooser.setFileFilter(
                    new FileNameExtensionFilter("Image Files", "jpg", "png"));
            int openResult = chooser.showOpenDialog(null);
            if (JFileChooser.APPROVE_OPTION == openResult) {
                try {
                    fullImage = ImageIO.read(chooser.getSelectedFile());
                    layoutHolder.setOriginalImage(fullImage);
                    previewUpdateThread.requestUpdate();
                } catch (IOException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(GuiFrame.this, e.getLocalizedMessage());
                }
            }

        }
    };

    ActionListener saveListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent event) {

            final JFileChooser chooser = new NonOverridingFileChooser();
            chooser.addChoosableFileFilter(new FileNameExtensionFilter("JPG Files", "jpg"));
            chooser.addChoosableFileFilter(new FileNameExtensionFilter("PNG Files", "png"));
            int openResult = chooser.showSaveDialog(null);

            if (JFileChooser.APPROVE_OPTION == openResult) {
                String extension = "";

                int i = chooser.getSelectedFile().getName().lastIndexOf('.');
                if (i > 0) {
                    extension = chooser.getSelectedFile().getName().substring(i + 1);
                }

                if ("png".equals(extension) || "jpg".equals(extension)) {
                    final ProgressBarDialog progressBarDialog = new ProgressBarDialog();
                    startSaveThread(progressBarDialog, chooser.getSelectedFile(),
                            extension);
                    progressBarDialog.show();
                } else {
                    JOptionPane.showMessageDialog(GuiFrame.this, "File type not supported");
                }

            }

        }
    };

    private void startSaveThread(final ProgressBarDialog progressBarDialog, final File file, final String type) {

        final ArrayList<ImageFilter> filters = EdgeDetectionFilterHelpers.calculateFilters(layoutHolder);
        progressBarDialog.setMaximum(filters.size());

        new Thread() {
            public void run() {

                try {
                    BufferedImage renderedImage = fullImage;
                    for (int i = 0; i < filters.size(); i++) {
                        progressBarDialog.update(i, filters.get(i).getClass().getSimpleName());
                        renderedImage = filters.get(i).applyFilter(renderedImage);
                    }
                    ImageIO.write(renderedImage, type, file);
                    progressBarDialog.dismiss();
                    JOptionPane.showMessageDialog(GuiFrame.this, "Saved");
                } catch (IOException e) {
                    e.printStackTrace();
                    progressBarDialog.dismiss();
                    JOptionPane.showMessageDialog(GuiFrame.this, e.getLocalizedMessage());
                }
            }
        }.start();
    }
}

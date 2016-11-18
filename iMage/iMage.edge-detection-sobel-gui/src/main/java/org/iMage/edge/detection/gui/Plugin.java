package org.iMage.edge.detection.gui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import org.iMage.edge.detection.base.ImageFilter;
import org.iMage.plugins.JmjrstPlugin;
import org.jis.Main;
import org.jis.options.Options;
import org.kohsuke.MetaInfServices;

/**
 * The edge detection plugin
 * 
 * @author Hans-Peter Lehmann
 * @version 1.0
 */
@MetaInfServices
public class Plugin extends JmjrstPlugin {

	private Main main;
	private GuiFrameForPlugin guiPlugin;

	@Override
	public void configure() {
		guiPlugin.setVisible(true);
	}

	@Override
	public String getMenuText() {
		return getName();
	}

	@Override
	public String getName() {
		return "EDGuy";
	}

	@Override
	public void init(Main arg0) {
		main = arg0;
		guiPlugin = new GuiFrameForPlugin();
	}

	@Override
	public boolean isConfigurable() {
		return true;
	}

	@Override
	public void run() {

		// Printing configuration to System.out
		System.out.println(guiPlugin.getConfiguration());

		Vector<File> vf = main.list.getSelectedValues();
		final File[] dir = new File[vf.size()];
		for (int i = 0; i < dir.length; i++) {
			dir[i] = vf.get(i);
		}

		final ProgressBarDialog progressBarDialog = new ProgressBarDialog();
		final ArrayList<ImageFilter> filters = guiPlugin.getFilters();
		progressBarDialog.setMaximum(dir.length * filters.size() + 1);
		progressBarDialog.update(0, 0 + "/" + dir.length);

		new Thread() {
			public void run() {
				for (int i = 0; i < dir.length; i++) {
					try {
						File outputFile = new File(Options.getInstance().getOutput_dir(), dir[i].getName());
						System.out.println("Creating " + outputFile.getAbsolutePath());
						String extension = "";

						int pos = dir[i].getName().lastIndexOf('.');
						if (pos > 0) {
							extension = dir[i].getName().substring(pos + 1).toLowerCase();
						}

						if (!"png".equals(extension) && !"jpg".equals(extension)) {
							progressBarDialog.dismiss();
							JOptionPane.showMessageDialog(guiPlugin, "Frong input file format");
							return;
						}

						if (outputFile.exists()) {
							progressBarDialog.dismiss();
							JOptionPane.showMessageDialog(guiPlugin, "Output file exists");
							return;
						}

						BufferedImage renderedImage = ImageIO.read(dir[i]);
						for (int k = 0; k < filters.size(); k++) {
							progressBarDialog.update((i * filters.size()) + (k + 1),
									"File " + (i + 1) + "/" + dir.length);
							System.out.println("  Applying " + filters.get(k).getClass().getSimpleName());
							renderedImage = filters.get(k).applyFilter(renderedImage);
						}

						ImageIO.write(renderedImage, extension, outputFile);
					} catch (IOException e) {
						e.printStackTrace();
						progressBarDialog.dismiss();
						JOptionPane.showMessageDialog(guiPlugin, e.getLocalizedMessage());
						return;
					}
				}
				progressBarDialog.dismiss();
				JOptionPane.showMessageDialog(guiPlugin, "Saved");
			}
		}.start();
		progressBarDialog.show();
	}

}

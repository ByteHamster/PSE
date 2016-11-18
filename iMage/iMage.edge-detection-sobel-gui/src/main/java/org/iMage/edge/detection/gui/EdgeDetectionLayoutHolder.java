package org.iMage.edge.detection.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Contains all the layout elements that are necessary to build the edge detection GUI
 * 
 * @author Hans-Peter Lehmann
 * @version 1.0
 */
public class EdgeDetectionLayoutHolder extends JPanel {
	private static final long serialVersionUID = -1779836843990398138L;

	private TitledImagePanel previewOriginal;
	private TitledImagePanel previewRendered;
	private JCheckBox optionsBlur;
	private JComboBox<String> optionsFilterSelect;
	private JCheckBox optionsThresholdCheckbox;
	private JSlider optionsThresholdSlider;

	private ValuesChangedListener listener;
	private String[] supportedFilters;

	/**
	 * Holds all layout elements necessary to apply edge detetion filters
	 * 
	 * @param supportedFilters
	 *            The filters that this implementation of an edge detector supports
	 */
	public EdgeDetectionLayoutHolder(String[] supportedFilters) {
		this.supportedFilters = supportedFilters;
		buildLayout();
	}

	private void buildLayout() {

		previewOriginal = new TitledImagePanel("Original");
		previewOriginal.setBorder(new EmptyBorder(0, 10, 10, 10));
		previewRendered = new TitledImagePanel("Rendered");
		previewRendered.setBorder(new EmptyBorder(0, 10, 10, 10));

		JPanel previewPanel = new JPanel();
		previewPanel.setBackground(Color.decode("#f2f2f2"));
		previewPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		previewPanel.setLayout(new BoxLayout(previewPanel, BoxLayout.X_AXIS));
		previewPanel.add(previewOriginal);
		previewPanel.add(previewRendered);

		optionsBlur = new JCheckBox("Blur");
		optionsBlur.setSelected(true);
		optionsBlur.addActionListener(settingsChangeListener);

		optionsFilterSelect = new JComboBox<String>(supportedFilters);
		optionsFilterSelect.setBorder(new EmptyBorder(10, 0, 10, 0));
		optionsFilterSelect.addActionListener(settingsChangeListener);

		optionsThresholdCheckbox = new JCheckBox("Threshold");
		optionsThresholdCheckbox.setBorder(new EmptyBorder(0, 0, 0, 10));
		optionsThresholdCheckbox.setSelected(true);
		optionsThresholdCheckbox.addActionListener(settingsChangeListener);

		optionsThresholdSlider = new JSlider();
		optionsThresholdSlider.setMaximum(255);
		optionsThresholdSlider.setValue(127);
		optionsThresholdSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if (listener != null) {
					listener.onValuesChanged();
				}
			}
		});

		JPanel optionsThreshold = new JPanel();
		optionsThreshold.setLayout(new BoxLayout(optionsThreshold, BoxLayout.X_AXIS));
		optionsThreshold.add(optionsThresholdCheckbox);
		optionsThreshold.add(optionsThresholdSlider);

		JPanel optionsPanel = new JPanel();
		optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
		optionsPanel.add(WindowTools.leftJustify(optionsBlur));
		optionsPanel.add(optionsFilterSelect);
		optionsPanel.add(optionsThreshold);
		optionsPanel.setBorder(new EmptyBorder(20, 120, 20, 120));

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(previewPanel);
		add(optionsPanel);
	}

	ActionListener settingsChangeListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			optionsThresholdSlider.setEnabled(optionsThresholdCheckbox.isSelected());
			if (listener != null) {
				listener.onValuesChanged();
			}
		}
	};

	/**
	 * Sets the listener that is called if some value changes
	 * 
	 * @param listener
	 *            The listener to set
	 */
	public void setValuesChangedListener(ValuesChangedListener listener) {
		this.listener = listener;
	}

	/**
	 * @return If the blur checkbox is enabled
	 */
	public boolean isBlur() {
		return optionsBlur.isSelected();
	}

	/**
	 * @return If the threshold checkbox is enabled
	 */
	public boolean isThreshold() {
		return optionsThresholdCheckbox.isSelected();
	}

	/**
	 * @return Value of the threshold slider
	 */
	public int getThreshold() {
		return optionsThresholdSlider.getValue();
	}

	/**
	 * @return The filter that is selected
	 */
	public String getSelectedFilter() {
		return (String) optionsFilterSelect.getSelectedItem();
	}

	/**
	 * Sets the image that is displayed below the "Original" caption
	 * @param fullImage The image to set. Scaled automatically.
	 */
	public void setOriginalImage(BufferedImage fullImage) {
		previewOriginal.setImage(fullImage);
	}

	/**
	 * Sets the image that is displayed below the "Rendered" caption
	 * @param fullImage The image to set. Scaled automatically.
	 */
	public void setRenderedImage(BufferedImage fullImage) {
		previewRendered.setImage(fullImage);
	}

	/**
	 * @return The scaled image that is used for the "Original" preview
	 */
	public BufferedImage getPreviewImage() {
		return previewOriginal.getPreviewImage();
	}


	/**
	 * Abstract class that can be used to provide a listener that fires if the values on this holder change
	 */
	public abstract static class ValuesChangedListener {
		/**
		 * Fired when a value changed
		 */
		public abstract void onValuesChanged();
	}
}

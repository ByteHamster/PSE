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
package org.jis.options;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;

/**
 * @author <a href="http://www.jgeppert.com">Johannes Geppert</a>
 * 
 * <p>
 * The Option Class is a Singleton Class for reading and storing the Options to
 * options.properties
 * </p>
 */
public class Options implements Serializable {
    private static final long    serialVersionUID    = -4716608961164613957L;

    private static final Options INSTANCE            = new Options();
    public static final String   fs                  = System.getProperty("file.separator");
    public static final String   ls                  = System.getProperty("line.separator");
    public static final int      MODUS_QUALITY       = 2;
    public static final int      MODUS_DEFAULT       = 1;
    public static final int      MODUS_SPEED         = 0;

    private final String         uh                  = System.getProperty("user.home");
    private File                 f                   = new File("options.properties");
    private String               LookAndFeel         = "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel";
    private Locale               local               = new Locale("en");
    private String               input_dir           = uh;
    private String               output_dir          = uh;
    private String               output_dir_gallerie = uh;
    private String               gallerieTitle       = "My Gallerie";
    private String               gallerieSubTitle    = "";
    private String               copyrightText       = "";
    private float                quality             = 0.80F;
    private int                  modus               = 2;                                                 // Width
    private int                  hmax                = 1024;                                              // Width
    private int                  vmax                = 768;                                               // Heigth
    private int                  gallerieWidth       = 4;
    private int                  gallerieHeigth      = 4;
    private int                  background_r        = 0;
    private int                  background_g        = 153;
    private int                  background_b        = 153;
    private int                  foreground_r        = 255;
    private int                  foreground_g        = 255;
    private int                  foreground_b        = 255;
    private int                  copyright_r         = 255;
    private int                  copyright_g         = 255;
    private int                  copyright_b         = 255;
    private boolean              copyright           = false;
    private boolean              initial             = true;
    private boolean              textbox             = false;
    private boolean              antialiasing        = true;
    private boolean              copyMetadata        = true;

    private Options() {
        super();
        try {
            Properties p = new Properties();
            p.loadFromXML(new FileInputStream(f));
            local = new Locale(p.getProperty("local"));
            LookAndFeel = p.getProperty("lookandfeel");
            input_dir = p.getProperty("input_dir");
            output_dir = p.getProperty("output_dir");
            quality = Float.parseFloat(p.getProperty("quality"));
            hmax = Integer.parseInt(p.getProperty("hmax"));
            vmax = Integer.parseInt(p.getProperty("vmax"));
            background_r = Integer.parseInt(p.getProperty("background_r"));
            background_g = Integer.parseInt(p.getProperty("background_g"));
            background_b = Integer.parseInt(p.getProperty("background_b"));
            foreground_r = Integer.parseInt(p.getProperty("foreground_r"));
            foreground_g = Integer.parseInt(p.getProperty("foreground_g"));
            foreground_b = Integer.parseInt(p.getProperty("foreground_b"));
            gallerieWidth = Integer.parseInt(p.getProperty("gallerieWidth"));
            gallerieHeigth = Integer.parseInt(p.getProperty("gallerieHeigth"));
            initial = Boolean.parseBoolean(p.getProperty("initial"));
            gallerieTitle = p.getProperty("gallerieTitle");
            gallerieSubTitle = p.getProperty("gallerieSubTitle");
            output_dir_gallerie = p.getProperty("output_dir_gallerie");
            try {
                textbox = Boolean.parseBoolean(p.getProperty("textbox"));
                copyright = Boolean.parseBoolean(p.getProperty("copyright"));
                copyright_r = Integer.parseInt(p.getProperty("copyright_r"));
                copyright_g = Integer.parseInt(p.getProperty("copyright_g"));
                copyright_b = Integer.parseInt(p.getProperty("copyright_b"));
                copyrightText = p.getProperty("copyrightText");
                modus = Integer.parseInt(p.getProperty("modus"));
                antialiasing = Boolean.parseBoolean(p.getProperty("antialiasing"));
                copyMetadata = Boolean.parseBoolean(p.getProperty("copyMetadata"));
            } catch (RuntimeException e) {
                saveOptions();
            }
        } catch (Exception e) {
            saveOptions();
        }

    }

    /**
     * @return a Singleton Instance of the Options Object
     */
    public static Options getInstance() {
        return INSTANCE;
    }

    public String getLookAndFeel() {
        return LookAndFeel;
    }

    public void setLookAndFeel(String lookAndFeel) {
        LookAndFeel = lookAndFeel;
        saveOptions();
    }

    public void saveOptions() {
        try {
            Properties p = new Properties();
            p.setProperty("local", "" + local.getLanguage());
            p.setProperty("lookandfeel", "" + LookAndFeel);
            p.setProperty("quality", "" + quality);
            p.setProperty("modus", "" + modus);
            p.setProperty("hmax", "" + hmax);
            p.setProperty("vmax", "" + vmax);
            p.setProperty("input_dir", "" + input_dir);
            p.setProperty("output_dir", "" + output_dir);
            p.setProperty("background_r", "" + background_r);
            p.setProperty("background_g", "" + background_g);
            p.setProperty("background_b", "" + background_b);
            p.setProperty("foreground_r", "" + foreground_r);
            p.setProperty("foreground_g", "" + foreground_g);
            p.setProperty("foreground_b", "" + foreground_b);
            p.setProperty("gallerieWidth", "" + gallerieWidth);
            p.setProperty("gallerieHeigth", "" + gallerieHeigth);
            p.setProperty("gallerieTitle", "" + gallerieTitle);
            p.setProperty("gallerieSubTitle", "" + gallerieSubTitle);
            p.setProperty("output_dir_gallerie", "" + output_dir_gallerie);
            p.setProperty("initial", "" + initial);
            p.setProperty("antialiasing", "" + antialiasing);
            p.setProperty("copyMetadata", "" + copyMetadata);
            p.setProperty("textbox", "" + textbox);
            p.setProperty("copyright", "" + copyright);
            p.setProperty("copyrightText", "" + copyrightText);
            p.setProperty("copyright_r", "" + copyright_r);
            p.setProperty("copyright_g", "" + copyright_g);
            p.setProperty("copyright_b", "" + copyright_b);

            p.storeToXML(new FileOutputStream(f), new Date(System.currentTimeMillis()).toString());
        } catch (Exception o) {
            System.err.println(o.getMessage());
        }
    }

    public String getInput_dir() {
        return input_dir;
    }

    public void setInput_dir(String input_dir) {
        this.input_dir = input_dir;
        saveOptions();
    }

    public String getOutput_dir() {
        return output_dir;
    }

    public void setOutput_dir(String output_dir) {
        this.output_dir = output_dir;
        saveOptions();
    }

    public float getQuality() {
        return quality;
    }

    public void setQuality(float quality) {
        this.quality = quality;
        saveOptions();
    }

    public int getHmax() {
        return hmax;
    }

    public void setHmax(int hmax) {
        this.hmax = hmax;
        saveOptions();
    }

    public int getVmax() {
        return vmax;
    }

    public void setVmax(int vmax) {
        this.vmax = vmax;
        saveOptions();
    }

    public Locale getLocal() {
        return local;
    }

    public void setLocal(Locale local) {
        this.local = local;
    }

    public int getBackground_b() {
        return background_b;
    }

    public void setBackground_b(int background_b) {
        this.background_b = background_b;
        saveOptions();
    }

    public int getBackground_g() {
        return background_g;
    }

    public void setBackground_g(int background_g) {
        this.background_g = background_g;
        saveOptions();
    }

    public int getBackground_r() {
        return background_r;
    }

    public void setBackground_r(int background_r) {
        this.background_r = background_r;
        saveOptions();
    }

    public int getForeground_b() {
        return foreground_b;
    }

    public void setForeground_b(int foreground_b) {
        this.foreground_b = foreground_b;
        saveOptions();
    }

    public int getForeground_g() {
        return foreground_g;
    }

    public void setForeground_g(int foreground_g) {
        this.foreground_g = foreground_g;
        saveOptions();
    }

    public int getForeground_r() {
        return foreground_r;
    }

    public void setForeground_r(int foreground_r) {
        this.foreground_r = foreground_r;
        saveOptions();
    }

    public int getGallerieHeigth() {
        return gallerieHeigth;
    }

    public void setGallerieHeigth(int gallerieHeigth) {
        this.gallerieHeigth = gallerieHeigth;
        saveOptions();
    }

    public String getGallerieTitle() {
        return gallerieTitle;
    }

    public void setGallerieTitle(String gallerieTitle) {
        this.gallerieTitle = gallerieTitle;
        saveOptions();
    }

    public int getGallerieWidth() {
        return gallerieWidth;
    }

    public void setGallerieWidth(int gallerieWidth) {
        this.gallerieWidth = gallerieWidth;
        saveOptions();
    }

    public String getOutput_dir_gallerie() {
        return output_dir_gallerie;
    }

    public void setOutput_dir_gallerie(String output_dir_gallerie) {
        this.output_dir_gallerie = output_dir_gallerie;
        saveOptions();
    }

    public boolean isInitial() {
        return initial;
    }

    public void setInitial(boolean initial) {
        this.initial = initial;
    }

    public String getGallerieSubTitle() {
        return gallerieSubTitle;
    }

    public void setGallerieSubTitle(String gallerieSubTitle) {
        this.gallerieSubTitle = gallerieSubTitle;
    }

    public boolean isTextbox() {
        return textbox;
    }

    public void setTextbox(boolean textbox) {
        this.textbox = textbox;
    }

    public boolean isCopyright() {
        return copyright;
    }

    public void setCopyright(boolean copyright) {
        this.copyright = copyright;
    }

    public int getCopyright_b() {
        return copyright_b;
    }

    public void setCopyright_b(int copyright_b) {
        this.copyright_b = copyright_b;
    }

    public int getCopyright_g() {
        return copyright_g;
    }

    public void setCopyright_g(int copyright_g) {
        this.copyright_g = copyright_g;
    }

    public int getCopyright_r() {
        return copyright_r;
    }

    public void setCopyright_r(int copyright_r) {
        this.copyright_r = copyright_r;
    }

    public String getCopyrightText() {
        return copyrightText;
    }

    public void setCopyrightText(String copyrightText) {
        this.copyrightText = copyrightText;
    }

    public boolean isAntialiasing() {
        return antialiasing;
    }

    public void setAntialiasing(boolean antialiasing) {
        this.antialiasing = antialiasing;
    }

    public int getModus() {
        return modus;
    }

    public void setModus(int modus) {
        this.modus = modus;
    }

    public boolean isCopyMetadata() {
        return copyMetadata;
    }

    public void setCopyMetadata(boolean copyMetadata) {
        this.copyMetadata = copyMetadata;
    }

}

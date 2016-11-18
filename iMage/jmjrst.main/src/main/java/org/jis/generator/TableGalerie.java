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

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.ProgressMonitor;

import org.jis.Main;
import org.jis.options.Options;

/**
 * @author <a href="http://www.jgeppert.com">Johannes Geppert</a>
 * 
 * <p>
 * this class build the Web Gallerie
 * </p>
 */
public class TableGalerie {
    private final String ls = System.getProperty("line.separator");
    private File[]       images;
    private File         directory;
    private Main         m;
    private Options      o;

    /**
     * @param main the main class
     */
    public TableGalerie(Main main) {
        super();
        o = Options.getInstance();
        this.directory = new File(o.getOutput_dir_gallerie());
        this.m = main;

        if (m.list.getSelectedValues().size() == 0)
            this.images = m.list.getPictures();
        else if (m.list.getSelectedValues().size() > 0
                && m.list.getSelectedValues().size() < m.list.getPictures().length) {
            int response = JOptionPane.showConfirmDialog(m.list, m.mes.getString("Generator.23"),
                    m.mes.getString("Generator.24"), JOptionPane.YES_NO_CANCEL_OPTION);
            switch (response) {
            case JOptionPane.YES_OPTION:
                Vector<File> vf = m.list.getSelectedValues();
                this.images = new File[vf.size()];
                for (int i = 0; i < this.images.length; i++)
                    this.images[i] = vf.get(i);
                ;
                break; // generate only the selected images
            case JOptionPane.NO_OPTION:
                this.images = m.list.getPictures();
                break; // generate the whole directory
            case JOptionPane.CANCEL_OPTION:
                return; // do nothing
            case JOptionPane.CLOSED_OPTION:
                return; // do nothing
            }
        } else {
            Vector<File> vf = m.list.getSelectedValues();
            this.images = new File[vf.size()];
            for (int i = 0; i < this.images.length; i++)
                this.images[i] = vf.get(i);
        }

        // print main info
        try {
            m.jOutputDoc.remove(0, m.jOutputDoc.getLength());
            m.jOutputDoc.insertString(m.jOutputDoc.getLength(),
                    images.length + m.mes.getString("Generator.28") + images[0].getParent()
                            + m.mes.getString("Generator.29") + o.getQuality() + m.mes.getString("Generator.30") + ls
                            + ls,
                    m.outputAtr);
            m.text.setCaretPosition(m.jOutputDoc.getLength());
        } catch (Exception e) {
            System.out.println(images.length + m.mes.getString("Generator.31") + images[0].getPath().toString()
                    + m.mes.getString("Generator.32") + o.getQuality() + m.mes.getString("Generator.33") + ls + ls);
        }

        String p_titel = images.length + m.mes.getString("Generator.28") + this.images[0].getParentFile().getPath()
                + m.mes.getString("Generator.29") + (o.getQuality() * 100) + m.mes.getString("Generator.30");
        m.p_monitor = new ProgressMonitor(m, p_titel, m.mes.getString("Generator.10"), 0, this.images.length + 1);
        m.p_monitor.setMillisToPopup(1);
        m.p_monitor.setProgress(1);

        Thread t = new Thread() {
            public void run() {
                m.status.setStatusOn();
                createCSS();
                StringBuilder sb = new StringBuilder();
                sb.append(getHeader(o.getGallerieTitle(), true));

                sb.append("   <table>\n");

                // generate the 3 images small, medium, big
                File small = new File(directory, "small");
                File medium = new File(directory, "medium");
                File big = new File(directory, "big");
                try {
                    small.mkdir();
                    medium.mkdir();
                    big.mkdir();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                int j = 0;
                int count = 0;
                int page = 0;

                int max = images.length;
                int x = o.getGallerieHeigth() * o.getGallerieWidth();

                int lastPage = (int) Math.ceil((double) images.length / (double) x);

                File out_s;
                File out_m;
                File out_b;
                ArrayList<Element> elements = new ArrayList<Element>();

                for (int i = 0; i < images.length; i++) {
                    try {
                        m.jOutputDoc.insertString(m.jOutputDoc.getLength(), m.mes.getString("Generator.10"),
                                m.outputAtr);
                        m.jOutputDoc.insertString(m.jOutputDoc.getLength(), images[i].getName(), m.fileAtr);
                        m.jOutputDoc.insertString(m.jOutputDoc.getLength(), "\t . . ", m.outputAtr);
                        m.text.setCaretPosition(m.jOutputDoc.getLength());
                    } catch (Exception e) {
                        System.out.print(m.mes.getString("Generator.10") + images[i].getName() + "\t . . . ");
                    }

                    j++;
                    count++;

                    // generate small image
                    out_s = new File(small, images[i].getName());
                    elements.add(new Element(i, images[i], 159, 119, small));

                    try {
                        m.jOutputDoc.insertString(m.jOutputDoc.getLength(), ". ", m.outputAtr);
                        m.text.setCaretPosition(m.jOutputDoc.getLength());

                    } catch (Exception e) {
                        System.out.print(". . .  ");
                    }

                    // generate medium image
                    out_m = new File(medium, images[i].getName());
                    elements.add(new Element(i, images[i], 450, 338, medium));

                    try {
                        m.jOutputDoc.insertString(m.jOutputDoc.getLength(), ". ", m.outputAtr);
                        m.text.setCaretPosition(m.jOutputDoc.getLength());

                    } catch (Exception e) {
                        System.out.print(". . .  ");
                    }

                    // generate big image
                    out_b = new File(big, images[i].getName());
                    elements.add(new Element(i, images[i], o.getHmax(), o.getVmax(), big));

                    if (i == 0)
                        createPreview(i, medium, out_m.getName(), out_b.getName(), false, true);
                    else if (i == (images.length - 1))
                        createPreview(i, medium, out_m.getName(), out_b.getName(), true, false);
                    else
                        createPreview(i, medium, out_m.getName(), out_b.getName(), true, true);

                    try {
                        m.jOutputDoc.insertString(m.jOutputDoc.getLength(), ".  ", m.outputAtr);
                        m.text.setCaretPosition(m.jOutputDoc.getLength());

                    } catch (Exception e) {
                        System.out.print(". . .  ");
                    }

                    // ROW BEGIN
                    if (j == 1)
                        sb.append("    <tr>\n");

                    // TABLE CELL
                    String titel = createTitle(out_s.getName());
                    sb.append("      <td><a href=\"medium/image" + i + ".html\" title=\"" + titel
                            + "\"><img src=\"small/" + out_s.getName() + "\" alt=\"" + titel + "\" /></a></td>\n");

                    // ROW END
                    if (j == o.getGallerieWidth()) {
                        sb.append("    </tr>\n");
                        j = 0;
                    } else if (j < o.getGallerieWidth() && i == (max - 1)) {
                        // create empty td and complete row
                        for (int k = 0; k < (o.getGallerieWidth() - j); k++) {
                            sb.append("     <td>&nbsp;</td>\n");
                        }
                        sb.append("    </tr>\n");
                        j = 0;
                    }

                    // end of page
                    if (count == x || i == (max - 1)) {
                        count = 0;
                        page++;

                        sb.append("    <tr>\n");
                        sb.append("     <td colspan=\"" + o.getGallerieWidth()
                                + "\" style=\"width: 100%; background-color: rgb(" + o.getBackground_r() + ","
                                + o.getBackground_g() + "," + o.getBackground_b() + "); vertical-align: top;\">\n");
                        if (lastPage > 1) {
                            if (page == 1)
                                sb.append(
                                        "      <a href=\"index" + (page + 1) + ".html\" class=\"next\">&gt;&gt;</a>\n");
                            else if (page == lastPage) {
                                if (page == 2)
                                    sb.append("      <a href=\"index.html\" class=\"next\">&lt;&lt;</a>\n");
                                else
                                    sb.append("      <a href=\"index" + (page - 1)
                                            + ".html\" class=\"next\">&lt;&lt;</a>\n");

                            } else {
                                if (page == 2)
                                    sb.append(
                                            "      <div class=\"next\"><a href=\"index.html\" class=\"next\">&lt;&lt;</a>&nbsp;|&nbsp;<a href=\"index"
                                                    + (page + 1) + ".html\" class=\"next\">&gt;&gt;</a></div>\n");
                                else
                                    sb.append("      <div class=\"next\"><a href=\"index" + (page - 1)
                                            + ".html\" class=\"next\">&lt;&lt;</a>&nbsp;|&nbsp;<a href=\"index"
                                            + (page + 1) + ".html\" class=\"next\">&gt;&gt;</a></div>\n");
                            }
                        } else {
                            sb.append("       &nbsp;\n");
                        }
                        sb.append("     </td>\n");
                        sb.append("    </tr>\n");
                        sb.append("   </table>\n");
                        sb.append(getFooter());
                        try {

                            String index = "index.html";
                            if (page > 1)
                                index = "index" + page + ".html";
                            FileOutputStream fos = new FileOutputStream(new File(directory, index));
                            fos.write(sb.toString().getBytes());
                            fos.close();
                            if ((i + 1) == max) {
                                try {
                                    m.jOutputDoc.insertString(m.jOutputDoc.getLength(),
                                            m.mes.getString("Generator.40") + ls, m.readyAtr);
                                    m.text.setCaretPosition(m.jOutputDoc.getLength());
                                    m.jOutputDoc.insertString(m.jOutputDoc.getLength(),
                                            ls + images.length + m.mes.getString("Generator.44")
                                                    + o.getOutput_dir_gallerie() + m.mes.getString("Generator.45") + ls,
                                            m.readyAtr);
                                    m.text.setCaretPosition(m.jOutputDoc.getLength());
                                } catch (Exception e) {
                                    System.out.println(m.mes.getString("Generator.40"));
                                }
                                break;
                            }
                        } catch (Exception e) {
                        }

                        if (i < (max - 1)) {
                            sb = new StringBuilder();
                            sb.append(getHeader(o.getGallerieTitle(), true));
                            sb.append("   <table>\n");
                        }

                    } // end of page

                    // print ready info
                    try {
                        m.jOutputDoc.insertString(m.jOutputDoc.getLength(), m.mes.getString("Generator.40") + ls,
                                m.readyAtr);
                        m.text.setCaretPosition(m.jOutputDoc.getLength());
                    } catch (Exception e) {
                        System.out.println(m.mes.getString("Generator.40"));
                    }

                } // end of for loop

                try {
                    m.jOutputDoc
                            .insertString(
                                    m.jOutputDoc.getLength(), ls + images.length + m.mes.getString("Generator.44")
                                            + o.getOutput_dir_gallerie() + m.mes.getString("Generator.45") + ls,
                                    m.readyAtr);
                    m.text.setCaretPosition(m.jOutputDoc.getLength());
                } catch (Exception e) {
                    System.out.println(ls + images.length + m.mes.getString("Generator.46") + ls);
                }

                Element[] els = new Element[elements.size()];
                Producer producer = new Producer(m, elements.toArray(els), "");
                Thread producerThread = new Thread(producer);
                int cpus = Runtime.getRuntime().availableProcessors();
                Thread consumerThreads[] = new Thread[cpus];
                for (int i = 0; i < cpus; i++) {
                    consumerThreads[i] = new Thread(new Consumer(producer, m, false, null));
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

                m.status.setStatusOff();
                m.p_monitor.close();
            }
        };
        t.start();

    }

    private void createPreview(int t, File file, String m_name, String b_name, boolean back, boolean next) {
        StringBuilder sb = new StringBuilder();
        String titel = createTitle(m_name);
        sb.append(getHeader(o.getGallerieTitle() + " - " + titel, false));
        sb.append("    <table>\n");
        sb.append("     <tr>\n");

        sb.append("      <td>\n");

        if (back)
            sb.append("       <h1><a href=\"image" + (t - 1) + ".html\">&lt;</a></h1>\n");
        else
            sb.append("       &nbsp;\n");

        sb.append("      </td>\n");

        sb.append("      <td style=\"width: 460px;\">\n");
        sb.append("       <a href=\"../big/" + b_name + "\" title=\"" + titel + "\"><img src=\"" + m_name + "\" alt=\""
                + titel + "\" /></a>\n");
        sb.append("      </td>\n");

        sb.append("      <td>\n");

        if (next)
            sb.append("       <h1><a href=\"image" + (t + 1) + ".html\">&gt;</a></h1>\n");
        else
            sb.append("       &nbsp;\n");

        sb.append("      </td>\n");

        sb.append("     </tr>\n");
        sb.append("    </table>\n");

        sb.append(getFooter());
        try {
            FileOutputStream fos = new FileOutputStream(new File(file, "image" + t + ".html"));
            fos.write(sb.toString().getBytes());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getHeader(String t, boolean css) {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
        sb.append(
                "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">\n");
        sb.append("<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"LC\">\n");
        sb.append(" <head>\n");
        sb.append("  <title>" + t + "</title>\n");
        sb.append("  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n");
        sb.append("  <meta name=\"Generator\" content=\"" + m.mes.getString("Main.0") + "\" />\n");
        if (css)
            sb.append("  <link rel=\"stylesheet\" type=\"text/css\" href=\"styles.css\" />\n");
        else
            sb.append("  <link rel=\"stylesheet\" type=\"text/css\" href=\"../styles.css\" />\n");
        sb.append(" </head>\n");

        sb.append(" <body>\n");
        sb.append("  <div style=\"text-align: center; margin: 0 auto; \">\n");

        sb.append("   <h1>" + t + "</h1>\n");
        return sb.toString();
    }

    private String getFooter() {
        StringBuilder sb = new StringBuilder();
        sb.append("   <br />\n");
        sb.append("   <br />\n");
        sb.append("   <a href=\"http://jmjrst.sourceforge.net/\" style=\"font-size: 9pt\">Web Gallery created by "
                + m.mes.getString("Main.0") + " " + m.mes.getString("AboutBox.22") + "</a>\n");
        sb.append("   <br />\n");
        sb.append("   <br />\n");
        sb.append("  </div>\n");
        sb.append(" </body>\n");
        sb.append("</html>\n");
        return sb.toString();
    }

    private void createCSS() {
        StringBuilder sb = new StringBuilder();
        sb.append("html, body {\n");
        sb.append("  height: 100%;\n");
        sb.append("  padding: 0;\n");
        sb.append("  margin: 0;\n");
        sb.append("}\n");
        sb.append("body {\n");
        sb.append("  font-family: Arial, Helvetica, sans-serif; \n");
        sb.append("  background-color: rgb(" + o.getBackground_r() + "," + o.getBackground_g() + ","
                + o.getBackground_b() + "); \n");
        sb.append("  font-size: 12pt;  \n");
        sb.append("  color: rgb(" + o.getForeground_r() + "," + o.getForeground_g() + "," + o.getForeground_b()
                + "); \n ");
        sb.append("  text-align:center;  \n");
        sb.append("}\n");
        sb.append("h1 {\n");
        sb.append("  font-size: 23pt; \n");
        sb.append("  text-align:center;  \n");
        sb.append("}\n");
        sb.append("img {\n");
        sb.append("  border: 0px;\n");
        sb.append("}\n");
        sb.append("table {\n");
        sb.append("  margin-left: auto;\n");
        sb.append("  margin-right: auto;\n");
        sb.append("}\n");
        sb.append("a   {   \n");
        sb.append("  font-family: Arial, Helvetica, sans-serif; \n");
        sb.append("  color: rgb(" + o.getForeground_r() + "," + o.getForeground_g() + "," + o.getForeground_b()
                + "); \n");
        sb.append("  text-decoration: none;\n");
        sb.append("}\n");
        sb.append("td {\n");
        sb.append("  width: 165px;\n");
        sb.append("  background-color: rgb(" + o.getBackground_r() + "," + o.getBackground_g() + ","
                + o.getBackground_b() + "); \n ");
        sb.append("  border: solid 2px rgb(" + o.getForeground_r() + "," + o.getForeground_g() + ","
                + o.getForeground_b() + ");\n");
        sb.append("  padding: 0;\n");
        sb.append("  vertical-align: middle;\n");
        sb.append("  text-align: center;\n");
        sb.append("}\n");
        sb.append(".next {\n");
        sb.append("  text-align: center;\n");
        sb.append("  font-size: 23pt; \n");
        sb.append("}\n");
        try {
            FileOutputStream fos = new FileOutputStream(new File(directory, "styles.css"));
            fos.write(sb.toString().getBytes());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String createTitle(String filename) {
        filename = filename.substring(0, filename.lastIndexOf("."));
        filename = filename.replace('_', ' ');
        return filename;
    }
}

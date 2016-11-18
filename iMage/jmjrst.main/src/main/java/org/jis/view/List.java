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

import java.awt.Color;
import java.awt.Component;
import java.awt.SystemColor;
import java.io.File;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ScrollPaneLayout;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileSystemView;

/**
 * @author <a href="http://www.jgeppert.com">Johannes Geppert</a>
 * 
 * <p>
 * list all jpegs of the selected directory
 * </p>
 */
public class List extends JScrollPane {
    private static final long serialVersionUID = 1949499539202488650L;
    private Vector<File>      v_file           = new Vector<File>();
    private Vector<String>    v_names          = new Vector<String>();
    private JList             entrys           = new JList();
    private Preview           t                = null;
    private Object            selected_values[];

    /**
     * @param prev a reference to the preview panel
     */
    public List(Preview prev) {
        this.t = prev;

        setAutoscrolls(true);
        setLayout(new ScrollPaneLayout());
        entrys.setCellRenderer(new SpecialCellRenderer());
        entrys.setListData(v_file);
        add(entrys);
        setViewportView(entrys);

        entrys.addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent arg0) {
                JList s = (JList) arg0.getSource();
                selected_values = s.getSelectedValues();

                if (selected_values.length == 1) {
                    Thread x = new Thread(new Runnable() {

                        public void run() {
                            try {
                                t.setThumb(new Thumbnail((File) selected_values[0], 220, 165));
                            } catch (Exception ex) {
                                System.out.println("Fehler beim erstellen der Vorschau!");
                                ex.printStackTrace();
                            }
                        }

                    });

                    x.start();
                }
            }
        });
    }

    public File getPicture() {
        return (File) entrys.getSelectedValue();
    }

    public File[] getPictures() {
        File[] e = new File[0];
        try {
            e = new File[v_file.size()];
            for (int k = 0; k < e.length; k++)
                e[k] = v_file.elementAt(k);
        } catch (Exception ex) {
            System.out.println(ex);
            System.out.println(ex.getMessage());
        }
        return e;

    }

    public void removePictures() {
        entrys.removeAll();
        v_file.removeAllElements();
        v_names.removeAllElements();

        entrys.getSelectedValues();
    }

    public Vector<File> getSelectedValues() {
        Vector<File> files = new Vector<File>();
        Object e[] = entrys.getSelectedValues();

        for (int i = 0; i < e.length; i++)
            files.addElement((File) e[i]);

        return files;
    }

    public void setPictures(File[] e) {
        entrys.removeAll();
        v_file.removeAllElements();
        v_names.removeAllElements();
        for (int i = 0; i < e.length; i++) {
            File entry = e[i];
            v_file.add(entry);
            v_names.add(entry.getName());
        }
        entrys.setListData(v_file);
    }

    /**
     * @author <a href="http://www.jgeppert.com">Johannes Geppert</a>
     * 
     * <p>
     * render the list elements
     * </p>
     */
    class SpecialCellRenderer extends JLabel implements ListCellRenderer {

        private static final long serialVersionUID = 7047703534126835655L;
        private FileSystemView    fsv              = FileSystemView.getFileSystemView();

        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
                boolean hasFocus) {
            if (isSelected) {
                setBackground(SystemColor.textInactiveText);
                setForeground(SystemColor.textHighlight);
            } else {
                setBackground(Color.WHITE);
                setForeground(SystemColor.textInactiveText);
            }
            setIcon(fsv.getSystemIcon((File) value));
            setText(((File) value).getName());
            return this;
        }
    }
}

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
package org.jis.view;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;

import org.jis.Main;

/**
 * @author <a href="http://www.jgeppert.com">Johannes Geppert</a>
 * 
 * <p>
 * The FileTree to select a directory
 * </p>
 */
public class FileTree extends JTree {
    private static final long serialVersionUID = 4317616202609184678L;

    private List              l;
    private Main              m;
    private Map<String, Icon> icons            = new HashMap<String, Icon>();

    /*
     * <p> Render the Items of the FileTree OS specific </p>
     */
    private class FileTreeRenderer extends DefaultTreeCellRenderer {
        private static final long serialVersionUID = 7646702219465760758L;

        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded,
                boolean leaf, int row, boolean hasFocus) {
            super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
            Object user = ((DefaultMutableTreeNode) value).getUserObject();
            if (user instanceof File) {
                FileSystemView fsv = FileSystemView.getFileSystemView();
                File f = (File) user;
                String name = f.getName();
                String ext = name.substring(name.lastIndexOf('.') + 1);
                if (icons.containsKey(ext))
                    setIcon(icons.get(ext));
                else
                    setIcon(fsv.getSystemIcon(f));
                setText(fsv.getSystemDisplayName(f));
            }
            return this;
        }
    }

    // protected FileFilter filter;
    // protected DefaultTreeModel model;

    /**
     * @param m a reference to the Main Class
     * @param l a referenc to the List where the files of a directory are
     * displayed
     */
    public FileTree(Main m, List l) {
        DefaultTreeModel model;
        this.l = l;
        this.m = m;
        setMinimumSize(new Dimension(210, 250));
        setRootVisible(false);
        File homeDir = FileSystemView.getFileSystemView().getHomeDirectory();

        DefaultMutableTreeNode root = new DefaultMutableTreeNode();
        DefaultMutableTreeNode home = new DefaultMutableTreeNode(homeDir);

        // windows: begin with home directory
        if (System.getProperty("os.name").substring(0, 3).equalsIgnoreCase("win")) {
            root = new DefaultMutableTreeNode(homeDir);
            root.add(home);
        }
        // others: begin with home root directory
        else {
            File[] roots = File.listRoots();
            root.add(home);
            for (int i = 0; i < roots.length; i++) {
                root.add(new DefaultMutableTreeNode(roots[i]));
            }
            // root = new
            // DefaultMutableTreeNode(FileSystemView.getFileSystemView(). new
            // File("/"));
            // root.add(home);
        }
        model = new DefaultTreeModel(root);
        setShowsRootHandles(true);
        putClientProperty("JTree.lineStyle", "Angled");
        setBorder(BorderFactory.createEtchedBorder());
        setCellRenderer(new FileTreeRenderer());
        setModel(model);
        expandPath(home);

        addTreeSelectionListener(new TreeSelectionListener() {

            public void valueChanged(TreeSelectionEvent arg0) {
                expandPath((DefaultMutableTreeNode) (arg0.getPath().getLastPathComponent()));
            }

        });

        addTreeWillExpandListener(new TreeWillExpandListener() {
            public void treeWillCollapse(TreeExpansionEvent e) {
                ((DefaultMutableTreeNode) (e.getPath().getLastPathComponent())).removeAllChildren();
                ((DefaultMutableTreeNode) (e.getPath().getLastPathComponent())).add(new DefaultMutableTreeNode(null));
            }

            public void treeWillExpand(TreeExpansionEvent e) {
                expandPath((DefaultMutableTreeNode) (e.getPath().getLastPathComponent()));
            }
        });
    }

    /**
     * <p>
     * expand the Path
     * </p>
     * 
     * @param d
     */
    private void expandPath(final DefaultMutableTreeNode d) {
        d.removeAllChildren();
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        File[] tempf;

        tempf = ((File) d.getUserObject()).listFiles();
        Vector<File> elem = new Vector<File>();
        Vector<File> dirs = new Vector<File>();
        Vector<File> files = new Vector<File>();
        for (int i = 0; i < tempf.length; i++)
            if (tempf[i].isDirectory())
                dirs.add(tempf[i]);
            else {
                String fileName = tempf[i].getName().toLowerCase();
                if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg"))
                    files.add(tempf[i]);
            }

        File[] sortedDirs = dirs.toArray(new File[0]);

        Arrays.sort(sortedDirs);

        File[] sortedFiles = files.toArray(new File[0]);
        Arrays.sort(sortedFiles);

        elem.addAll(Arrays.asList(sortedDirs));
        // elem.addAll(Arrays.asList(sortedFiles));
        if (sortedFiles.length > 0) {
            m.menu.gallerie.setEnabled(true);
            m.menu.zippen.setEnabled(true);
            m.menu.gener.setEnabled(true);
            m.toolBar.gallerie.setEnabled(true);
            m.toolBar.zippen.setEnabled(true);
            m.toolBar.gener.setEnabled(true);
        } else {
            m.menu.gallerie.setEnabled(false);
            m.menu.zippen.setEnabled(false);
            m.menu.gener.setEnabled(false);
            m.toolBar.gallerie.setEnabled(false);
            m.toolBar.zippen.setEnabled(false);
            m.toolBar.gener.setEnabled(false);
        }
        l.setPictures(sortedFiles);

        DefaultMutableTreeNode tempd = null;
        for (int i = 0; i < elem.size(); i++) {
            // if (getFileFilter() == null ||
            // getFileFilter().accept(elem.get(i)))
            // {
            tempd = new DefaultMutableTreeNode(elem.get(i));
            if (elem.get(i).isDirectory())
                tempd.add(new DefaultMutableTreeNode(null));
            d.add(tempd);
            // }
        }
        ((DefaultTreeModel) getModel()).reload(d);
        setCursor(Cursor.getDefaultCursor());
    }

    // public void setFileFilter(FileFilter f)
    // {
    // filter = f;
    // expandPath((DefaultMutableTreeNode) model.getRoot());
    // }
    //
    // public FileFilter getFileFilter()
    // {
    // return filter;
    // }
    //
    public void setFileIcon(String ext, Icon icon) {
        icons.put(ext, icon);
    }

    public void removeFileIcon(String ext) {
        icons.remove(ext);
    }

    public void updateUI() {
        super.updateUI();
        setCellRenderer(new FileTreeRenderer());
    }

    public Dimension getPreferredSize() {
        Dimension size = super.getPreferredSize();
        size.width = Math.max(200, size.width);
        size.height = Math.max(400, size.height);
        return size;
    }
}

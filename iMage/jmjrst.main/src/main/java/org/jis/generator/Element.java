package org.jis.generator;

import java.io.File;

public class Element {
    public final File file;
    public final int  index;
    public final int  width;
    public final int  height;
    public final File outDir;

    public Element(int index, File file, int width, int height, File outDir) {
        super();
        this.file = file;
        this.index = index;
        this.width = width;
        this.height = height;
        this.outDir = outDir;
    }
}

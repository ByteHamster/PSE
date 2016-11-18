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

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.jis.Main;
import org.jis.options.Options;

/**
 * @author <a href="http://www.jgeppert.com">Johannes Geppert</a>
 * 
 * <p>
 * The class representing the Producer side
 * </p>
 */
public class Producer implements Runnable {

    protected BlockingQueue<Element> queue;
    public final String              praefix;
    private Element[]                elements;
    private boolean                  isDone = false;
    private Main                     m;

    Producer(Main m, Element[] elements, String praefix) {
        this.queue = new LinkedBlockingQueue<Element>(Runtime.getRuntime().availableProcessors() + 1);
        this.elements = elements;
        this.m = m;
        this.praefix = praefix;

        // print info
        try {
            m.jOutputDoc.remove(0, m.jOutputDoc.getLength());
            m.jOutputDoc.insertString(m.jOutputDoc.getLength(),
                    elements.length + m.mes.getString("Generator.28") + elements[0].file.getParent()
                            + m.mes.getString("Generator.29") + Options.getInstance().getQuality()
                            + m.mes.getString("Generator.30") + Options.ls + Options.ls,
                    m.outputAtr);
            m.text.setCaretPosition(m.jOutputDoc.getLength());
        } catch (Exception e) {
            System.out.println(elements.length + m.mes.getString("Generator.31") + elements[0].file.getPath().toString()
                    + m.mes.getString("Generator.32") + Options.getInstance().getQuality()
                    + m.mes.getString("Generator.33") + Options.ls + Options.ls);
        }

    }

    public void run() {
        try {
            for (int i = 0; i < elements.length; i++) {
                if (m.p_monitor.isCanceled())
                    break;

                Element elem = elements[i];
                queue.put(elem);
            }
        } catch (InterruptedException ex) {
            System.err.println("Producer INTERRUPTED");
        }
        isDone = true;
    }

    int index = 0;

    public synchronized void incrementIndex() {
        m.p_monitor.setProgress(++index);
    }

    public boolean isDone() {
        return isDone;
    }
}

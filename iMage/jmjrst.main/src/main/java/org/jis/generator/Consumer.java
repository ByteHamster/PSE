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
import java.io.IOException;
import java.util.Vector;

import org.jis.Main;
import org.jis.options.Options;

/**
 * @author <a href="http://www.jgeppert.com">Johannes Geppert</a>
 * 
 * <p>
 * The class representing the Consumer side
 * </p>
 */
public class Consumer implements Runnable {
    protected Producer   producer;
    private Main         m;
    private boolean      zippen;
    private Vector<File> zipIt;

    public Consumer(Producer producer, Main m, boolean zippen, Vector<File> zipIt) {
        super();
        this.producer = producer;
        this.m = m;
        this.zippen = zippen;
        this.zipIt = zipIt;
    }

    public void run() {
        try {
            while (producer.isDone() == false || producer.queue.size() > 0) {

                if (m.p_monitor.isCanceled())
                    break;

                Element obj = producer.queue.take();
                try {
                    process(obj);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (InterruptedException ex) {
            System.out.println("CONSUMER INTERRUPTED");
        }
    }

    void process(Element obj) throws IOException {
        m.p_monitor.setNote("Aktuelles Bild: " + obj.file.getName());
        try {
            m.jOutputDoc.insertString(m.jOutputDoc.getLength(), m.mes.getString("Generator.10"), m.outputAtr);
            m.jOutputDoc.insertString(m.jOutputDoc.getLength(), obj.file.getName(), m.fileAtr);
            m.jOutputDoc.insertString(m.jOutputDoc.getLength(), "\t . . . ", m.outputAtr);
            m.text.setCaretPosition(m.jOutputDoc.getLength());
        } catch (Exception e) {
            System.out.print(m.mes.getString("Generator.10") + obj.file.toString() + "\t . . . ");
        }

        boolean error = false;

        File out = new File(obj.outDir, producer.praefix + obj.file.getName());
        out = m.generator.generateImage(obj.file, obj.outDir, true, obj.width, obj.height, producer.praefix);

        // add file to the Files for ZIP
        if (zippen)
            zipIt.addElement(out);

        try {
            m.jOutputDoc.insertString(m.jOutputDoc.getLength(), ". . .  ", m.outputAtr);
            m.text.setCaretPosition(m.jOutputDoc.getLength());
        } catch (Exception e) {
            System.out.print(". . .  ");
        }

        if (error == false)
            try {
                m.jOutputDoc.insertString(m.jOutputDoc.getLength(), m.mes.getString("Generator.40") + Options.ls,
                        m.readyAtr);
                m.text.setCaretPosition(m.jOutputDoc.getLength());
            } catch (Exception e) {
                System.out.println(m.mes.getString("Generator.40"));
            }
        else
            try {
                m.jOutputDoc.insertString(m.jOutputDoc.getLength(), m.mes.getString("Generator.42") + Options.ls,
                        m.errorAtr);
                m.text.setCaretPosition(m.jOutputDoc.getLength());
            } catch (Exception e) {
                System.out.println(m.mes.getString("Generator.42"));
            }

        producer.incrementIndex();
    }
}

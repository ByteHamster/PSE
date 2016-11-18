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
package org.jis.listner;

/*
 * ExitListner Copyright (c) 2004 Johannes Geppert. All rights reserved.
 */
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class CloseListner extends WindowAdapter {
    public void windowClosing(WindowEvent event) {
        event.getWindow().setEnabled(false);
        // System.exit(999);
    }
}

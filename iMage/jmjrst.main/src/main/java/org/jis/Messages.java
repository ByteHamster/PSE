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
package org.jis;

import java.io.Serializable;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * @author <a href="http://www.jgeppert.com">Johannes Geppert</a>
 * 
 * <p>
 * This class controls the messages of the available languages
 * </p>
 */
public class Messages implements Serializable {
    private static final long serialVersionUID = -3129495340668876130L;

    private final String      BUNDLE_NAME      = "org.jis.messages";

    private ResourceBundle    RESOURCE_BUNDLE;

    /**
     * @param l the locale to use the correct resource bundle
     */
    public Messages(Locale l) {
        RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME, l);
    }

    /**
     * @param key the message key
     * @return returns the message of the specified language
     */
    public String getString(String key) {
        try {
            return RESOURCE_BUNDLE.getString(key);
        } catch (MissingResourceException e) {
            e.printStackTrace();
            return '!' + key + '!';
        }
    }
}

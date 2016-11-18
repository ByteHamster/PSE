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
package org.jis.view.dialog;

import java.io.File;

/**
 * @author <a href="http://www.jgeppert.com">Johannes Geppert</a>
 * 
 * <p>
 * this class a Layouts for an Webgallerie
 * </p>
 */
public class Layout {
    private String  name;
    private String  type;
    private boolean mediumCreate;
    private boolean bigCreate;
    private boolean subTitle;
    private int     smallWidth;
    private int     smallHeight;
    private int     mediumWidth;
    private int     mediumHeight;
    private int     bigWidth;
    private int     bigHeight;
    private int     max_pictures_on_site;
    private String  footer;
    private String  listType;
    private String  onclickSmall;
    private String  onclickMedium;
    private File    file;
    private String  description;
    private String  prefix;
    private String  previewNext;
    private String  previewBack;
    private String  previewHome;
    private String  aProperty;
    private File    previewImage;

    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

    public int getMax_pictures_on_site() {
        return max_pictures_on_site;
    }

    public void setMax_pictures_on_site(int max_pictures_on_site) {
        this.max_pictures_on_site = max_pictures_on_site;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return getName();
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public boolean isBigCreate() {
        return bigCreate;
    }

    public void setBigCreate(boolean bigCreate) {
        this.bigCreate = bigCreate;
    }

    public int getBigHeight() {
        return bigHeight;
    }

    public void setBigHeight(int bigHeight) {
        this.bigHeight = bigHeight;
    }

    public int getBigWidth() {
        return bigWidth;
    }

    public void setBigWidth(int bigWidth) {
        this.bigWidth = bigWidth;
    }

    public boolean isMediumCreate() {
        return mediumCreate;
    }

    public void setMediumCreate(boolean mediumCreate) {
        this.mediumCreate = mediumCreate;
    }

    public int getMediumHeight() {
        return mediumHeight;
    }

    public void setMediumHeight(int mediumHeight) {
        this.mediumHeight = mediumHeight;
    }

    public int getMediumWidth() {
        return mediumWidth;
    }

    public void setMediumWidth(int mediumWidth) {
        this.mediumWidth = mediumWidth;
    }

    public String getOnclickMedium() {
        return onclickMedium;
    }

    public void setOnclickMedium(String onclickMedium) {
        this.onclickMedium = onclickMedium;
    }

    public String getOnclickSmall() {
        return onclickSmall;
    }

    public void setOnclickSmall(String onclickSmall) {
        this.onclickSmall = onclickSmall;
    }

    public int getSmallHeight() {
        return smallHeight;
    }

    public void setSmallHeight(int smallHeight) {
        this.smallHeight = smallHeight;
    }

    public int getSmallWidth() {
        return smallWidth;
    }

    public void setSmallWidth(int smallWidth) {
        this.smallWidth = smallWidth;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public boolean isSubTitle() {
        return subTitle;
    }

    public void setSubTitle(boolean subTitle) {
        this.subTitle = subTitle;
    }

    public String getPreviewBack() {
        return previewBack;
    }

    public void setPreviewBack(String previewBack) {
        this.previewBack = previewBack;
    }

    public String getPreviewHome() {
        return previewHome;
    }

    public void setPreviewHome(String previewHome) {
        this.previewHome = previewHome;
    }

    public String getPreviewNext() {
        return previewNext;
    }

    public void setPreviewNext(String previewNext) {
        this.previewNext = previewNext;
    }

    public String getAProperty() {
        return aProperty;
    }

    public void setAProperty(String property) {
        aProperty = property;
    }

    public File getPreviewImage() {
        return previewImage;
    }

    public void setPreviewImage(File previewImage) {
        this.previewImage = previewImage;
    }

    public String getListType() {
        return listType;
    }

    public void setListType(String listType) {
        this.listType = listType;
    }
}

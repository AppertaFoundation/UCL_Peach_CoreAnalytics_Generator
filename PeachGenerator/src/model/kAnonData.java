package model;

import java.io.File;

public class kAnonData {

    private String[] data;
    private String attributeTitle;
    private String attributeType;
    private File anonHierarchy;

    public kAnonData(String attributeTitle, String attributeType, File anonHierarchy) {
        this.attributeType = attributeType;
        this.attributeTitle = attributeTitle;
        this.anonHierarchy = anonHierarchy;
    }

    public String getAttributeType() {
        return attributeType;
    }

    public void setAttributeType(String attributeType) {
        this.attributeType = attributeType;
    }

    public String getattributeTitle() {
        return attributeTitle;
    }

    public void setattributeTitle(String attributeTitle) {
        this.attributeTitle = attributeTitle;
    }

    public File getAnonHierarchy() {
        return anonHierarchy;
    }

    public void setAnonHierarchy(File anonHierarchy) {

        this.anonHierarchy = anonHierarchy;
    }
}

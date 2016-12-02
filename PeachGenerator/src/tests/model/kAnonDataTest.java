package model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class kAnonDataTest {

    protected kAnonData kAnonData;
    protected File hierarchyFile;

    @Before
    public void initialiseMockObject(){

        hierarchyFile = new File("__hierarchy__age.csv");

        /* Create mock object */
        kAnonData = new kAnonData(
                "Age",
                "Categorical",
                hierarchyFile
        );
    }

    @Test
    public void testGetAttributeType() throws Exception {
        Assert.assertTrue(kAnonData.getAttributeType().equals("Categorical"));
    }

    @Test
    public void testSetAttributeType() throws Exception {
        kAnonData.setAttributeType("newType");
        Assert.assertTrue(kAnonData.getAttributeType().equals("newType"));
    }

    @Test
    public void testGetattributeTitle() throws Exception {
        Assert.assertTrue(kAnonData.getattributeTitle().equals("Age"));
    }

    @Test
    public void testSetattributeTitle() throws Exception {
        kAnonData.setattributeTitle("newAtt");
        Assert.assertTrue(kAnonData.getattributeTitle().equals("newAtt"));
    }

    @Test
    public void testGetAnonHierarchy() throws Exception {
        Assert.assertTrue(kAnonData.getAnonHierarchy() == hierarchyFile);
    }

    @Test
    public void testSetAnonHierarchy() throws Exception {
        File newFile = new File("testFile.csv");
        kAnonData.setAnonHierarchy(newFile);
        Assert.assertTrue(kAnonData.getAnonHierarchy() == newFile);
    }
}
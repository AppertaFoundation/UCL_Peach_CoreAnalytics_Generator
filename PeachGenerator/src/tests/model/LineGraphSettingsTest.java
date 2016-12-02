package model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LineGraphSettingsTest {

    LineGraphSettings lineGraphSettings;
    int[] dataset1 = {1,2,3};
    int[] dataset2 = {4,5,6};

    @Before
    public void initialiseMockObject(){
        lineGraphSettings = new LineGraphSettings(
                dataset1,
                dataset2,
                "testXAxisLabel",
                "testGraphTitle",
                "testDataTitle1",
                "testDataTitle2"
        );
    }

    @Test
    public void testGetDataSet1() throws Exception {
        int[] datasetcompare = {1,2,3};
        Assert.assertArrayEquals(datasetcompare,lineGraphSettings.getDataSet1());
    }

    @Test
    public void testSetDataSet1() throws Exception {
        int[] newArr = {7,7,7};
        lineGraphSettings.setDataSet1(newArr);
        Assert.assertArrayEquals(newArr,lineGraphSettings.getDataSet1());
    }

    @Test
    public void testGetDataSet2() throws Exception {
        int[] dataset2Compare = {4,5,6};
        Assert.assertArrayEquals(dataset2Compare,lineGraphSettings.getDataSet2());
    }

    @Test
    public void testSetDataSet2() throws Exception {
        int[] newDataSet2 = {9,9,9};
        lineGraphSettings.setDataSet2(newDataSet2);
        Assert.assertArrayEquals(newDataSet2,lineGraphSettings.getDataSet2());
    }

    @Test
    public void testGetxAxisLabel() throws Exception {
        Assert.assertTrue(lineGraphSettings.getxAxisLabel().equals("testXAxisLabel"));
    }

    @Test
    public void testSetxAxisLabel() throws Exception {
        lineGraphSettings.setxAxisLabel("newXAxisLabel");
        Assert.assertTrue(lineGraphSettings.getxAxisLabel().equals("newXAxisLabel"));
    }

    @Test
    public void testGetGraphTitle() throws Exception {
        Assert.assertTrue(lineGraphSettings.getGraphTitle().equals("testGraphTitle"));
    }

    @Test
    public void testSetGraphTitle() throws Exception {
        lineGraphSettings.setGraphTitle("newGraphTitle");
        Assert.assertTrue(lineGraphSettings.getGraphTitle().equals("newGraphTitle"));
    }

    @Test
    public void testGetData1Title() throws Exception {
        Assert.assertTrue(lineGraphSettings.getData1Title().equals("testDataTitle1"));
    }

    @Test
    public void testSetData1Title() throws Exception {
        lineGraphSettings.setData1Title("newData1Title");
        Assert.assertTrue(lineGraphSettings.getData1Title().equals("newData1Title"));
    }

    @Test
    public void testGetData2Title() throws Exception {
        Assert.assertTrue(lineGraphSettings.getData2Title().equals("testDataTitle2"));
    }

    @Test
    public void testSetData2Title() throws Exception {
        lineGraphSettings.setData2Title("newDataTitle2");
        Assert.assertTrue(lineGraphSettings.getData2Title().equals("newDataTitle2"));
    }
}
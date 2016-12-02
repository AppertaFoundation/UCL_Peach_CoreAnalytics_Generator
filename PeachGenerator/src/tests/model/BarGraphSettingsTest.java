package model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BarGraphSettingsTest {

    protected BarGraphSettings barGraphSettings;

    /* create mock data sets */
    protected int[] dataset1 = {1,2,3,4};
    protected int[] dataset2 = {5,6,7,8};
    String[] xAxisTitles = {"t1","t2","t3","t4"};

    @Before
    public void intialiseMockObject(){

        barGraphSettings = new BarGraphSettings(
                dataset1,
                dataset2,
                xAxisTitles,
                "TestTitle",
                "TestDataTitle1",
                "TestDataTitle2",
                "TestxAxisTitle",
                "TestyAxisTitle"
        );
    }

    @Test
    public void testGetDataset1() throws Exception {
        Assert.assertTrue(barGraphSettings.getDataset1() == dataset1 );
    }

    @Test
    public void testSetDataset1() throws Exception {
        int[] newDataSet1 = {7,7,7,7};
        barGraphSettings.setDataset1(newDataSet1);
        Assert.assertTrue(barGraphSettings.getDataset1() == newDataSet1);
    }

    @Test
    public void testGetDataset2() throws Exception {
        Assert.assertTrue(barGraphSettings.getDataset2() == dataset2);
    }

    @Test
    public void testSetDataset2() throws Exception {
        int[] newDataSet2 = {8,8,8,8};
        barGraphSettings.setDataset2(newDataSet2);
        Assert.assertTrue(barGraphSettings.getDataset2() == newDataSet2);
    }

    @Test
    public void testGetxAxisLabel() throws Exception {
        Assert.assertTrue(barGraphSettings.getxAxisLabel().equals(xAxisTitles));
    }

    @Test
    public void testSetxAxisLabel() throws Exception {
        String[] newAxisLabels = {"n1","n2","n3","n4"};
        barGraphSettings.setxAxisLabel(newAxisLabels);
        Assert.assertTrue(newAxisLabels.equals(newAxisLabels));
    }

    @Test
    public void testGetGraphTitle() throws Exception {
        Assert.assertTrue(barGraphSettings.getGraphTitle().equals("TestTitle"));
    }

    @Test
    public void testSetGraphTitle() throws Exception {
        barGraphSettings.setGraphTitle("newTitle");
        Assert.assertTrue(barGraphSettings.getGraphTitle().equals("newTitle"));
    }

    @Test
    public void testGetData1Title() throws Exception {
        Assert.assertTrue(barGraphSettings.getData1Title().equals("TestDataTitle1"));
    }

    @Test
    public void testSetData1Title() throws Exception {
        barGraphSettings.setData1Title("newDataLabel1");
        Assert.assertTrue(barGraphSettings.getData1Title().equals("newDataLabel1"));
    }

    @Test
    public void testGetData2Title() throws Exception {
        Assert.assertTrue(barGraphSettings.getData2Title().equals("TestDataTitle2"));
    }

    @Test
    public void testSetData2Title() throws Exception {
        barGraphSettings.setData2Title("newData2Title");
        Assert.assertTrue(barGraphSettings.getData2Title().equals("newData2Title"));
    }

    @Test
    public void testGetxAxisTitle() throws Exception {
        Assert.assertTrue(barGraphSettings.getxAxisTitle().equals("TestxAxisTitle"));
    }

    @Test
    public void testSetxAxisTitle() throws Exception {
        barGraphSettings.setxAxisTitle("newTitle");
        Assert.assertTrue(barGraphSettings.getxAxisTitle().equals("newTitle"));
    }

    @Test
    public void testGetyAxisTitle() throws Exception {
        Assert.assertTrue(barGraphSettings.getyAxisTitle().equals("TestyAxisTitle"));
    }

    @Test
    public void testSetyAxisTitle() throws Exception {
        barGraphSettings.setyAxisTitle("newYTitle");
        Assert.assertTrue(barGraphSettings.getyAxisTitle().equals("newYTitle"));
    }
}
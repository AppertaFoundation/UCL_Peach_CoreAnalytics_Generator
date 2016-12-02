package utility;

import javafx.embed.swing.JFXPanel;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import model.BarGraphSettings;
import model.LineGraphSettings;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class DataPlotManagerTest {

    @BeforeClass
    public static void initEnvironment(){
        /* init JavaFX environment */
        JFXPanel jfxPanel = new JFXPanel();
    }

    /**
     * Test Data is being plotted correctly
     */
    @Test
    public void testPlotLineGraph(){

        /* Get mock line graph settings */
        LineGraphSettings lineGraphSettings = getTestLineGraphSettings();

        /* Generate mock object */
        LineChart testLineGraph = DataPlotManager.plotLineGraph(lineGraphSettings);

        /* Get data for series 1 */
        XYChart.Series data1 = (XYChart.Series) testLineGraph.getData().get(0);
        XYChart.Data data1val1 = (XYChart.Data) data1.getData().get(1);

        /* Check value value at x:1 == y:2 */
        Assert.assertTrue(data1val1.getXValue().equals(1));
        Assert.assertTrue(data1val1.getYValue().equals(2));

        /* Get data for series 2 */
        XYChart.Series data2 = (XYChart.Series) testLineGraph.getData().get(1);
        XYChart.Data data2val2 = (XYChart.Data) data2.getData().get(4);

        /* Check value value at x:4 == y:10 */
        Assert.assertTrue(data2val2.getXValue().equals(4));
        Assert.assertTrue(data2val2.getYValue().equals(10));
    }

    /**
     * Test invalid input for datasets
     */
    @Test
    public void testNullDataPlotLineGraph(){

        /* Get mock line graph settings */
        LineGraphSettings lineGraphSettings = getTestLineGraphSettings();

        lineGraphSettings.setDataSet1(null);

        /* Generate mock object */
        LineChart testLineGraph = DataPlotManager.plotLineGraph(lineGraphSettings);

        /* null expected */
        Assert.assertTrue(testLineGraph == null);

        int[] vals = {1,2,3,4,5};

        /* reset data set 1  */
        lineGraphSettings.setDataSet1(vals);

        /* pass dataset2 == null */
        lineGraphSettings.setDataSet2(null);

        testLineGraph = DataPlotManager.plotLineGraph(lineGraphSettings);

        Assert.assertTrue(testLineGraph == null);

    }


    /**
     * Test null title entry
     */
    @Test
    public void testNullGraphTitleLineGraph(){

        /* Get mock line graph settings */
        LineGraphSettings lineGraphSettings = getTestLineGraphSettings();

        lineGraphSettings.setGraphTitle(null);

        /* Generate mock object */
        LineChart testLineGraph = DataPlotManager.plotLineGraph(lineGraphSettings);

        /* Line graph with empty title generated */
        Assert.assertTrue(testLineGraph.getTitle().equals(""));

    }

    /**
     * test invalid axis labels
     */
    @Test
    public void testNullAxisLabel(){
        /* Get mock line graph settings */
        LineGraphSettings lineGraphSettings = getTestLineGraphSettings();

        lineGraphSettings.setxAxisLabel(null);

        /* Generate mock object */
        LineChart testLineGraph = DataPlotManager.plotLineGraph(lineGraphSettings);

        /* Line graph with empty title generated */
        Assert.assertTrue(testLineGraph.getXAxis().getLabel().equals(""));

    }

    /**
     * Test invalid data titles
     */
    @Test
    public void testNullAxisTitles(){

        /* Get mock line graph settings */
        LineGraphSettings lineGraphSettings = getTestLineGraphSettings();

        /* Set Dataset labels to null */
        lineGraphSettings.setData1Title(null);
        lineGraphSettings.setData2Title(null);

        /* Generate mock object */
        LineChart testLineGraph = DataPlotManager.plotLineGraph(lineGraphSettings);

        /* Get data for series 1 */
        XYChart.Series data1 = (XYChart.Series) testLineGraph.getData().get(0);
        XYChart.Series data2 = (XYChart.Series) testLineGraph.getData().get(1);

        Assert.assertTrue(data1.getName().equals("Dataset 1"));
        Assert.assertTrue(data2.getName().equals("Dataset 2"));

    }

    /**
     * Check correct creation of bar chart objects
     */
    @Test
    public void testInputsBarGraph(){

        BarGraphSettings barGraphSettings = getBarGraphSettings();

        /* Create a new bar chart */
        BarChart barChart = DataPlotManager.plotBarChart(barGraphSettings);

        XYChart.Series dataSeries1 = (XYChart.Series) barChart.getData().get(0);
        XYChart.Series dataSeries2 = (XYChart.Series) barChart.getData().get(1);

        XYChart.Data dataPoint1 = (XYChart.Data) dataSeries1.getData().get(0);
        XYChart.Data dataPoint2 = (XYChart.Data) dataSeries2.getData().get(1);

        /* Check first value of first data set i.e. first element */
        Assert.assertTrue(dataPoint1.getXValue().equals("prop1"));
        Assert.assertTrue((int)dataPoint1.getYValue() == 1);

        /* Check second value of second data set i.e last element */
        Assert.assertTrue(dataPoint2.getXValue().equals("prop2"));
        Assert.assertTrue((int)dataPoint2.getYValue() == 4);

    }

    /**
     * Check null data inputs
     */
    @Test
    public void testNullInputsBarGraph(){

        BarGraphSettings barGraphSettings = getBarGraphSettings();

        /* Pass dataset 1 as null */
        barGraphSettings.setDataset1(null);

        /* Create a new bar chart */
        BarChart barChart = DataPlotManager.plotBarChart(barGraphSettings);

        Assert.assertTrue(barChart == null);

        BarGraphSettings barGraphSettings2 = getBarGraphSettings();

        /* Pass dataset 2 as null */
        barGraphSettings2.setDataset2(null);

        BarChart barChart2 = DataPlotManager.plotBarChart(barGraphSettings2);

        Assert.assertTrue(barChart2 == null);

    }

    /**
     * Check no axis labels case
     */
    @Test
    public void testNullAxisLabelsPlotBarGraph(){

        BarGraphSettings barGraphSettings = getBarGraphSettings();

        barGraphSettings.setxAxisLabel(null);

        /* Create a new bar chart */
        BarChart barChart = DataPlotManager.plotBarChart(barGraphSettings);

        Assert.assertTrue(barChart == null);

    }

    /**
     * Test case where no title provided
     */
    @Test
    public void testNullTitlePlotBarGraph(){

        BarGraphSettings barGraphSettings = getBarGraphSettings();

        /* set title as null */
        barGraphSettings.setGraphTitle(null);

        /* Create a new bar chart */
        BarChart barChart = DataPlotManager.plotBarChart(barGraphSettings);

        Assert.assertTrue(barChart.getTitle().equals(""));
    }

    /**
     * Test case where no data labels provided
     */
    @Test
    public void testNullDataLabelsPlotBarGraph(){

        BarGraphSettings barGraphSettings = getBarGraphSettings();

        barGraphSettings.setData1Title(null);
        barGraphSettings.setData2Title(null);

        /* Create a new bar chart */
        BarChart barChart = DataPlotManager.plotBarChart(barGraphSettings);

        XYChart.Series dataSeries1 = (XYChart.Series) barChart.getData().get(0);
        XYChart.Series dataSeries2 = (XYChart.Series) barChart.getData().get(1);

        Assert.assertTrue(dataSeries1.getName().equals("dataset 1"));
        Assert.assertTrue(dataSeries2.getName().equals("dataset 2"));

    }


    public LineGraphSettings getTestLineGraphSettings(){

        int[] data1 = {1,2,3,4,5};
        int[] data2 = {6,7,8,9,10};

        LineGraphSettings lineGraphSettings = new LineGraphSettings(
                data1,
                data2,
                "xAxisLabel",
                "TestGraphTitle",
                "TestVar1",
                "TestVar2"
        );

        return lineGraphSettings;
    }

    public BarGraphSettings getBarGraphSettings(){

        int[] data1 = {1,2};
        int[] data2 = {3,4};

        String[] xAxisLabels = {"prop1","prop2"};

        BarGraphSettings barGraphSettings = new BarGraphSettings(
                data1,data2,xAxisLabels,
                "TestGraph","val1","val2","xTitle","yTitle"
        );

        return barGraphSettings;
    }

}
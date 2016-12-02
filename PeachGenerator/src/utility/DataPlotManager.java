package utility;

import javafx.scene.chart.*;
import model.BarGraphSettings;
import model.LineGraphSettings;

public class DataPlotManager {

    public static LineChart<Number,Number> plotLineGraph(LineGraphSettings lineGraphSettings) {

        /* Check presence of data sets */
        if(lineGraphSettings.getDataSet1() == null ||
                lineGraphSettings.getDataSet2() == null){
            System.out.println("Line chart: data null ");
            return null;
        }

        /* Check graph title */
        if(lineGraphSettings.getGraphTitle() == null){
            /* set title to empty if null provided */
            lineGraphSettings.setGraphTitle("");
        }

        /* Check xAxisLabel */
        if(lineGraphSettings.getxAxisLabel() == null){
            /* set xAxisLabel to empty if null */
            lineGraphSettings.setxAxisLabel("");
        }

        /* Check dataset 1 labels*/
        if(lineGraphSettings.getData1Title() == null){lineGraphSettings.setData1Title("Dataset 1");}
        if(lineGraphSettings.getData2Title() == null){lineGraphSettings.setData2Title("Dataset 2");}

        //defining the axes
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();

        xAxis.setLabel(lineGraphSettings.getxAxisLabel());

        //creating the chart
        final LineChart<Number, Number> lineChart =
                new LineChart<Number, Number>(xAxis, yAxis);

        lineChart.setTitle(lineGraphSettings.getGraphTitle());

        //defining a series
        XYChart.Series series1 = new XYChart.Series();
        series1.setName(lineGraphSettings.getData1Title());

        //populating the series with data
        XYChart.Series series2 = new XYChart.Series();
        series2.setName(lineGraphSettings.getData2Title());

        //turn off point markers
        lineChart.setCreateSymbols(false);

        int[] dataset1 = lineGraphSettings.getDataSet1();
        int[] dataset2 = lineGraphSettings.getDataSet2();

        for (int i = 0; i < dataset1.length; i += 1) {
            series1.getData().add(new XYChart.Data(i, dataset1[i]));
            series2.getData().add(new XYChart.Data(i, dataset2[i]));
        }

        lineChart.getData().addAll(series1, series2);

        return lineChart;

    }

    public static BarChart<String, Number> plotBarChart(BarGraphSettings barGraphSettings){

        /* Return null if no data provided */
        if(barGraphSettings.getDataset1() == null ||
                barGraphSettings.getDataset2() == null){
            System.out.println("Bar Chart : No data provided");
            return null;
        }

        /* Return null if no labels provided */
        if(barGraphSettings.getxAxisLabel() == null){
            System.out.println("Bar Chart : No xLabels Provided");
            return null;
        }

        /* Set title to blank if none provided */
        if(barGraphSettings.getGraphTitle() == null){
            barGraphSettings.setGraphTitle("");
        }

        /* set labels to dataset 1 and dataset 2 if missing */
        if(barGraphSettings.getData1Title() == null){
            barGraphSettings.setData1Title("dataset 1");
        }

        if(barGraphSettings.getData2Title() == null){
            barGraphSettings.setData2Title("dataset 2");
        }


        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String,Number> barChart = new BarChart<String,Number>(xAxis,yAxis);
        barChart.setTitle(barGraphSettings.getGraphTitle());
        xAxis.setLabel(barGraphSettings.getxAxisTitle());
        yAxis.setLabel(barGraphSettings.getyAxisTitle());

        XYChart.Series series1 = new XYChart.Series();
        series1.setName(barGraphSettings.getData1Title());

        int[] dataset1 = barGraphSettings.getDataset1();
        int[] dataset2 = barGraphSettings.getDataset2();

        String[] xAxisLabels = barGraphSettings.getxAxisLabel();

        for(int i=0; i<dataset1.length;i++){
            series1.getData().add(new XYChart.Data(xAxisLabels[i],dataset1[i]));
        }

        XYChart.Series series2 = new XYChart.Series();
        series2.setName(barGraphSettings.getData2Title());

        for(int i=0; i<dataset2.length;i++){
            series2.getData().add(new XYChart.Data(xAxisLabels[i],dataset2[i]));
        }

        barChart.getData().addAll(series1,series2);

        return barChart;
    }

}

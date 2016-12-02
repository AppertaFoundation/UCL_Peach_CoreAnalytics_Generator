package TestFolder;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import model.NoiseGenerator;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import java.util.Arrays;
import java.util.Random;

public class NoiseAdditionTest extends Application {


    public static double[] randArr;
    public static int[] counter;


    public static void main(String[] args){

        Random random = new Random();

        int sampleSize = 10000;

        randArr = new double[sampleSize];
        counter = new int[sampleSize];

        for (int i=0; i<randArr.length;i++){
            randArr[i] = random.nextGaussian();
            counter[i] = i;
        }


        //Arrays.sort(randArr);

        launch(args);


    }

    public static int[] perturbDataSet(int[] originalDataSet, double scalingFactor){

        /* If dataset is null return null */
        if(originalDataSet == null){
            return null;
        }

        /* Scaling factor must be >= 0.0 */
        if(scalingFactor < 0){
            return null;
        }

        //get standard deviation
        DescriptiveStatistics stats = new DescriptiveStatistics();

        for(int value: originalDataSet){
            stats.addValue(value);
        }

        double mean = stats.getMean();
        double standardDeviation = stats.getStandardDeviation();

        Random rand = new Random();

        int[] perturbData = new int[originalDataSet.length];

        double randNoise;

        for(int i=0;i<originalDataSet.length;i++){
            randNoise = rand.nextGaussian()*standardDeviation*scalingFactor;
            perturbData[i] = (int)((double) originalDataSet[i] + randNoise);

            //min value = 1
            if (perturbData[i] < 0){
                perturbData[i] = 1;
            }
        }

        return perturbData;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        //defining the axes
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();

        xAxis.setLabel("sample data");

        //creating the chart
        final LineChart<Number, Number> lineChart =
                new LineChart<Number, Number>(xAxis, yAxis);

        lineChart.setTitle("noise generator test");

        //defining a series
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("test data");

        //turn off point markers
        lineChart.setCreateSymbols(false);

        double[] dataset1 = randArr;

        for (int i = 0; i < dataset1.length; i += 1) {
            series1.getData().add(new XYChart.Data(dataset1[i],i));
        }

        lineChart.getData().add(series1);

        Stage stage = primaryStage;

        Scene scene  = new Scene(lineChart,800,600);

        stage.setScene(scene);
        stage.show();

    }
}

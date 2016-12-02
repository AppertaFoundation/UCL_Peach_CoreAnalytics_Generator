package TestFolder;

import external.utility.AliasMethod;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.util.ArrayList;

public class CategoricalSamplingTest extends Application {

    public static int samples;
    public static double tolerance;

    protected static ArrayList<String> categoryLabels = new ArrayList<>();
    protected static ArrayList<Double> original = new ArrayList<>();
    protected static ArrayList<Double> random = new ArrayList<>();

    @Override public void start(Stage stage) {

        /* Plot bar chart of results */

        stage.setTitle("Categorical sampling test");
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String,Number> bc =
                new BarChart<String,Number>(xAxis,yAxis);

        String tol_round = String.format("%.5g%n", tolerance);

        bc.setTitle("Categorical sampling test n = " + samples + "    Max difference: " + tol_round);
        xAxis.setLabel("Category");
        yAxis.setLabel("Probability");

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("original");

        for(int i=0; i<original.size();i++){
            series1.getData().add(new XYChart.Data(categoryLabels.get(i), original.get(i)));
        }

        XYChart.Series series2 = new XYChart.Series();
        series2.setName("randomly sampled");
        for(int i=0; i<original.size();i++){
            series2.getData().add(new XYChart.Data(categoryLabels.get(i), random.get(i)));
        }

        /* Display results */
        Scene scene  = new Scene(bc,800,600);
        bc.getData().addAll(series1, series2);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

        /* Original probability distribution */
        original.add(0.20);
        original.add(0.30);
        original.add(0.35);
        original.add(0.15);


        AliasMethod discreteSampler = new AliasMethod(original);

        /* set sample size */
        samples = 100;

        /* Use alias method to sample from original distribution */
        int[] results = new int[samples];

        for(int i=0;i<samples;i++){
            results[i] = discreteSampler.next();
        }

        /* Calculate the probability distribution */
        double[] probRandom = new double[original.size()];

        for(int i=0;i<results.length;i++){
            probRandom[results[i]] += 1;
        }

        int count = 0;

        /* tolerance */
        tolerance = 0;


        /* Add distributions to array last of results from random sampling */
        for(double value: probRandom){
            value = value / (double) results.length;
            random.add(value);
            categoryLabels.add("Label " + String.valueOf(count));
            // System.out.println("Prob: " + value);

            if(Math.abs(original.get(count) - value) > tolerance){
                tolerance = Math.abs(original.get(count) - value);
            }

            count++;
        }

        /* Plot graph */
        launch(args);
    }

}

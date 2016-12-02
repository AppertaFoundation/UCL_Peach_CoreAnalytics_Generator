package model;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import external.utility.StdRandom;

import java.util.Random;

public class NoiseGenerator {

    public static int[] perturbDataSet(int[] originalDataSet, double scalingFactor){

        /**/
        final int MIN_VALUE = 1 ;
        final int MIN_THRESHOLD = 0 ;


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
        double variance = stats.getVariance();

        Random rand = new Random();

        int[] perturbData = new int[originalDataSet.length];

        double randNoise;

        for(int i=0;i<originalDataSet.length;i++){
            randNoise = rand.nextGaussian()*variance*scalingFactor;
            perturbData[i] = (int)((double) originalDataSet[i] + randNoise);

            /* ensure min value within acceptable range */
            if (perturbData[i] < MIN_THRESHOLD){
                perturbData[i] = MIN_VALUE;
            }
        }

        return perturbData;
    }

    public static int[] randomBinaryData(int size, double prob){

        /* probability must be in range 0 < p(x) < 1.0  */
        if(prob > 1.0 || prob < 0){
            System.out.println("BinaryDataGenerator - Prob val error ");
            return null;
        }

        /* magnitude of outputs must be >= 1 */
        if(size < 1){
            System.out.println("BinaryDataGenerator - Err size of output less than 1");
            return null;
        }

        int[] retArray = new int[size];

        for (int i=0; i<size;i++){
            if(StdRandom.bernoulli(prob)){
                retArray[i] = 1;
            } else {
                retArray[i] = 0;
            }
        }

        return retArray;
    }

}

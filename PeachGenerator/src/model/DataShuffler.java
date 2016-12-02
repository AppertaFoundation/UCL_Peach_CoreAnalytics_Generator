package model;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class DataShuffler {



    public static String[] shuffleArray(String[] data){

        /* Check input */
        if(data == null){
            return null;
        }

        /* For single value - return value */
        if(data.length == 1){
            System.out.println("data length == 1 shuffling not possible");
            return data;
        }

        //shuffle data
        String headerInfo = data[0];

        //new array slightly smaller than original array
        String[] shuffleData = new String[data.length-1];

        //remove the header
        for (int i=1;i<shuffleData.length+1;i++){
            shuffleData[i-1] = data[i];
        }

        Collections.shuffle(Arrays.asList(shuffleData));

        //reconstruct the outputlist
        for (int i=0; i<shuffleData.length;i++){
            data[i+1] = shuffleData[i];
        }

        return data;
    }

}

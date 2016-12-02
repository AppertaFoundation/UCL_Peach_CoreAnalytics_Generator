package model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.Arrays;

import static org.junit.Assert.*;


public class DataShufflerTest {

    /* Generate mock data */
    String[] testInput = {"1","2","3","4","5"};

    /**
     * Check functionality of shuffler
     * @throws Exception
     */
    @Test
    public void testShuffleArray() throws Exception {

        /* create same array as input */
        String[] shuffledArray = {"1","2","3","4","5"};

        /* check arrays are the same */
        Assert.assertTrue(Arrays.equals(testInput,shuffledArray));

        /* pass through shuffler method */
        shuffledArray = DataShuffler.shuffleArray(shuffledArray);

        /* ensure shuffled array != testInput */
        Assert.assertFalse(Arrays.equals(testInput,shuffledArray));

    }

    /**
     * Check behaviour of edge case array == null
     */
    @Test
    public void testNullInputShuffleArray(){

        /* Pass null */
        String[] result = DataShuffler.shuffleArray(null);

        /* Expected output : null */
        Assert.assertTrue(result == null);

    }


    /**
     * Check behaviour of edge case, array.length == 1
     */
    @Test
    public void testSingleInput(){

        /* mock single item */
        String[] testArr = {"A"};

        /* Expected value == original item */
        testArr = DataShuffler.shuffleArray(testArr);

        /*Check size has not changed and value is the same */
        Assert.assertTrue(testArr.length == 1);
        Assert.assertTrue(testArr[0].equals("A"));

    }

}
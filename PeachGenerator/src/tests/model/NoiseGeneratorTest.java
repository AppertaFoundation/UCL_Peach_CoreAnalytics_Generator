package model;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class NoiseGeneratorTest {

    /* mock data set */
    protected int[] dataSet = {1,2,9,2,5,8,6,9,7,2};

    /**
     * Check response to null input
     * @throws Exception
     */
    @Test
    public void testNullInputPerturbDataSet() throws Exception {
        int[] result = NoiseGenerator.perturbDataSet(null, 2);
        Assert.assertTrue(result == null);
    }

    /**
     * Check response to invalid scaling factor value
     * @throws Exception
     */
    @Test
    public void testInvalidScalingPerturbDataSet() throws Exception {
        int[] result = NoiseGenerator.perturbDataSet(dataSet, -1);
        Assert.assertTrue(result == null);
    }

    /**
     * Test invalid size of return elements
     */
    @Test
    public void testSizeRandomBinaryData() throws Exception {
        /* Test with valid value */
        int[] result = NoiseGenerator.randomBinaryData(5,0.50);
        Assert.assertFalse(result == null);

        /* Test with invalid value */
        result = NoiseGenerator.randomBinaryData(-1,0.50);
        Assert.assertTrue(result == null);
    }

    /**
     * Test with invalid probability i.e. > 0.0
     * @throws Exception
     */
    @Test
    public void testProbInputBinaryData() throws Exception {
        /* Test with valid value */
        int[] result = NoiseGenerator.randomBinaryData(5,0.5);
        Assert.assertFalse(result == null);

        /* Test with invalid value */
        result = NoiseGenerator.randomBinaryData(5,-0.1);
        Assert.assertTrue(result == null);

    }

    /**
     * Test with invalid prob value <1.0
     * @throws Exception
     */
    @Test
    public void testPropEdgeCaseBinaryData() throws Exception {
        /* Test with value greater than p = 1.0 */
        int[] result = NoiseGenerator.randomBinaryData(10,1.10);
        Assert.assertTrue(result == null);
    }




}
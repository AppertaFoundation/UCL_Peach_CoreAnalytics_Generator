package model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ARXkAnonymiserTest {

    protected ArrayList<String[]> resultArr;
    protected kAnonData[] kAnonDataArr;
    protected ARXkAnonymiser kAnonymiser;
    protected File testInputFile;

    /* create mock object */
    @Before
    public void createkAnonMockData(){

        testInputFile = new File("testAnonInput.csv");

        kAnonymiser = new ARXkAnonymiser();

        File ageFile = new File("__hierarchy__age.csv");
        File genderFile = new File("__hierarchy__gender.csv");

        kAnonData col1 = new kAnonData(
                "DEMOGKEY",
                "Insensitive",
                null
        );

        kAnonData col2 = new kAnonData(
                "DOB",
                "Quasi-Identifier",
                ageFile
        );

        kAnonData col3 = new kAnonData(
                "SEX",
                "Quasi-Identifier",
                genderFile
        );

        /* note: for testing purposes only - surname usually identifier */
        kAnonData col4 = new kAnonData(
                "SURNAME",
                "Insensitive",
                null
        );

        kAnonDataArr = new kAnonData[4];

            kAnonDataArr[0] = col1;
            kAnonDataArr[1] = col2;
            kAnonDataArr[2] = col3;
            kAnonDataArr[3] = col4;

        System.out.println("here");

    }

    /**
     * Check correct opperation of anon process
     * @throws Exception
     */
    @Test
    public void testKAnonymiseData() throws Exception {

        ArrayList<String[]> data = ARXkAnonymiser.kAnonimiseData(testInputFile,kAnonDataArr,2);

        /* check pre-calculated values */
        Assert.assertTrue(data.get(0)[0].equals("DEMOGKEY"));
        Assert.assertTrue(data.get(24)[0].equals("301838"));
        Assert.assertTrue(data.get(24)[1].equals("[20, 30]"));
    }

    /**
     * Check for null input file
     */
    @Test
    public void testKAnonNullInputFile() throws IOException {
        resultArr = ARXkAnonymiser.kAnonimiseData(null, kAnonDataArr, 2);
        Assert.assertTrue(resultArr == null);
    }

    /**
     * Check for incorrect file types
     */
    @Test
    public void testKAnonIncorrectFile() throws IOException {
        File txtFile = new File("testFile.txt");
        resultArr = ARXkAnonymiser.kAnonimiseData(txtFile,kAnonDataArr, 2);
        Assert.assertTrue(resultArr == null);
    }

    /**
     * Check for null entered in records
     */
    @Test
    public void testNullRecords() throws IOException {
        resultArr = ARXkAnonymiser.kAnonimiseData(testInputFile,null,2);
        Assert.assertTrue(resultArr == null);
    }

    /**
     * Check for incorrect values of kAnon value
     */
    @Test
    public void testIncorrectkAnonVal() throws IOException {
        /* Check negative number case*/
        resultArr = ARXkAnonymiser.kAnonimiseData(testInputFile,kAnonDataArr,-1);
        Assert.assertTrue(resultArr == null);

        /* Check zero case */
        resultArr = ARXkAnonymiser.kAnonimiseData(testInputFile,kAnonDataArr,0);
        Assert.assertTrue(resultArr == null);
    }

}
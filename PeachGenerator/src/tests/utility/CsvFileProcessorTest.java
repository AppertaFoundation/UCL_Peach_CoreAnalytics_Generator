package utility;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class CsvFileProcessorTest {

    /* Mock data used for testing */
    protected final String[][] testData = {{"heading1","heading2"},{"val1","val2"}};

    /**
     * Test checks if data is correctly written to a new file
     * @throws Exception
     */
    @Test
    public void testWriteAllDataToCSVFile() throws Exception {

        /* Create new output file */
        File testOutputFile = new File("TestOutputFile.csv");

        /* Test function */
        boolean writeResult = CsvFileProcessor.writeAllDatatoCSVFile(testOutputFile,testData);

        /* Check file exists */
        Assert.assertTrue(testOutputFile.isFile());

        /* Check file header and data entered correctly */
        Scanner sc = new Scanner(testOutputFile);
        String strHeader = sc.next();
        String strData = sc.next();

        sc = new Scanner(strHeader);
        sc.useDelimiter(",");

        /* Test headers were correctly written to file*/
        Assert.assertTrue(sc.next().equals("heading1"));
        Assert.assertTrue(sc.next().equals("heading2"));

        sc = new Scanner(strData);
        sc.useDelimiter(",");

        /* Test values correctly inserted into the next line  */
        Assert.assertTrue(sc.next().equals("val1"));
        Assert.assertTrue(sc.next().equals("val2"));

        /* Check write is sucessful */
        Assert.assertTrue(writeResult);

        /* remove test file and scanner */
        testOutputFile.delete();
        sc.close();
    }

    /**
     * Test checks if data is correctly written to a new file
     * Test of the overloadedmathod
     * @throws Exception
     */
    @Test
    public void testArrayListWriteAllDataToCSVFile() throws Exception {

        /* Create new output file */
        File testOutputFile = new File("TestOutputFile.csv");

        /* Test data to write to file */
        String[] testHeaders = {"heading1","heading2"};
        String[] testVals = {"val1","val2"};
        ArrayList<String[]> inputArrayList = new ArrayList<String[]>();
        inputArrayList.add(0,testHeaders);
        inputArrayList.add(1,testVals);

        /* Test function */
        boolean writeResult = CsvFileProcessor.writeAllDatatoCSVFile(testOutputFile,inputArrayList);

        /* Check file exists */
        Assert.assertTrue(testOutputFile.isFile());

        /* Check file header and data entered correctly */
        Scanner sc = new Scanner(testOutputFile);
        String strHeader = sc.next();
        String strData = sc.next();

        sc = new Scanner(strHeader);
        sc.useDelimiter(",");

        Assert.assertTrue(sc.next().equals("heading1"));
        Assert.assertTrue(sc.next().equals("heading2"));

        sc = new Scanner(strData);
        sc.useDelimiter(",");

        Assert.assertTrue(sc.next().equals("val1"));
        Assert.assertTrue(sc.next().equals("val2"));

        /* Check write is sucessful */
        Assert.assertTrue(writeResult);

        /* remove test file */
        testOutputFile.delete();
    }

    /**
     * Test null input for csvFile
     * @throws Exception
     */
    @Test
    public void testNullDFileWriteAllDataToCSVFile() throws Exception {

        boolean writeResult;

        /* create valid mock data */
        String[][] testData = {{"test1","test2"},{"test3","test4"}};

        /* Pass null as csvFile */
        writeResult = CsvFileProcessor.writeAllDatatoCSVFile(null,testData);

        /* function must return false */
        Assert.assertFalse(writeResult);

    }

    /**
     * Test null input for overloaded ArrayList method
     * @throws Exception
     */
    @Test
    public void testNullArrayDFileWriteAllDataToCSVFile() throws Exception {

        boolean writeResult;

        /* create valid mock data */
        ArrayList<String[]> inputArrayList = getArrayListInput();

        /* Pass null as csvFile */
        writeResult = CsvFileProcessor.writeAllDatatoCSVFile(null,inputArrayList);

        /* function must return false */
        Assert.assertFalse(writeResult);
    }

    /**
     * Test null input for data
     * @throws Exception
     */
    @Test
    public void testNullDataFileWriteAllDataToCSVFile() throws Exception {

        boolean writeResult;

        /* create valid mock data */
        File testOutputFile = new File("TestOutputFile");

        /* Pass null data */
        writeResult = CsvFileProcessor.writeAllDatatoCSVFile(testOutputFile,(String[][]) null);

        /* function must return false */
        Assert.assertFalse(writeResult);

    }

    /**
     * Test null input for data - overloaded arrayList method
     * @throws Exception
     */
    @Test
    public void testNullArrayListDataFileWriteAllDataToCSVFile() throws Exception {

        boolean writeResult;

        /* create valid mock data */
        File testOutputFile = new File("TestOutputFile");

        /* Pass null data */
        writeResult = CsvFileProcessor.writeAllDatatoCSVFile(testOutputFile,(ArrayList<String[]>) null);

        /* function must return false */
        Assert.assertFalse(writeResult);

    }

    /**
     * Check if non csv passed as input
     * @throws Exception
     */
    @Test
    public void testCheckFileExtentionWriteAllData() throws Exception {

        /* Feed txt file instead of CSV file */
        File testFile = new File("testFile.txt");

        boolean writeResult = CsvFileProcessor.writeAllDatatoCSVFile(testFile,testData);

        Assert.assertFalse(writeResult);

    }

    /**
     * Check is non csv passed as input ArrayList case
     * @throws Exception
     */
    @Test
    public void testCheckFileExtentionArrayWriteAllData() throws Exception {

        /* Feed txt file instead of CSV file */
        File testFile = new File("testFile.txt");

        boolean writeResult = CsvFileProcessor.writeAllDatatoCSVFile(testFile,getArrayListInput());

        Assert.assertFalse(writeResult);

    }

    /**
     * Check correct headers and number of headers returned
     * @throws Exception
     */
    @Test
    public void testGetCsvFileHeaders() throws Exception {

        /* load test csv file */
        File testFile = new File("TestInputFile.csv");
        String[] headers = CsvFileProcessor.getCsvFileHeaders(testFile);

        /* check headers in file */
        Assert.assertTrue(headers[0].equals("firstHeading"));
        Assert.assertTrue(headers[1].equals("secondHeading"));

        /* check size == no. of headers */
        Assert.assertTrue(headers.length == 2);

    }

    /***
     * Check invalid inputs for CsvGetFileHeaders method
     */
    @Test
    public void testFileInputGetFileHeaders(){

        /* function output */
        String[] returnData;

        /* feed null */
        returnData = CsvFileProcessor.getCsvFileHeaders(null);

        /* return null */
        Assert.assertTrue(returnData == null);

        /* create invalid file */
        File txtFile = new File("testFile.txt");

        /* pass txtFile */
        returnData = CsvFileProcessor.getCsvFileHeaders(txtFile);

        /* null should be returned */
        Assert.assertTrue(returnData == null);

    }

    /**
     * Check correct number of records returned
     * @throws Exception
     */
    @Test
    public void testGetCsvData() throws Exception {
        /* Create a test file */
        File testFile = new File("TestInputFile.csv");

        /* Get 5 rows (incl. header) */
        String[][] data = CsvFileProcessor.getCsvData(testFile,5);

        /* Check correct number of records returned */
        Assert.assertTrue(data.length == 5);
        Assert.assertTrue(data[0].length == 2);

        /* Check the first and last records */
        Assert.assertTrue(data[0][0].equals("firstHeading"));
        Assert.assertTrue(data[4][0].equals("2862"));
    }

    /***
     * Test invalid file inputs for getCsvData method
     * @throws Exception
     */
    @Test
    public void testFileGenerateColumnData() throws Exception {

        /* Pass invalid file type */
        File testInvalidFile = new File("newFile.txt");
        String[][] methodResult = CsvFileProcessor.getCsvData(testInvalidFile,5);

        Assert.assertTrue(methodResult == null);

        /* Pass null */
        methodResult = CsvFileProcessor.getCsvData(null, 5);

        Assert.assertTrue(methodResult == null);

    }

    /***
     * Test invalid row inputs for getCsvData method
     * @throws Exception
     */
    @Test
    public void testRowsGenerateColumnData() throws Exception {

        String[][] methodResult;

        /* Pass invalid number of rows */
        File testValidFile = new File("TestInputFile.csv");
        methodResult = CsvFileProcessor.getCsvData(testValidFile,0);

        Assert.assertTrue(methodResult == null);

        /* Pass too many rows */
        String[][] newResult = CsvFileProcessor.getCsvData(testValidFile,100);

        Assert.assertTrue(newResult.length == 25);
        Assert.assertTrue(newResult[24][0].equals("9940"));
    }

    /**
     * Test empty file and incorrect file case
     */
    @Test
    public void testRowsInputGenerateColumnData(){

        /* Mock file object */
        File testFile = new File("TestInputFile.csv");

        /* pass 0 rows */
        String[][] returnDataZero = CsvFileProcessor.generateColumnData(testFile,0);
        Assert.assertTrue(returnDataZero == null);

        /* pass >max rows */
        String[][] returnDataMax = CsvFileProcessor.generateColumnData(testFile,100);
        Assert.assertTrue(returnDataMax[0].length == 25);

    }

    /**
     * Test rows = 0 and rows > rows in file case
     */
    @Test
    public void testFileInputGenerateColumnData(){

        /* null file case */
        String[][] nullData = CsvFileProcessor.generateColumnData(null, 10);

        Assert.assertTrue(nullData == null);

        /* wrong file format case */
        File wrongFormat = new File("testFile.txt");

        String[][] wrongFormatData = CsvFileProcessor.generateColumnData(wrongFormat,10);

        Assert.assertTrue(wrongFormatData == null);

    }

    /* Convenience method - mock data */
    private ArrayList<String[]> getArrayListInput (){
        String[] testHeaders = {"heading1","heading2"};
        String[] testVals = {"val1","val2"};
        ArrayList<String[]> inputArrayList = new ArrayList<String[]>();
        inputArrayList.add(0,testHeaders);
        inputArrayList.add(1,testVals);
        return inputArrayList;
    }

}
package review;

import model.DataShuffler;
import utility.CsvFileProcessor;

import java.io.File;

public class Anonimiser {


    public boolean anonimiseFile(File inputFile, File outputLocation){

        //output file will have same name is input file
        File outputFile = new File(outputLocation.getAbsolutePath() + "/" + inputFile.getName());

        //Get first 20 values of each column
        String[][] csvData = CsvFileProcessor.getCsvData(inputFile,20);

        //Create a new table object
        DataTable dataTable = new DataTable(csvData[0].length);

        //insert the data into the table object
        for (int i=0; i<csvData.length; i++){
            dataTable.insertRecord(csvData[i]);
        }

        //get column representation of folder
        String[][] testData = dataTable.getDataColumns();

        //Shuffle all the data
        for(String[] columnData : testData){
            columnData = DataShuffler.shuffleArray(columnData);
        }

        //Convert data from columns back to rows
        String[][] outputRowData = DataTable.generateRowData(testData);

        //write the data back to file
        CsvFileProcessor.writeAllDatatoCSVFile(outputFile,outputRowData);

        return true;
    }


    public static void main(String[] args){

        File testInputFile = new File("/Users/Macintosh_HD/Desktop/inputFiles/DATADEMOG.csv");
        File testOutputFile = new File("/Users/Macintosh_HD/Desktop/outputFiles/DATADEMOG.csv");

        //Get the header for the testFile
        String[] csvFileHeader = CsvFileProcessor.getCsvFileHeaders(testInputFile);

        //Count the number of columns
        int columnCount = csvFileHeader.length;

        //Get first 20 values of each column
        String[][] csvData = CsvFileProcessor.getCsvData(testInputFile,20);

        //Create a new table object
        DataTable dataTable = new DataTable(csvData[0].length);

        //insert the data into the table object
        for (int i=0; i<csvData.length; i++){
            dataTable.insertRecord(csvData[i]);
        }

        //test the data as columns
        String[][] testData = dataTable.getDataColumns();

        //Shuffle all the data
        for(String[] columnData : testData){
            columnData = DataShuffler.shuffleArray(columnData);
        }

        //Convert data from columns back to rows
        String[][] outputRowData = DataTable.generateRowData(testData);

        //write the data back to file
        CsvFileProcessor.writeAllDatatoCSVFile(testOutputFile,outputRowData);

    }

}

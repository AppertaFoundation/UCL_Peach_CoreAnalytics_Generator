package utility;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Utility class the is used to:
 * Retrieve headers from a CSV file
 * Write headers to a CSV file
 */
public class CsvFileProcessor {

    public static boolean writeAllDatatoCSVFile(File csvFile, String[][] data){

        /* Check input are correct */
        if(csvFile == null || data == null){return false;}

        /* Check file is a CSV file */
        String fileExtn = FilenameUtils.getExtension(csvFile.getAbsolutePath());
        if(!fileExtn.equals("csv")){return false;}

        //output string
        StringBuilder writeData = new StringBuilder();

        //Filepath where new datafile will be created / accessed
        String saveAbsolutePath = csvFile.getAbsolutePath();

        //Specify the Delimiter and new line seperator
        final String COMMA_DELIMITER = ",";
        final String NEW_LINE_DELIMTER = "\n";

        for (String[] rowData : data){
            for(String dataItem : rowData){
                writeData.append(dataItem);
                writeData.append(COMMA_DELIMITER);
            }
            writeData.deleteCharAt(writeData.length()-1);
            writeData.append(NEW_LINE_DELIMTER);
        }

        writeData.deleteCharAt(writeData.length()-1);

        //use filewriter to write to the CSV
        FileWriter fileWriter = null;

        try {
            fileWriter = new FileWriter(saveAbsolutePath,true);

            //Write the CSV file header to the new file
            fileWriter.append(writeData.toString());

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        //Flush the filewriter buffers
        try {
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;

    }


    public static boolean writeAllDatatoCSVFile(File csvFile, ArrayList<String[]> data){

        /* Check input are correct */
        if(csvFile == null || data == null){return false;}

        /* Check file is a CSV file */
        String fileExtn = FilenameUtils.getExtension(csvFile.getAbsolutePath());
        if(!fileExtn.equals("csv")){return false;}

        //output string
        StringBuilder writeData = new StringBuilder();

        //Filepath where new datafile will be created / accessed
        String saveAbsolutePath = csvFile.getAbsolutePath();

        //Specify the Delimiter and new line seperator
        final String COMMA_DELIMITER = ",";
        final String NEW_LINE_DELIMTER = "\n";

        for (String[] rowData : data){
            for(String dataItem : rowData){
                dataItem = dataItem.replace(",","-");
                writeData.append(dataItem);
                writeData.append(COMMA_DELIMITER);
            }
            writeData.deleteCharAt(writeData.length()-1);
            writeData.append(NEW_LINE_DELIMTER);
        }

        writeData.deleteCharAt(writeData.length()-1);

        //use filewriter to write to the CSV
        FileWriter fileWriter = null;

        try {
            fileWriter = new FileWriter(saveAbsolutePath,true);

            //Write the CSV file header to the new file
            fileWriter.append(writeData.toString());

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        //Flush the filewriter buffers
        try {
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;

    }


    public static String[] getCsvFileHeaders(File csvFile) {

        /* Check for empty or incorrect files */
        if(csvFile == null){
            System.out.println("File not found");
            return null;
        } else if (!FilenameUtils.getExtension(csvFile.getAbsolutePath()).equals("csv")){
            System.out.println("Incorrect file format");
            return null;
        }

        ArrayList<String> columnHeaders = new ArrayList<>();

        //Set the file you will be using
        File file = new File(csvFile.getAbsolutePath());

        /* Scanner to read inputs */
        try {
            Scanner lineReader = new Scanner(file);
            Scanner wordReader;

            String line = lineReader.nextLine();
            wordReader = new Scanner(line);

            //set delimiter for the scanner
            wordReader.useDelimiter(",");

            while(wordReader.hasNext()){
                columnHeaders.add(wordReader.next());
            }

        } catch (FileNotFoundException e) {
            System.out.println("error- file has not been found");
            e.printStackTrace();
        }

        String[] returnString = new String[columnHeaders.size()];

        for (int i = 0 ; i < returnString.length;i++){
            returnString[i] = columnHeaders.get(i);
        }

        return returnString;
    }


    public static String[][] getCsvData(File csvFile, int rows){

        if(csvFile == null) {
            System.out.println("null file");
            return null;
        }

        String fileExtn = FilenameUtils.getExtension(csvFile.getAbsolutePath());

        if(!fileExtn.equals("csv")){
            return null;
        }

        if(rows == 0){
            return null;
        }

        //Get the csv File headers
        String[] csvFileHeader = getCsvFileHeaders(csvFile);

        //Return array for the method
        String[][] csvRowData = new String[rows][csvFileHeader.length];

        //Scanner to read words in a row
        Scanner wordReader;

        //Temp storage for lines
        String lineData;

        //number of rows counted
        int rowReadCount = 0;

        try {
            Scanner lineReader = new Scanner(csvFile);

            while(rowReadCount < rows && lineReader.hasNext()){

                //Read the next line
                lineData = lineReader.next();

                //Create new scanner to read words in the sentence
                wordReader = new Scanner(lineData);
                wordReader.useDelimiter(",");

                //populate the array with the csv Data
                for(int i=0; i<csvFileHeader.length;i++){
                    csvRowData [rowReadCount][i] = wordReader.next();
                }

                //read the next row
                rowReadCount++;

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        /* trim array to size if rows > rows in file */
        if(rowReadCount < rows){
            String[][] newRetData = new String[rowReadCount][csvRowData[0].length];
            for(int i=0 ; i<rowReadCount; i++){
                newRetData[i] = csvRowData[i];
            }
            return newRetData;
        }

        return csvRowData;
    }


    public static String[][] generateColumnData(File csvFile, int rows){

        String[][] csvRowData = CsvFileProcessor.getCsvData(csvFile,rows);

        if(csvFile == null || rows == 0){
            return null;
        }

        String fileExtn = FilenameUtils.getExtension(csvFile.getAbsolutePath());
        if(!fileExtn.equals("csv")){
            return null;
        }

        String[][] retData = new String[csvRowData[0].length][csvRowData.length];

        for (int row=0; row <csvRowData.length; row++){
            for(int col=0; col<csvRowData[0].length; col++){
                retData[col][row] = csvRowData[row][col];
            }
        }

        return retData;
    }

}

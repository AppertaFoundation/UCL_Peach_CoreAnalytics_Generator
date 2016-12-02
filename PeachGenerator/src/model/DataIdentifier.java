package model;

import utility.DataProperty;
import utility.CsvFileProcessor;

import java.io.*;
import java.util.HashMap;

public class DataIdentifier {

    public static String[] getTypeVector(File csvFile) throws IOException {

        int rows = getRows(csvFile);


        String[][] colData = CsvFileProcessor.generateColumnData(csvFile,rows);

        String[] retArr = new String[colData.length];

        for (int i=0; i<colData.length;i++){
            retArr[i] = autoIdentifyType(colData[i]);
        }

        return retArr;
    }

    public static String autoIdentifyType(String[] colData){

        String header = colData[0].toLowerCase();
        boolean emptyFlag = true;

        /* Check for empty */
        for(int i=1; i<colData.length;i++){
            if(!colData[i].equals("NULL")){
                emptyFlag = false;
                break;
            }
        }

        if(emptyFlag){
            return "empty";
        }

        /* Check for name */
        if(header.contains("name")){
            return "name";
        }

        if(org.apache.commons.lang.StringUtils.isNumeric(colData[1])){
            /* Check data type */
            HashMap<String,Integer> inputData = DataProperty.getEnumFrequency(colData);
            if(inputData.size() > 0.1*colData.length){
                return "continious";
            } else {
                return "categorical";
            }
        } else {
            return "categorical";
        }

    }


    private static int getRows(File file) throws IOException {

        LineNumberReader lnr = new LineNumberReader(new FileReader(file));
        lnr.skip(Long.MAX_VALUE);
        lnr.close();

        return lnr.getLineNumber() + 1;
    }


}

package review;

import java.util.ArrayList;

public class DataTable {

    private int columnCount;
    private ArrayList<String[]> tableData = new ArrayList<>();

    //Define the number of columns in the
    public DataTable(int columnCount) {
        this.columnCount = columnCount;
    }

    //insert new records into the file
    public void insertRecord (String[] record){
        tableData.add(record);
    }

    //Swap all the values in the tables
    public String[][] getDataColumns (){

        //temp storage for column data
        String[][] outputData = new String[columnCount][tableData.size()];

        for (int row=0; row<tableData.size();row++){
            for (int column=0; column<columnCount;column++){
                outputData[column][row] = tableData.get(row)[column];
            }
        }

        return outputData;
    }

    public static void main(String[] args){
        System.out.println("test");
    }


    //takes an array and converts it into row data
    public static String[][] generateRowData(String[][] data){

        String[][] outputData = new String[data[0].length][data.length];

        for (int i = 0; i < data[0].length; i++){
            for (int j = 0; j < data.length; j++){
                outputData[i][j] = data[j][i];
            }
        }

        return outputData;
    }

}

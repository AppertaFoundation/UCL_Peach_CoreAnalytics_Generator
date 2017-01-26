package review;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import utility.CsvFileProcessor;

import java.io.File;

/**
 * Created by Macintosh_HD on 28/08/2016.
 */
public class TableManager {

    private File dataFile;

    //Table view and data
    private ObservableList<ObservableList> data;
    private TableView tableView = new TableView();
    private int j = 0;

    public TableManager(File dataFile){
        this.dataFile = dataFile;
    }

    public TableView generateDataTable() {

        //get Data from file
        String[][] csvData = CsvFileProcessor.getCsvData(dataFile, 30);

//        for (int i = 0; i < csvData.length; i++) {
//            for (int j = 0; j < csvData[0].length; j++) {
//                System.out.print(csvData[i][j] + ' ');
//            }
//            System.out.println();
//        }
        data = FXCollections.observableArrayList();

        for(int i=0;i<csvData[0].length;i++){
            j=i;
            String[] columnNames = csvData[0];
            TableColumn col = new TableColumn(columnNames[i]);

            col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList,String> param) {
                    return new SimpleStringProperty(param.getValue().get(j).toString());
                }
            });

            tableView.getColumns().add(col);

        }

        //add data to the Observable list
        for(int i =1; i<csvData.length; i++){
            ObservableList<String> row = FXCollections.observableArrayList();
            for(int j=0; j<csvData[0].length;j++){
                row.add(csvData[i][j]);
//                System.out.println(row.get(j));
            }
            data.add(row);
        }
//        System.out.println(data.get(5).get(2));
        tableView.setItems(data);
        return tableView;
    }

}

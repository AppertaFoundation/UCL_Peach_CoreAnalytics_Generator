package Presenter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.*;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.DataIdentifier;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.lang.*;
import review.TableManager;
import utility.CsvFileProcessor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class NoiseInjectorMainController {

    public Button btnBrowseFile;
    public Button btnBackNoiseInjection;
    public Button btnNext;

    public ScrollPane tableScrollPane;
    public ScrollPane settingsScrollPane;

    public GridPane settingsGridPane;

    public File inputFile;

    public TextField txtFieldFileSelected;

    private boolean guiPage2 = false;

    private String[][] comboBoxData;

    public void browseForFile(ActionEvent event){

        //Create a file chooser for selecting the input file
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Input File");

        inputFile = fileChooser.showOpenDialog(btnBrowseFile.getScene().getWindow());

        txtFieldFileSelected.setText(inputFile.getName());

    }

    public void loadFile(ActionEvent event) throws IOException {

        /* Create table for the preview */
        TableManager tableManager = new TableManager(inputFile);
        TableView newDataTable = tableManager.generateDataTable();

        newDataTable.setPrefHeight(590);

        /* Disable resizing of table columns */
        newDataTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);


        tableScrollPane.setContent(newDataTable);

        /* Get headers for settings scrollpane */
        String[] headers = CsvFileProcessor.getCsvData(inputFile,2)[0];

        comboBoxData = new String[3][headers.length];

        /* Populate 2D that contains settings value */
        for (int cols =0;cols<3;cols++){
            for(int rows =0;rows<headers.length; rows++){
                comboBoxData[cols][rows] = "noval";
            }
        }

        /* Setting information - first col == field name  */
        for (int i=0;i<headers.length;i++){
            comboBoxData[0][i] = headers[i];
        }

        /* Gridpane for settings */
        settingsGridPane = new GridPane();
        settingsGridPane.setVgap(10);
        settingsGridPane.setHgap(10);

        //set headings
        settingsScrollPane.setPadding(new Insets(5,5,5,5));

        //set the headings for the modify settings pane
        Text heading1 = new Text("Columm");
        heading1.setFont(Font.font(heading1.getFont().toString(),FontWeight.BOLD,14));

        Text heading2 = new Text("Privacy");
        heading2.setFont(Font.font(heading2.getFont().toString(),FontWeight.BOLD,14));

        Text heading3 = new Text("Data Type");
        heading3.setFont(Font.font(heading3.getFont().toString(),FontWeight.BOLD,14));


        settingsGridPane.add(heading1,0,1);
        settingsGridPane.add(heading2,1,1);
        settingsGridPane.add(heading3,2,1);

        ObservableList<String> optionsDataType = FXCollections.observableArrayList(
          "Continous","Categorical","Name","Empty",""
        );

        ObservableList<String> optionsPrivacy = FXCollections.observableArrayList(
                "Identifier","Quasi-Identifier","Sensitive","Insensitive"
        );


        /*
        int[] testPrivacy = {3,1,3,0,0,3,3,3,3,1,0,3,3,3,3};
        int[] testDataType = {4,0,4,2,2,4,4,4,4,1,2,4,3,3,4};
        */

        /*
        for(int i=0;i<headers.length;i++){
            settingsGridPane.add(new Text(headers[i]),0,i+2);
            settingsGridPane.add(generateComboBox(optionsPrivacy,testPrivacy[i],1,i),1,i+2);
            settingsGridPane.add(generateComboBox(optionsDataType,testDataType[i],2,i),2,i+2);
        }
        */

        String[] typeVector = DataIdentifier.getTypeVector(inputFile);

        int dataRef;

        for(int i=0;i<headers.length;i++){

            dataRef = tpyeRef(typeVector[i]);

            settingsGridPane.add(new Text(headers[i]),0,i+2);
            settingsGridPane.add(generateComboBox(optionsPrivacy,0,1,i),1,i+2);
            settingsGridPane.add(generateComboBox(optionsDataType,dataRef,2,i),2,i+2);
        }

        settingsScrollPane.setContent(settingsGridPane);

    }

    private ComboBox generateComboBox(ObservableList<String> options){

        ComboBox comboBox = new ComboBox(options);

        //comboBox.getSelectionModel().select(0);
        comboBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                comboBoxHandler(event);
            }
        });

        return comboBox;
    }


    private ComboBox generateComboBox(ObservableList<String> options, int index, int col, int row){

        ComboBox comboBox = new ComboBox(options);

        comboBox.getSelectionModel().select(index);

        comboBoxData[col][row] = comboBox.getSelectionModel().getSelectedItem().toString();

        //comboBox.getSelectionModel().select(0);
        comboBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                comboBoxHandler(event);
            }
        });

        return comboBox;
    }


    private void comboBoxHandler(ActionEvent event){

        //Node comboBox = (Node) event.getSource();
        ComboBox comboBox = (ComboBox) event.getSource();

        /* reference to node modified */
        int row = settingsGridPane.getRowIndex(comboBox);
        int column = settingsGridPane.getColumnIndex(comboBox);

        /* value of selected item */
        String cBoxValue = comboBox.getSelectionModel().getSelectedItem().toString();

        /* Disable DataType Box if insensitive selected */
        if(cBoxValue.equals("Insensitive")){
            System.out.println("DISABLE NEXT BOX");
            ComboBox privacyBox = (ComboBox) getNodeByRowColumnIndex(row,column+1,settingsGridPane);
            privacyBox.disableProperty().setValue(true);
        } else if(column == 1) {
            ComboBox privacyBox = (ComboBox) getNodeByRowColumnIndex(row,column+1,settingsGridPane);
            privacyBox.disableProperty().setValue(false);
        }

        /* Update the tracking object */
        comboBoxData[column][row-2] = cBoxValue;

    }


    public void navigationButtonClick(ActionEvent event) throws Exception{

        Stage stage = null;
        Parent root = null;

        int width = 500;
        int height = 640;

        if (event.getSource() == btnBackNoiseInjection) {
            stage = (Stage) btnBackNoiseInjection.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("../view/mainGUI.fxml"));
        } else if(event.getSource() == btnNext ) {
            ArrayList<String> unfilledOptions = checkSettingData();

            if(unfilledOptions.size()!=0){
                generateUnfilledAlertBox(unfilledOptions);
            }else {

                guiPage2 = true;

                stage = (Stage) btnNext.getScene().getWindow();

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/noiseInjectorGUImodify.fxml"));
                root = (Parent) fxmlLoader.load();
                NoiseInjectorModifyController controller = fxmlLoader.<NoiseInjectorModifyController>getController();
                controller.setComboBoxValues(comboBoxData);
                controller.setInputFile(inputFile);
                controller.initialiseGUI();

                //root = FXMLLoader.load(getClass().getResource("../view/noiseInjectorGUImodify.fxml"));

                height = 1024;
                width = 800;
            }
        }

        //only execute the change if root is not null
        if(root != null) {
            Scene scene = new Scene(root,height,width);
            stage.setScene(scene);
            stage.show();

        }

    }


    //Check to make sure that any values are not empty
    public ArrayList<String> checkSettingData (){

        ArrayList<String> returnList = new ArrayList<>();

        for(int col=1;col<comboBoxData.length;col++){
            for(int row=1;row<comboBoxData[0].length;row++){
                if(comboBoxData[col][row].equals("noval") && col==1){
                    returnList.add("Privay Setting: " + comboBoxData[0][row]);
                } else if (comboBoxData[col][row].equals("noval") && col == 2){
                    returnList.add("Data Type Setting: " + comboBoxData[0][row]);
                }
            }
        }

        return returnList;

    }


    private void generateUnfilledAlertBox(ArrayList<String> messages){

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Missing Information");
        alert.setHeaderText(null);

        StringBuilder alertMessage = new StringBuilder();

        alertMessage.append("Please provide the following information:" + "\n" + "\n");

        for (String message: messages){
            alertMessage.append(message);
            alertMessage.append("\n");
        }

        alert.setContentText(alertMessage.toString());
        alert.showAndWait();

    }


    public Node getNodeByRowColumnIndex(final int row,final int column,GridPane gridPane) {
        Node result = null;
        ObservableList<Node> childrens = gridPane.getChildren();
        for(Node node : childrens) {
            if(gridPane.getRowIndex(node) == row && gridPane.getColumnIndex(node) == column) {
                result = node;
                break;
            }
        }
        return result;
    }


    protected int tpyeRef(String catType){

        /* Set default as empty */
        int retVal = 4;

        switch (catType){
            case "continious":
                retVal = 0;
                break;
            case "categorical":
                retVal = 1;
                break;
            case "name":
                retVal = 2;
                break;
            case "empty":
                retVal = 3;
                break;
            default:
                retVal = 4;
        }

        return retVal;
    }


}

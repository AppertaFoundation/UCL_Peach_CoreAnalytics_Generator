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
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.ARXkAnonymiser;
import model.kAnonData;
import utility.CsvFileProcessor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class kAnonController {

    /* GUI elements */
    public Button btnBack;
    public Button btnBrowseFile;
    public Button btnSaveFile;

    public TextField txtFieldFileSelected;
    public TextField txtFieldKAnon;
    public TextField txtFieldSaveFile;

    public ScrollPane configDataScrollPane;

    protected GridPane configureDataGridPane;

    /* comboBox configuration manager */
    protected String[][] configData; 

    /* files and input data */
    protected File inputFile;
    protected File saveFile;
    protected String[] headers; 
    
    public static void main(String[] args){

    }

    public void browseForFile(ActionEvent event){

        //Create a file chooser for selecting the input file
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Input File");
        inputFile = fileChooser.showOpenDialog(btnBrowseFile.getScene().getWindow());

        /* set txtField to result */
        txtFieldFileSelected.setText(inputFile.getName());

    }

    public void navigationButtonHandler(ActionEvent event) throws Exception{

        Stage stage = null;
        Parent root = null;

        /* Return to main GUI */
        if (event.getSource() == btnBack) {
            stage = (Stage) btnBack.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("../view/mainGUI.fxml"));
        }

        //only execute the change if root is not null
        if(root != null) {
            Scene scene = new Scene(root,640,500);
            stage.setScene(scene);
            stage.show();
        }
    }

    /* Event handler for loading data into */
    public void loadFileHandler(){

        configDataScrollPane.setPadding(new Insets(10,10,10,10));
        configureDataGridPane = generateScrollPaneContent();
        configDataScrollPane.setContent(configureDataGridPane);
        
        configData = new String[3][headers.length];
        configData[0] = headers;
        
        //TODO remove - for testing only
        prefillVals();

    }


    public GridPane generateScrollPaneContent(){
        GridPane retGridPane = new GridPane();

        retGridPane.setHgap(10);
        retGridPane.setVgap(10);

        headers = CsvFileProcessor.getCsvFileHeaders(inputFile);

        retGridPane.add(new Text("Field"),0,0);
        retGridPane.add(new Text("Data Type"),1,0);
        retGridPane.add(new Text("Heirarchy"),2,0);

        //options for data type combobox;
        ObservableList<String> dataTypeOptions = FXCollections.observableArrayList(
                "Identifier","Quasi-Identifier","Sensitive","Insensitive"
        );

        //options for hierarchy combobox;
        ObservableList<String> heirarchyOptions = FXCollections.observableArrayList(
                "age","gender","none"
        );

        for(int i=1;i<headers.length+1;i++){
            retGridPane.add(new Text(headers[i-1]),0,i);
            retGridPane.add(generateComboBoxDataType(dataTypeOptions),1,i);
            retGridPane.add(generateComboBoxDataType(heirarchyOptions),2,i);
        }

        return retGridPane;
    }

    private ComboBox generateComboBoxDataType(ObservableList options){

        ComboBox retComboBox;

        retComboBox = new ComboBox(options);

        retComboBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ComboBox comboBox = (ComboBox) event.getSource();
                int row = configureDataGridPane.getRowIndex(comboBox);
                int col = configureDataGridPane.getColumnIndex(comboBox);

                configData[col][row-1] = (String) comboBox.getSelectionModel().getSelectedItem();
                System.out.println("here");
            }
        });

        return retComboBox;
    }

    public void generateFileHandler() throws IOException {

        //check k-anon property filled
        if(txtFieldKAnon.getText().equals("")){
            generateAlertBox("Please specify a valid k-anonymity parameter");
        } else {
            createkAnonData();
        }

    }

    /* Method used to generate the save file and save to disk */
    private void createkAnonData() throws IOException {

        kAnonData[] kAnonData = new kAnonData[configData[0].length];

        File[] hierarchyFiles = populateHierarchyFiles();

        for(int i=0; i<configData[0].length;i++){
            String fieldName = configData[0][i];
            String dataType = configData[1][i];

            kAnonData[i] = new kAnonData(fieldName,dataType,hierarchyFiles[i]);
        }

        int kAnonValue = Integer.valueOf(Integer.valueOf(txtFieldKAnon.getText()));

        ArrayList<String[]> kAnonimisedData = new ArrayList<>();
        kAnonimisedData = ARXkAnonymiser.kAnonimiseData(inputFile,kAnonData,kAnonValue);

        if(saveFile != null){
            CsvFileProcessor.writeAllDatatoCSVFile(saveFile,kAnonimisedData);
            generateAlertBox("File successfully created");
        } else {
            generateAlertBox("Please specify a valid save location");
        }
    }

    /* Method used to populate the generalisation hierarchy files */
    private File[] populateHierarchyFiles(){

        File[] heirarchyFiles = new File[configData[0].length];
        String hierarchyType = "";

        /* Load hierarchy files */
        File ageHierarchy = new File("__hierarchy__age.csv");
        File genderHierarchy = new File("__hierarchy__gender.csv");

        //get the configuration data
        for (int i=0; i<configData[2].length;i++){

            hierarchyType = configData[2][i];

            switch (hierarchyType) {
                case "age":
                    heirarchyFiles[i] = ageHierarchy;
                    break;
                case "gender":
                    heirarchyFiles[i] = genderHierarchy;
                    break;
                default:
                    heirarchyFiles[i] = null;
            }
        }

        return heirarchyFiles;
    }

    /*Method is used for DEMO purposes only - pre-fills comboBox values*/
    private void prefillVals(){

        /* Pre defined settings for demo file*/
        int[] dataTypePreFill = {3,1,0,0,3,3,3,3,3,1,0,3,0,0,3};
        int[] heirarchyPreFill = {2,0,2,2,2,2,2,2,2,1,2,2,2,2,2};

        /*pre fills all comboBoxs on screen*/
        for (int i=0; i < dataTypePreFill.length; i++){
            ComboBox setDataType = (ComboBox) getNodeByRowColumnIndex(i+1,1,configureDataGridPane);
            ComboBox setHeirarchy = (ComboBox) getNodeByRowColumnIndex(i+1,2,configureDataGridPane);

            setDataType.getSelectionModel().select(dataTypePreFill[i]);
            setHeirarchy.getSelectionModel().select(heirarchyPreFill[i]);

            configData[0][i] = headers[i];
            configData[1][i] = (String) setDataType.getSelectionModel().getSelectedItem();
            configData[2][i] = (String) setHeirarchy.getSelectionModel().getSelectedItem();
        }

        /*pre-defined k-anonymity setting*/
        txtFieldKAnon.setText("2");
    }


    private void generateAlertBox(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /* NOTE: THIS CODE SEGMENT HAS BEEN OBTAINED FROM: */
    /* http://stackoverflow.com/questions/20825935/javafx-get-node-by-row-and-column */
    /* Select a gridpane not by row and column index*/
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


    public void saveFileHandler(ActionEvent event){

        //Create a file chooser for selecting the input file
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Open Input File");

        String saveFileLocation = directoryChooser.showDialog(btnSaveFile.getScene().getWindow()).toString();

        //TODO allow user to input name of save file
        String saveFileName = "/kAnonData.csv";

        /* Display name of new file in txtField */
        txtFieldSaveFile.setText(saveFileLocation);

        /* set saveFile to instance variable */
        saveFile = new File(saveFileLocation+saveFileName);

    }

}

package Presenter;

import com.sun.tools.doclets.formats.html.SourceToHTMLConverter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import model.BarGraphSettings;
import model.LineGraphSettings;
import model.NoiseGenerator;
import utility.CsvFileProcessor;
import utility.DataPlotManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class NoiseInjectorModifyController {

    private String[][] originalData;
    private String[][] transformedData;
    private boolean[] transformTracker;

    private String[] maleNames;
    private String[] femaleNames;

    private File inputFile;

    private String[][] comboBoxValues;
    private Object[][] options;

    public Button btnBrowseFolder;
    public Button btnBack;
    public Button btnGenerate;

    public TextField txtFieldFolderSelected;

    public ScrollPane optionsScrollPane;
    public ScrollPane previewScrollPane;

    public GridPane modifyGridPane; 

    private File saveLocation;

    private final int OPTIONS_PANE_PADDING = 5;

    public void initialiseGUI(){

        /* set padding for scroll pane */
        optionsScrollPane.setPadding(new Insets(OPTIONS_PANE_PADDING));

        /* Create grid pane to go inside scroll pane */
        modifyGridPane = new GridPane(); 
        modifyGridPane.setHgap(10);
        modifyGridPane.setVgap(10);

        /* Define headings for grid pane */
        String[] strHeadings = {"Column","Options","Preview"};

        /* add headings to grid pane */
        createHeadings(modifyGridPane, strHeadings, FontWeight.BOLD, 14);

        /* add rows to the grid pane */
        for(int i=0;i<comboBoxValues[0].length;i++){
            addSettingsRow(modifyGridPane,i);
        }

        /* update grid pane to show content */
        optionsScrollPane.setContent(modifyGridPane);

        /* populate the data used for this view */
        originalData = CsvFileProcessor.generateColumnData(inputFile,100);
        transformedData = CsvFileProcessor.generateColumnData(inputFile,100);

        /* keep track data that is modified */
        transformTracker = new boolean[transformedData.length];

    }

    /* Insert the column headings into a parent grid pane */
    private void createHeadings(GridPane parent, String[] strHeadings, FontWeight fontWeight, int size){
        Text heading;

        for(int i=0 ; i<strHeadings.length;i++){
            heading = new Text(strHeadings[i]);
            heading.setFont(Font.font(heading.getFont().toString(),fontWeight, size));
            parent.add(heading,i,0);
        }

    }

    public void setSaveLocation(ActionEvent event){

        //Create a directory chooser for selecting the input file
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select save location");
        saveLocation = directoryChooser.showDialog(btnBrowseFolder.getScene().getWindow());

        //populate textfield accordingly
        if(saveLocation == null){
            txtFieldFolderSelected.setText("No folder selected..");
        } else {
            txtFieldFolderSelected.setText(saveLocation.getAbsolutePath());
        }
    }

    public void navigationBtnHandler(ActionEvent event) throws IOException {

        //TODO resume from here
        Stage stage = null;
        Parent root = null;

        int width = 500;
        int height = 640;

        if (event.getSource() == btnBack) {
            stage = (Stage) btnBack.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("../view/noiseInjectorGUImain.fxml"));
            width = 800;
            height = 1024;
        } else if(event.getSource() == btnGenerate ) {
            System.out.println("here");
        }

        //only execute the change if root is not null
        if(root != null) {
            Scene scene = new Scene(root,height,width);
            stage.setScene(scene);
            stage.show();

        }

    }

    private Node generateButton(String dataType){

        if(dataType.equals("Quasi-Identifier") || dataType.equals("Identifier")){
            Button paneButton = new Button("Preview");
            paneButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    previewButtonHandler(event);
                }
            });
            return paneButton;
        } else {
            return new Text("");
        }

    }

    private void previewButtonHandler(ActionEvent event){

        Button btnPreview = (Button) event.getSource();

        int row = modifyGridPane.getRowIndex(btnPreview);
        int column = modifyGridPane.getColumnIndex(btnPreview);

        loadPreviewPanel(row-2);

    }

    private void loadPreviewPanel(int row){
        //find out what needs to go in the panel

        String optionsVal = (String) options[row][0];

        switch (optionsVal){
            case "StandardDev":
                break;
            case "Identifier":
        }

        if(optionsVal.equals("StandardDev")){

            String valueDataType = comboBoxValues[2][row];
            Float stDevVal = (Float) options[row][1];

            if(valueDataType.equals("Continous")){

                TextField txtFieldVal = (TextField) getNodeByRowColumnIndex(row,1,modifyGridPane);

                System.out.println(txtFieldVal.getText());

                System.out.println(valueDataType);

                System.out.println(comboBoxValues[0][row] + " row: " + row);
                String[] colData = originalData[row];

                //parse into smaller array
                int[] baseData = new int[colData.length-1];

                //load all the values in a new array
                for(int i=1; i<colData.length;i++) {
                    baseData[i - 1] = Integer.valueOf(colData[i]);
                }

                System.out.println(stDevVal);

                int[] modData = NoiseGenerator.perturbDataSet(baseData,stDevVal);

                LineGraphSettings lineGraphSettings = new LineGraphSettings(
                        baseData,
                        modData,
                        "Patient Number",
                        "Test Graph",
                        "Original",
                        "Noisy"
                );

                LineChart lineChart = DataPlotManager.plotLineGraph(lineGraphSettings);

                String[] strModData = new String[modData.length+1];

                //add the title back in
                strModData[0] = colData[0];

                for(int i=0;i<modData.length;i++){
                    strModData[i+1] = String.valueOf(modData[i]);
                }

                //update the transfored data
                transformedData[row] = strModData;

                //update the tracker
                transformTracker[row] = true;

                previewScrollPane.setContent(lineChart);

            } else if(valueDataType.equals("categorical")){

                String[] colData = new String[originalData[row].length-1];

                //remove the header
                for(int i=1; i<originalData[row].length;i++){
                    colData[i-1] = originalData[row][i];
                }

                HashMap<String,Integer> colDataFreq = getEnumFrequency(colData);

                int enumContainerSize = colDataFreq.size();

                String[] enumLabels = new String[enumContainerSize];
                int[] enumValues = new int[enumContainerSize];

                int count = 0;

                for(Map.Entry<String, Integer> entry : colDataFreq.entrySet()){
                    enumLabels[count] = entry.getKey();
                    enumValues[count] = entry.getValue();
                    count++;
                }

                int[] baseData = enumValues;

                double enumFreqDist = baseData[0] / (double) colData.length;

                int[] randomGenderArr = NoiseGenerator.randomBinaryData((originalData[row].length)-1,enumFreqDist);

                int randMales = 0;

                for(int val: randomGenderArr){
                    randMales += val;
                }

                int randFemales = randomGenderArr.length - randMales;

                int[] modData = {randMales,randFemales};

                BarGraphSettings barGraphSettings = new BarGraphSettings(
                        baseData,
                        modData,
                        enumLabels,
                        "Gender distribution",
                        "Original",
                        "Noisy",
                        "Gender",
                        "Frequency"
                );

                BarChart barChart = DataPlotManager.plotBarChart(barGraphSettings);

                String[] strModData = new String[randomGenderArr.length+1];

                strModData[0] = originalData[row][0];

                for(int i = 1 ; i < randomGenderArr.length+1;i++){
                    if(randomGenderArr[i-1] == 1){
                        strModData[i] = enumLabels[0];
                    } else {
                        strModData[i] = enumLabels[1];
                    }
                }

                //update the transfored data
                transformedData[row] = strModData;

                //update the tracker
                transformTracker[row] = true;

                previewScrollPane.setContent(barChart);

            }

        } else if(optionsVal.equals("Identifier")){
            //get the value in combobox
            String comboBoxOptionSelected = (String) options[row][1];

            if(comboBoxOptionSelected.equals("Obfuscate")){
                System.out.println("hide name");

                //add title back in
                transformedData[row][0] = originalData[row][0];

                for(int i=1;i<transformedData[row].length;i++){
                    transformedData[row][i] = "[*]";
                    System.out.println("Original :" + originalData[row][i] + " transformed: " + transformedData[row][i]);
                }
                transformTracker[row] = true;
            } else if(comboBoxOptionSelected.equals("Generate Novel")){
                System.out.println("fake name");
                for(int i=1;i<transformedData[row].length;i++){
                    //generate random name
                    //TODO violates gender dependancy
                    transformedData[row][i] = getRandomName(Math.random()<0.5);
                    System.out.println("Original :" + originalData[row][i] + " transformed: " + transformedData[row][i]);
                }
                transformTracker[row] = true;
            }
        }

        System.out.println(optionsVal);

    }

    private String getRandomName(boolean maleName){

        if(maleNames == null || femaleNames == null){

            maleNames = new String[250];
            femaleNames = new String[250];

            File maleList = new File("MaleFirstname.csv");
            File femaleList = new File("FemaleFirstname.csv");

            maleNames = loadNames(maleList,250);
            femaleNames = loadNames(femaleList,250);
        }

        if(maleName){
            return maleNames[ThreadLocalRandom.current().nextInt(0,maleNames.length)];
        } else {
            return femaleNames[ThreadLocalRandom.current().nextInt(0,femaleNames.length)];
        }
    }

    private String[] loadNames(File filename, int numRecords){

        String[] retArray = new String[numRecords];
        int count = 0;


        try {
            Scanner scanner = new Scanner(filename);

            while (count < numRecords && scanner.hasNext()){
                retArray[count] = scanner.next();
                count++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return retArray;
    }


    public void setComboBoxValues(String[][] comboBoxValues){
        this.comboBoxValues = comboBoxValues;
        initialiseOptionArray();
    }

    public void setInputFile(File inputFile){
        this.inputFile = inputFile;
    }

    private void initialiseOptionArray(){
        options = new Object[comboBoxValues[0].length][2];
    }

    public static HashMap<String,Integer> getEnumFrequency(String[] EnumData){

        HashMap<String,Integer> returnHashMap = new HashMap<>();

        HashSet<String> enumVals = new HashSet<>();

        for(String val: EnumData ){
            enumVals.add(val);
        }

        for(String value: enumVals){
            int freq = Collections.frequency(Arrays.asList(EnumData),value);
            returnHashMap.put(value,freq);
        }

        return returnHashMap;
    }

    public void generateDataHandler(){

        //check to make sure all data has been transformed
        System.out.println("here");

        //find all options
        int[] checkTransformations = new int[options.length];

        for(int i=0 ; i<checkTransformations.length; i++){
            if(options[i][0] != null){
                checkTransformations[i] = 1;
            } else {
                checkTransformations[i] = 0;
            }

        }

        boolean modCheck = true;

        for(int i=0;i<checkTransformations.length;i++){
            if(checkTransformations[i] == 1 && transformTracker[i] == false){
                modCheck = false;
                break;
            }
        }

        //Check that the file is selected
        if (modCheck && saveLocation != null){

            String fileName = "/newOutputFile.csv";
            File outputFile = new File(saveLocation.getAbsolutePath() + fileName);

            /*
            String [][] finalData = new String[transformedData[0].length][transformedData.length];

            for(int i=0;i<transformedData.length;i++){
                for(int j=0;j<transformedData[0].length;j++){
                    finalData[j][i] = transformedData[i][j];
                }
            }

            CsvFileProcessor.writeAllDatatoCSVFile(outputFile,finalData);
            */

            System.out.println("data saved!");

        }
    }

    private void writeDataToFile(File outputFile){

    }

    private void addSettingsRow(GridPane gridPane, int row){
        gridPane.add(new Text(comboBoxValues[0][row]),0,row+2);
        gridPane.add(generateOptionsNode(comboBoxValues[1][row],1,row+2),1,row+2);
        gridPane.add(generateButton(comboBoxValues[1][row]),2,row+2);
    }

    private Node generateOptionsNode(String dataType, int column, int row){
        if(dataType.equals("Identifier")){
            return generateComboBox(row);
        } else if(dataType.equals("Quasi-Identifier")) {
            Float initialValue = new Float(0.2);
            //add new value to options array
            options[row-2][0] = "StandardDev";
            options[row-2][1] = initialValue;
            return new TextField("0.2");
        } else {
            return new Text("");
        }
    }

    private Node generateComboBox(int row){

        ObservableList<String> optionsIdentifiers = FXCollections.observableArrayList(
                "Obfuscate","Generate Novel");
        ComboBox retComboBox = new ComboBox(optionsIdentifiers);
        retComboBox.getSelectionModel().select(0);
        options[row-2][0] = "Identifier";
        options[row-2][1] = retComboBox.getSelectionModel().getSelectedItem();

        retComboBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ComboBox comboBox = (ComboBox) event.getSource();
                int changeRow = modifyGridPane.getRowIndex(comboBox);

                options[changeRow-2][1] = comboBox.getSelectionModel().getSelectedItem();

            }
        });

        return retComboBox;
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

}

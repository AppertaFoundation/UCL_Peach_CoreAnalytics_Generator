package Presenter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class MainController {

    /* GUI Elements */
    public Button btnSelectFile;
    public Button btnSelectSaveLocation;
    public Button btnScrambler;
    public Button btnNextMain;
    public Button btnBackScrambler;
    public Button btnClose;

    public RadioButton rbkAnon;
    public RadioButton rbNoiseInjector;

    public TextArea txtAreaInputFile;
    public TextArea txtAreaOutputFolder;

    @FXML
    ToggleGroup rbTaskSelectionGroup;

    public String taskSelection = "";

    @FXML
    public void radioButtonStatus(ActionEvent event) throws Exception{
        //radio button selection
        if(rbkAnon.isSelected()){
            taskSelection = "GenerateNovel";
        } else if(rbNoiseInjector.isSelected()){
           taskSelection = "GenerateDataNoiseInjection";
        } 
    }

    @FXML
    public void navigationButtonClick(ActionEvent event) throws Exception {

        Stage stage;
        Parent root = null;

        /*initialise default size of window*/
        int width = 640;
        int height = 500;

        if (event.getSource() == btnNextMain) {

            stage = (Stage) btnNextMain.getScene().getWindow();

            switch (taskSelection) {
                case "GenerateDataScramble":
                    root = FXMLLoader.load(getClass().getResource("zzdelFolder/scramblerGUI.fxml"));
                    break;
                case "GenerateDataNoiseInjection":
                    root = FXMLLoader.load(getClass().getResource("../view/noiseInjectorGUImain.fxml"));
                    width = 1024;
                    height = 800;
                    break;
                case "GenerateNovel":
                    root = FXMLLoader.load(getClass().getResource("../view/kAnonDataInput.fxml"));
                    height = 600;
                    width = 640;
            }

            //only execute the change if root is not null
            if (root != null) {
                Scene scene = new Scene(root, width, height);
                stage.setScene(scene);
                stage.show();
            }

        }

    }

    /* exit program */
    public void closeBtnHandler(ActionEvent event){
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }

}

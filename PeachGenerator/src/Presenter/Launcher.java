package Presenter;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class Launcher extends Application {

    @Override
    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../view/mainGUI.fxml"));
        Scene scene = new Scene(root, 640,500);

        stage.setTitle("PEACH GENERATOR");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args){

        launch(args);
    }

}

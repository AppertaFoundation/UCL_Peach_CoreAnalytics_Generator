package Presenter;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import org.junit.Assert;
import org.junit.Test;
import org.loadui.testfx.GuiTest;

import java.io.IOException;

import static org.junit.Assert.*;

public class NoiseInjectorMainControllerTest extends GuiTest {


    @Override
    protected Parent getRootNode() {

            Parent parent = null;
        try {
            parent = FXMLLoader.load(getClass().getResource("../view/noiseInjectorGUImain.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return parent;

    }


    @Test
    public void testImportFile() throws Exception {

        Button btnBrowse = find("#btnBrowseFile");
        click(btnBrowse);

    }

}
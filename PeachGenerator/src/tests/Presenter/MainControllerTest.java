package Presenter;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.RadioButton;
import org.junit.Assert;
import org.junit.Test;
import org.loadui.testfx.GuiTest;

import java.io.IOException;

import org.loadui.testfx.GuiTest;

public class MainControllerTest extends GuiTest {

    /**
     * Test radio buttons on main page
     * @throws Exception
     */
    @Test
    public void testRadioButtonStatus() throws Exception {
        RadioButton generateNovelData = find("#rbkAnon");
        RadioButton anonData = find("#rbNoiseInjector");

        /* Check generate-data and only novel data button is selected */
        click(generateNovelData);
        Assert.assertTrue(generateNovelData.isSelected());
        Assert.assertFalse(anonData.isSelected());

        /* Check Anon-data and only anon data button is selected */
        click(anonData);
        Assert.assertFalse(generateNovelData.isSelected());
        Assert.assertTrue(anonData.isSelected());
    }

    @Override
    protected Parent getRootNode() {

        Parent parent = null;
        try {
            parent = FXMLLoader.load(getClass().getResource("../view/mainGUI.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return parent;
    }

}
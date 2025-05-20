package Core;

import java.io.IOException;

import Controller.ButtonController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

public class ButtonControl {

    public VBox container;
    public ButtonController buttonController;

    public void initialize() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/ButtonPane.fxml"));
        this.container = (VBox) loader.load();
        this.buttonController = loader.getController();
    }

}
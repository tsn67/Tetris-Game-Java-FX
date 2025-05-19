package Core;

import java.io.IOException;

import Controller.ScoreController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

public class ScoreWindow {

    public Pane scoreContainer;
    public ScoreController scoreController;

    public void initialize() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/ScorePane.fxml"));
        this.scoreContainer = (Pane) loader.load();
        this.scoreController = loader.getController();
    }

}

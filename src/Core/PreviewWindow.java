
package Core;

import java.io.IOException;

import Controller.PreviewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

public class PreviewWindow {

    public Pane containerPane = null;
    public PreviewController previewController = null;

    public void initialize() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Preview.fxml"));
        this.containerPane = (Pane) loader.load();
        this.previewController = loader.getController();
    }
}
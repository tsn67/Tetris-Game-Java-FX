package Core;

import java.io.IOException;
import Controller.ButtonContainerController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class ButtonHighlight {

    public ButtonContainerController buttonHighLightController;
    private AnchorPane buttonHighLightAnchorPane;

    public AnchorPane getButtonHighLightComponent() {
        return this.buttonHighLightAnchorPane;
    }

    public void initialize() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/HighLightButtons.fxml"));
        this.buttonHighLightAnchorPane = (AnchorPane) loader.load();
        this.buttonHighLightController = loader.getController();
    }

}

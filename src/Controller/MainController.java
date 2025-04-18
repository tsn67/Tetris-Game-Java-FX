package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Core.Grid;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;

public class MainController implements Initializable{
    
    @FXML
    private AnchorPane container;

    @FXML
    private FlowPane mainFlowPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        Grid gameGrid = new Grid();
        try {
            gameGrid.initialize();
            Node gridUIcomponent = gameGrid.getGridUI();
            this.mainFlowPane.setOrientation(Orientation.HORIZONTAL);
            this.mainFlowPane.setAlignment(Pos.CENTER);
            this.mainFlowPane.setHgap(10);
            this.mainFlowPane.getChildren().add(gridUIcomponent);
        } catch(IOException e) {
            System.out.println("Cannot load the grid pane! "+e.getMessage());
            return;
        }


        
    }

}

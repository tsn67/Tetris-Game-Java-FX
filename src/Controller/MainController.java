package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import Core.Grid;
import Utils.GameEngine;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;

public class MainController implements Initializable {

    @FXML
    private AnchorPane container;

    @FXML
    private FlowPane mainFlowPane;
    private Grid gameGrid;
    private GameOverLabelController gameOverLabel;

    /*
     * the function can only be implemented to a fxml component hence
     * implemented here, but function record keyevents and passes it to the Grid
     * 
     */

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        gameGrid = new Grid(this.container);

        try {
            gameGrid.initialize();
            Node gridUIcomponent = gameGrid.getGridUI();
            this.mainFlowPane.setOrientation(Orientation.HORIZONTAL);
            this.mainFlowPane.setAlignment(Pos.CENTER);
            this.mainFlowPane.setHgap(10);
            this.mainFlowPane.getChildren().add(gridUIcomponent);

            this.gameOverLabel = new GameOverLabelController(this.gameGrid);
            // just for testing
            // int[][] gridMatrix = gameGrid.getGrid();
            // gridMatrix[12][4] = 1;
            // gridMatrix[12][5] = 1;
            // gridMatrix[13][4] = 1;
            // gridMatrix[13][5] = 1;
            // gameGrid.updateGrid();

            GameEngine gameEngine = new GameEngine(gameGrid, gameOverLabel);
            gameEngine.startGame();

        } catch (IOException e) {
            System.out.println("Cannot load the grid pane! " + e.getMessage());
            return;
        }

        Platform.runLater(() -> container.requestFocus());

    }

}

package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Core.ButtonControl;
import Core.ButtonHighlight;
import Core.Grid;
import Core.PreviewWindow;
import Core.ScoreWindow;
import Utils.GameEngine;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

public class MainController implements Initializable {

    @FXML
    private AnchorPane container;

    @FXML
    private VBox leftContainer;

    @FXML
    private VBox rightContainer;

    @FXML
    private ButtonControl buttonControl;

    @FXML
    private FlowPane mainFlowPane;
    private Grid gameGrid;
    private GameOverLabelController gameOverLabel;
    private ButtonHighlight buttonHighLighter;
    private PreviewWindow previewWindow;
    private ScoreWindow scoreWindow;
    /*
     * the function can only be implemented to a fxml component hence
     * implemented here, but function record keyevents and passes it to the Grid
     * 
     */

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        gameGrid = new Grid(this.container);
        buttonHighLighter = new ButtonHighlight();
        previewWindow = new PreviewWindow();
        scoreWindow = new ScoreWindow();
        buttonControl = new ButtonControl();
        try {
            gameGrid.initialize();
            Node gridUIcomponent = gameGrid.getGridUI();
            this.mainFlowPane.setOrientation(Orientation.HORIZONTAL);
            this.mainFlowPane.setAlignment(Pos.CENTER);
            this.mainFlowPane.setHgap(10);
            this.mainFlowPane.getChildren().add(gridUIcomponent);

            // start button hightlight add to left stack pane
            buttonHighLighter.initialize();
            previewWindow.initialize();

            this.leftContainer.setAlignment(Pos.TOP_CENTER);
            this.leftContainer.getChildren().add(buttonHighLighter.getButtonHighLightComponent());

            this.rightContainer.setAlignment(Pos.TOP_CENTER);
            this.rightContainer.getChildren().add(previewWindow.containerPane);

            this.gameOverLabel = new GameOverLabelController(this.gameGrid);
            // just for testing
            // int[][] gridMatrix = gameGrid.getGrid();
            // gridMatrix[12][4] = 1;
            // gridMatrix[12][5] = 1;
            // gridMatrix[13][4] = 1;
            // gridMatrix[13][5] = 1;
            // gameGrid.updateGrid();

            scoreWindow.initialize();
            // add score window to right stackpane
            this.leftContainer.getChildren().add(scoreWindow.scoreContainer);

            buttonControl.initialize();
            this.rightContainer.getChildren().add(buttonControl.container);

            GameEngine gameEngine = new GameEngine(gameGrid, gameOverLabel,
                    buttonHighLighter.buttonHighLightController, previewWindow.previewController,
                    scoreWindow.scoreController, buttonControl.buttonController);
            gameEngine.startGame();

        } catch (IOException e) {
            System.out.println("Cannot load the grid pane! " + e.getMessage());
            return;
        }

        Platform.runLater(() -> container.requestFocus());

    }

}

package Controller;

import Core.Grid;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class GameOverLabelController {

    Grid grid;
    final String gameOverString = "Game Over!";
    Label label;
    StackPane overLayPane;

    public GameOverLabelController(Grid grid) {
        this.grid = grid;
        this.label = new Label(gameOverString);
        this.label.setFont(new Font("Arial", 40));
        this.label.setTextFill(Color.RED);

    }

    public void hideLabel() {
        this.overLayPane.setVisible(false);
    }

    public void showLabel() {
        this.overLayPane = new StackPane();
        this.overLayPane.setAlignment(Pos.CENTER);
        this.overLayPane.setPrefHeight(grid.getGridController().gridPaneHeight);
        this.overLayPane.setPrefWidth(grid.getGridController().gridPaneWidth);
        this.overLayPane.getChildren().add(this.label);
        this.overLayPane.setVisible(true);
        Pane tempPane = (Pane) this.grid.getGridUI(); // unsafe, will deal with it later ***
        tempPane.getChildren().add(this.overLayPane);
    }
}

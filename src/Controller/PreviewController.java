package Controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public class PreviewController implements Initializable {

    @FXML
    private Pane previewPane;

    public int[][] previewGrid;

    private int cellWidth = 30;
    private int cellHeight = 30;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        previewGrid = new int[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++)
                previewGrid[i][j] = 0;
        }
        this.initDraw();
    }

    public void initDraw() {
        this.previewPane.getChildren().clear();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                Rectangle rectangle = new Rectangle(j * cellHeight, i * cellWidth, cellHeight, cellWidth);
                rectangle.setFill(Color.BLACK);
                rectangle.setStroke(Color.web("#393E46"));
                rectangle.setStrokeWidth(1);
                this.previewPane.getChildren().add(rectangle);
            }
        }
    }

    public void redraw(int[][] grid, int colorValue) {
        String color = Assets.Tetrispiece.colors[colorValue - 1];
        this.initDraw();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 0)
                    continue;
                Rectangle rectangle = new Rectangle((j * cellHeight) + cellHeight, (i * cellWidth) + cellHeight,
                        cellHeight,
                        cellWidth);
                rectangle.setFill(Color.web(color));
                rectangle.setStroke(Color.BLACK);
                rectangle.setStrokeWidth(1);
                this.previewPane.getChildren().add(rectangle);
            }
        }

    }
}
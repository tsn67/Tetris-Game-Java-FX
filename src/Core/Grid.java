package Core;

import java.io.IOException;
import Controller.GridController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

public class Grid {

    /* - grid array representing the actual tetris grid */
    public int[][] grid;

    private GridController controller;
    private Node gridUiComponent;

    public AnchorPane mainContainer;

    public Grid(AnchorPane mainContainer) {
        grid = new int[23][10]; // 3 hidden rows to spawn each piece
        // initialize the grid to have 20 rows and 10 cols
        this.mainContainer = mainContainer;
    }

    public Grid(int rows, int cols) {
        grid = new int[rows][cols];
        // custome grid in case - future use
    }

    public GridController getGridController() {
        return this.controller;
    }

    public int[][] getGrid() {

        return this.grid;
    }

    public Node getGridUI() {
        return this.gridUiComponent;
    }

    public void clearGrid() {
        for (int i = 0; i < grid.length; i++)
            for (int j = 0; j < grid[0].length; j++)
                grid[i][j] = 0; // 0 -denoting the cell is empty
    }

    public void updateGrid() {
        // function just calls the ui component redraw without any processing
        controller.reDrawGrid(grid);
    }

    public void clearRow(int row) {
        for (int j = 0; j < grid[0].length; j++) {
            grid[row][j] = 0;
        }
    }

    public void initialize() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Grid.fxml"));
        gridUiComponent = loader.load();
        controller = loader.getController();
        controller.initDraw(grid);
        // controller.reDrawGrid(grid);
    }

    public void clearEntireGrid() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                grid[i][j] = 0;
            }
        }
    }
}

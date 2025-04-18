package Core;

import java.io.IOException;
import Controller.GridController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

public class Grid {
    
    /* - grid array representing the actual tetris grid */
    int[][] grid; 

    private GridController controller;
    private Node gridUiComponent;

    public Grid() {
        grid = new int[20][10];
        //initialize the grid to have 20 rows and 10 cols
    }

    public Grid(int rows, int cols) {
        grid = new int[rows][cols];
        //custome grid in case - future use  
    }

    public int[][] getGrid() {
        return this.grid;
    }

    public Node getGridUI() {
        return this.gridUiComponent;
    }

   
    public void updateGrid() {

    }

    public void initialize() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Grid.fxml"));
        gridUiComponent = loader.load();
        controller = loader.getController();
        controller.initDraw(grid);
    }

}

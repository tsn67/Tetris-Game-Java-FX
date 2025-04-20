package Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import Assets.Tetrispiece;

public class GridController implements Initializable{
    
    @FXML
    private Pane gridPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

    //pane height and widht - won't change, constant
    private final int gridPaneHeight = 500;
    private final int gridPaneWidth = 250; 
    private final Color defaultFillColor = Color.BLACK;
    private final Color StrokeColor = Color.GREY;

    public void initDraw(int[][] currentGrid) {
        
        //current rectable refernces must be cleared
        gridPane.getChildren().clear();
        for(int i = 0;i < currentGrid.length;i++) {
            if(i < 3) continue;
            for(int j = 0;j < currentGrid[0].length;j++) {
                Rectangle rect = new Rectangle(j * 25, (i - 3) * 25, 25, 25); //i - 3 performed to avoide first 3 hidden rows
                rect.setFill(defaultFillColor);
                rect.setStroke(StrokeColor);
                rect.setStrokeWidth(1);

                this.gridPane.getChildren().add(rect);
            }
        }

    }

    public void reDrawGrid(int[][] currentGrid) {
        
        //current rectable refernces must be cleared
        gridPane.getChildren().clear();

        /*
         * function will redraw the grid based on the currentGrid matrix, do not perform any control activity
         * if grid cell value = 0, fill default color
         * else get the TetrisPiece.colors[cellValue - 1] color , as web.color() - in #color value
         * the function is costly, so handle the situations that uses this method
         */

        for(int i = 0;i < currentGrid.length;i++) {
            if(i < 3) continue; 
            for(int j = 0;j < currentGrid[0].length;j++) {
                Rectangle rect = new Rectangle(j * 25, (i - 3) * 25, 25, 25); //i - 3 performed to avoide first 3 hidden rows
                if(currentGrid[i][j] == 0) {
                    rect.setFill(defaultFillColor);
                    rect.setStroke(StrokeColor);
                    rect.setStrokeWidth(1);
                   //System.out.println("hello");
                } else {
                    
                    //get the color value
                    String hexColor = Tetrispiece.colors[currentGrid[i][j] - 1];
                    rect.setFill(Color.web(hexColor));
                    rect.setStroke(Color.BLACK);
                    rect.setStrokeWidth(1);     
                }
                
                this.gridPane.getChildren().add(rect);
            }
        }
        
    }

}

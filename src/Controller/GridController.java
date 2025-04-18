package Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GridController implements Initializable{
    
    @FXML
    private Pane gridPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

    //pane height and widht - won't change, constant
    private final int height = 500;
    private final int width = 250; 

    public void initDraw(int[][] currentGrid) {

        for(int i = 0;i < currentGrid.length;i++) {
            for(int j = 0;j < currentGrid[0].length;j++) {
                Rectangle rect = new Rectangle(j * 25, i * 25, 25, 25);
                rect.setFill(Color.TRANSPARENT);
                rect.setStroke(Color.GREY);
                rect.setStrokeWidth(1);

                this.gridPane.getChildren().add(rect);
            }
        }

    }

}

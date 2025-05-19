package Controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class ScoreController implements Initializable {

    @FXML
    private Label scoreLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resetScore();
    }

    public int currentScore = 0;

    public void resetScore() {
        this.currentScore = 0;
        this.scoreLabel.setText("00");
    }

    public void increment() {
        this.currentScore++;
        String text = String.valueOf(currentScore);
        if (text.length() < 2) {
            text = "0" + text;
        }
        this.scoreLabel.setText(text);
    }
}
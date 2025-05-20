package Controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class ButtonController implements Initializable {

    @FXML
    private Button pauseButton;

    @FXML
    private Button restartButton;

    private String defaultStyleString = "-fx-background-color: #4E6688; ";
    private String hoverStyleString = "-fx-background-color: #332D56";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    private void buttonHover(MouseEvent e) {
        Button button = (Button) e.getSource();
        button.setStyle(hoverStyleString);
    }

    @FXML
    private void buttonHoverExit(MouseEvent e) {
        Button button = (Button) e.getSource();
        button.setStyle(defaultStyleString);
    }

    public Button getRestartButton() {
        return this.restartButton;
    }

    public Button getPauseButton() {
        return this.pauseButton;
    }

    public void changeButtonText(String text) {
        this.pauseButton.setText(text);
    }
}

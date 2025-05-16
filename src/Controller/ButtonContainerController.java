package Controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class ButtonContainerController implements Initializable {

    @FXML
    private Button aButton;

    @FXML
    private Button dButton;

    @FXML
    private Button sButton;

    @FXML
    private Button wButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // no use
    }

    private Color initColor = Color.BLACK;
    private Color finalColor = Color.web("#7d7c78");

    private void buttonAnimationPlay(Button button) {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(button.backgroundProperty(),
                                new Background(new BackgroundFill(finalColor, CornerRadii.EMPTY, null)))),
                new KeyFrame(Duration.millis(30),
                        new KeyValue(button.backgroundProperty(),
                                new Background(new BackgroundFill(initColor, CornerRadii.EMPTY, null)))));

        timeline.play();
        timeline.setOnFinished((e) -> {
            button.setBackground(new Background(new BackgroundFill(initColor,
                    CornerRadii.EMPTY, null)));
        });
    }

    public void buttonFadeInOutAnimation(String button) {
        switch (button) {
            case "w":
                buttonAnimationPlay(wButton);
                break;
            case "s":
                buttonAnimationPlay(sButton);
                break;
            case "d":
                buttonAnimationPlay(dButton);
                break;
            case "a":
                buttonAnimationPlay(aButton);
                break;
            default:
                throw new Error("Invalid button!");
        }
    }

}
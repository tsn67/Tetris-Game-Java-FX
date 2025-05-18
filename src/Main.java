import Controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private final int minScreenWidth = 1024;
    private final int minScreenHeight = 600;

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("./FXML/Main.fxml"));
        Parent root = loader.load();

        MainController mainController = loader.getController();
        // main contoller - game controller
        stage.setMinHeight(this.minScreenHeight);
        stage.setMinWidth(this.minScreenWidth);

        Scene scene = new Scene(root);
        stage.setTitle("Tetris - JAVA FX");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}

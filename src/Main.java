import Controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{
    
    @Override
    public void start(Stage stage) throws Exception {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("./FXML/Main.fxml"));
        Parent root = loader.load();

        MainController mainController = loader.getController();
        //main contoller - game controller

        Scene scene = new Scene(root);
        stage.setTitle("Tetris - JAVA FX");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);        
    }
}

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.application.Application;



public class App extends Application {
    public static void main(String[] args) throws Exception {
        launch(args);
    }


    @Override
    public void start(Stage PrimaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/login.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        PrimaryStage.setScene(scene);
        PrimaryStage.show();
    
    }


}



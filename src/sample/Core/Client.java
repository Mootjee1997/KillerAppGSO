package sample.Core;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import sample.Server.Server;


public class Client extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Server server = new Server();
        primaryStage.getIcons().add(new Image("http://zwartwitmedia.com/wp-content/uploads/2014/12/logo-bibliotheek.jpg"));
        Parent root = FXMLLoader.load(getClass().getResource("/sample/JavaFX/Hoofdscherm.fxml"));
        primaryStage.setTitle("Bibliotheek Eindhoven Client");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

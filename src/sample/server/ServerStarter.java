package sample.server;
import javafx.application.Application;
import javafx.stage.Stage;

public class ServerStarter extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        new Server();
    }
}

package sample.core;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SceneHandler {
    private Logger logger = Logger.getLogger("Logger");
    private Parent scherm;

    public void loadScherm(Parent scherm, ActionEvent e) {
        try {
            this.scherm = scherm;
            showWindow(e);
        }
        catch (Exception ex) {
            logger.log( Level.WARNING, ex.toString(), ex);
        }
    }
    public void showWindow(ActionEvent e){
        Scene gebruikersscherm = new Scene(scherm);
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(gebruikersscherm);
        stage.show();
    }
}

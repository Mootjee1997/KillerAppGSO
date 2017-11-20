package sample.JavaFX;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Core.AppManager;
import javafx.event.ActionEvent;

public class GebruikerController {
    AppManager appManager = new AppManager();
    Parent scherm;

    @FXML private TextField tbGebruikersnaam;
    @FXML private TextField tbWachtwoord;
    @FXML private Button btnLogin;
    @FXML private Button btnRegistreer;

    @FXML public void login(ActionEvent e) throws Exception {
//        if (appManager.login(tbGebruikersnaam.getText(), tbWachtwoord.getText()).getStatus() == "Medewerker") {
//            scherm = FXMLLoader.load(getClass().getResource("Gebruikersscherm (Medewerker).fxml"));
//        }
//        else scherm =
        scherm = FXMLLoader.load(getClass().getResource("Gebruikersscherm (Klant).fxml"));

        Scene gebruikersscherm = new Scene(scherm);
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(gebruikersscherm);
        stage.show();
    }
}

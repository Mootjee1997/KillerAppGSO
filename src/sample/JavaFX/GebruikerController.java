package sample.JavaFX;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sample.Core.AppManager;
import javafx.event.ActionEvent;
import sample.Models.Gebruiker;

public class GebruikerController {
    Parent scherm;
    @FXML private Label lblmessage, lblmessageR, lblmessageP;
    @FXML private TextField tbGebruikersnaam, tbWachtwoord, tbWachtwoordP,tbNaamP,tbWoonplaatsP, tbEmailP, tbTelefoonNrP, tbGebruikersnaamR, tbWachtwoordR, tbNaamR,tbWoonplaatsR, tbEmailR, tbTelefoonNrR;

    public boolean loginUser(String gebruikersnaam, String wachtwoord) throws Exception {
        if (AppManager.login(gebruikersnaam, wachtwoord).getStatus().equals("Medewerker")) {
            scherm = FXMLLoader.load(getClass().getResource("Gebruikersscherm (Medewerker).fxml"));
            return true;
        }
        else if (AppManager.login(gebruikersnaam, wachtwoord).getStatus().equals("Klant")) {
            scherm = FXMLLoader.load(getClass().getResource("Gebruikersscherm (Klant).fxml"));
            return true;
        }
        return false;
    }

    public void showWindow(ActionEvent e){
        Scene gebruikersscherm = new Scene(scherm);
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(gebruikersscherm);
        stage.show();
    }

    @FXML
    public void initialize() {
        if (tbNaamP != null && tbWoonplaatsP != null && tbEmailP != null && tbTelefoonNrP != null) {
            tbNaamP.setText(AppManager.getGebruiker().getNaam());
            tbWoonplaatsP.setText(AppManager.getGebruiker().getWoonplaats());
            tbEmailP.setText(AppManager.getGebruiker().getEmail());
            tbTelefoonNrP.setText(AppManager.getGebruiker().getTelefoonNr());
        }
    }

    @FXML public void login(ActionEvent e) throws Exception {
        if (!tbGebruikersnaam.getText().trim().equals("") && !tbWachtwoord.getText().trim().equals("")) {
            if (loginUser(tbGebruikersnaam.getText(), tbWachtwoord.getText())){
                showWindow(e);
            }
            else {
                lblmessage.setTextFill(Color.RED);
                lblmessage.setText("Gebruikersnaam en/of wachtwoord komen niet overeen.");
            }
        }
        else {
            lblmessage.setTextFill(Color.RED);
            lblmessage.setText("Vul alle velden in aub.");
        }
    }

    @FXML public void registreer(ActionEvent e) throws Exception {
        if (!tbGebruikersnaamR.getText().trim().equals("") && !tbWachtwoordR.getText().trim().equals("") && !tbNaamR.getText().trim().equals("") && !tbWoonplaatsR.getText().trim().equals("")) {
            AppManager.registreer(new Gebruiker(tbGebruikersnaamR.getText(), tbWachtwoordR.getText(), tbNaamR.getText(), tbEmailR.getText(), tbWoonplaatsR.getText(), tbTelefoonNrR.getText()));
            loginUser(tbGebruikersnaamR.getText(), tbWachtwoordR.getText());
            showWindow(e);
        }
        else {
            lblmessageR.setTextFill(Color.RED);
            lblmessageR.setText("Vul alle velden in aub.");
        }
    }

    @FXML public void wijzigProfiel(ActionEvent e) throws Exception {
        if (!tbWachtwoordP.getText().trim().equals("") && !tbNaamP.getText().trim().equals("") && !tbEmailP.getText().trim().equals("")) {
            if (AppManager.getGebruiker().wijzigGegevens(tbWachtwoordP.getText(), tbNaamP.getText(), tbEmailP.getText(), tbWoonplaatsP.getText(), tbTelefoonNrP.getText())) {
                lblmessageP.setTextFill(Color.GREEN);
                lblmessageP.setText("Gegevens succesvol gewijzigd");
            }
        }
        else {
            lblmessageP.setTextFill(Color.RED);
            lblmessageP.setText("Vul alle benodigde velden in aub.");
        }
    }

    @FXML public void loguit(ActionEvent e) throws Exception {
        AppManager.loguit();
        scherm = FXMLLoader.load(getClass().getResource("Hoofdscherm.fxml"));
        showWindow(e);
    }

    @FXML public void openregistreerScherm(ActionEvent e) throws Exception {
        scherm = FXMLLoader.load(getClass().getResource("Registreerscherm.fxml"));
        showWindow(e);
    }

    @FXML public void openReturnScherm(ActionEvent e) throws Exception {
        scherm = FXMLLoader.load(getClass().getResource("Hoofdscherm.fxml"));
        showWindow(e);
    }
}

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
import sample.Models.Gegevens;

public class GebruikerController {
    Parent scherm;
    AppManager appManager = AppManager.getInstance();
    @FXML private Label lblmessage, lblmessageR, lblmessageP, lblIngelogdAls;
    @FXML private TextField tbGebruikersnaam, tbWachtwoord, tbWachtwoordP,tbNaamP,tbWoonplaatsP, tbEmailP, tbTelefoonNrP, tbGebruikersnaamR, tbWachtwoordR, tbNaamR,tbWoonplaatsR, tbEmailR, tbTelefoonNrR;

    public boolean loginUser(String gebruikersnaam, String wachtwoord) throws Exception {
        if (appManager.login(gebruikersnaam, wachtwoord) != null)
        if (appManager.getGebruiker().getStatus().equals("Medewerker")) {
            scherm = FXMLLoader.load(getClass().getResource("MijnProfiel (Medewerker).fxml"));
            return true;
        }
        else if (appManager.getGebruiker().getStatus().equals("Klant")) {
            scherm = FXMLLoader.load(getClass().getResource("MijnProfiel (Klant).fxml"));
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

    @FXML public void initialize() throws Exception {
        appManager.getBoeken();
        appManager.getGebruiker();
        if (tbNaamP != null && tbWoonplaatsP != null && tbEmailP != null && tbTelefoonNrP != null && lblmessageP != null) {
            tbNaamP.setText(appManager.getGebruiker().getGegevens().getNaam());
            tbWoonplaatsP.setText(appManager.getGebruiker().getGegevens().getWoonplaats());
            tbEmailP.setText(appManager.getGebruiker().getGegevens().getEmail());
            tbTelefoonNrP.setText(appManager.getGebruiker().getGegevens().getTelefoonNr());
            lblIngelogdAls.setText("Ingelogd als: " + appManager.getGebruiker().getGebruikersnaam());
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
            appManager.registreer(new Gebruiker(tbGebruikersnaamR.getText(), tbWachtwoordR.getText(), new Gegevens(tbNaamR.getText(), tbEmailR.getText(), tbWoonplaatsR.getText(), tbTelefoonNrR.getText())));
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
            if (appManager.getGebruiker().wijzigGegevens(tbWachtwoordP.getText(), new Gegevens(tbNaamP.getText(), tbEmailP.getText(), tbWoonplaatsP.getText(), tbTelefoonNrP.getText()))) {
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
        appManager.loguit();
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

    @FXML public void openMijnBoeken(ActionEvent e) throws Exception {
        scherm = FXMLLoader.load(getClass().getResource("MijnBoeken (Klant).fxml"));
        showWindow(e);
    }

    public GebruikerController() throws Exception {
    }
}

package sample.JavaFX;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sample.Core.AppManager;
import javafx.event.ActionEvent;
import sample.Models.Gebruiker;
import sample.Models.Gegevens;

public class GebruikerController {
    private Parent scherm;
    private AppManager appManager = AppManager.getInstance();
    private Gebruiker gebruiker = appManager.getGebruiker();

    @FXML private PasswordField tbWachtwoord, tbPasswordVerif;
    @FXML private Label lblmessage, lblmessageR, lblmessageP, lblIngelogdAls;
    @FXML private TextField tbGebruikersnaam, tbNaam,tbWoonplaats, tbEmail, tbTelefoonNr, tbWachtwoordVisible;

    public void showWindow(ActionEvent e){
        Scene gebruikersscherm = new Scene(scherm);
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(gebruikersscherm);
        stage.show();
    }

    @FXML public void initialize() throws Exception {
        if (tbNaam != null && tbWoonplaats != null && tbEmail != null && tbTelefoonNr != null && lblmessageP != null) {
            tbNaam.setText(gebruiker.getGegevens().getNaam());
            tbWoonplaats.setText(gebruiker.getGegevens().getWoonplaats());
            tbEmail.setText(gebruiker.getGegevens().getEmail());
            tbTelefoonNr.setText(gebruiker.getGegevens().getTelefoonNr());
            lblIngelogdAls.setText("Ingelogd als: " + gebruiker.getGebruikersnaam());
        }
    }

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
        if (!tbGebruikersnaam.getText().trim().equals("") && !tbWachtwoord.getText().trim().equals("") && !tbNaam.getText().trim().equals("") && !tbWoonplaats.getText().trim().equals("")) {
            appManager.registreer(new Gebruiker(tbGebruikersnaam.getText(), tbWachtwoord.getText(), new Gegevens(tbNaam.getText(), tbEmail.getText(), tbWoonplaats.getText(), tbTelefoonNr.getText())));
            loginUser(tbGebruikersnaam.getText(), tbWachtwoord.getText());
            showWindow(e);
        }
        else {
            lblmessageR.setTextFill(Color.RED);
            lblmessageR.setText("Vul alle velden in aub.");
        }
    }

    @FXML public void wijzigProfiel(ActionEvent e) throws Exception {
        if (tbPasswordVerif.getText().equals(appManager.getGebruiker().getWachtwoord())) {
            if (!tbWachtwoord.getText().trim().equals("") && !tbNaam.getText().trim().equals("") && !tbEmail.getText().trim().equals("")) {
                if (appManager.getGebruiker().wijzigGegevens(tbWachtwoord.getText(), new Gegevens(tbNaam.getText(), tbEmail.getText(), tbWoonplaats.getText(), tbTelefoonNr.getText()))) {
                    lblmessageP.setTextFill(Color.GREEN);
                    lblmessageP.setText("Gegevens succesvol gewijzigd.");
                    tbWachtwoord.setText("");
                }
            } else {
                lblmessageP.setTextFill(Color.RED);
                lblmessageP.setText("Vul alle benodigde velden in aub.");
            }
        }
        else {
            lblmessageP.setTextFill(Color.RED);
            lblmessageP.setText("Huidige wachtwoord klopt niet.");
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
    @FXML public void openMijnProfiel(ActionEvent e) throws Exception {
        if (gebruiker.getStatus().equals("Medewerker")) {
            scherm = FXMLLoader.load(getClass().getResource("MijnProfiel (Medewerker).fxml"));
        }
        else scherm = FXMLLoader.load(getClass().getResource("MijnProfiel (Klant).fxml"));
        showWindow(e);
    }
    @FXML public void openMijnBoeken(ActionEvent e) throws Exception {
        scherm = FXMLLoader.load(getClass().getResource("MijnBoeken (Klant).fxml"));
        showWindow(e);
    }
    @FXML public void openGebruikerslijst(ActionEvent e) throws Exception {
        scherm = FXMLLoader.load(getClass().getResource("GebruikersLijst (Medewerker).fxml"));
        showWindow(e);
    }
    @FXML public void openBoekenLijst(ActionEvent e) throws Exception {
        if (appManager.getGebruiker().getStatus().equals("Medewerker")) {
            scherm = FXMLLoader.load(getClass().getResource("Boekenlijst (Medewerker).fxml"));
        }
        else scherm = FXMLLoader.load(getClass().getResource("Boekenlijst (Klant).fxml"));
        showWindow(e);
    }
    @FXML public void openBoekToevoegen(ActionEvent e) throws Exception {
        scherm = FXMLLoader.load(getClass().getResource("BoekToevoegen (Medewerker).fxml"));
        showWindow(e);
    }
    @FXML public void showPassword() throws Exception {
        if (tbWachtwoordVisible.isVisible() == false) {
            tbWachtwoord.setVisible(false);
            tbWachtwoordVisible.setVisible(true);
            tbWachtwoordVisible.setText(tbWachtwoord.getText());
        }
        else {
            tbWachtwoord.setVisible(true);
            tbWachtwoordVisible.setVisible(false);
        }
    }
    public GebruikerController() throws Exception {
    }
}

package sample.JavaFX;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sample.Core.AppManager;
import javafx.event.ActionEvent;
import sample.Enums.Kleur;
import sample.Enums.Status;
import sample.Models.Gebruiker;
import sample.Models.Gegevens;

public class GebruikerController {
    private Parent scherm;
    private AppManager appManager = AppManager.getInstance();
    private Gebruiker gebruiker = appManager.getGebruiker();

    @FXML private Button btnProfielWijzigen;
    @FXML private PasswordField tbWachtwoord, tbPasswordVerif;
    @FXML private Label lblmessage, lblIngelogdAls;
    @FXML private TextField tbGebruikersnaam, tbNaam,tbWoonplaats, tbEmail, tbTelefoonNr, tbWachtwoordVisible;

    public boolean loginUser(String gebruikersnaam, String wachtwoord) throws Exception {
        if (appManager.login(gebruikersnaam, wachtwoord) != null)
            if (appManager.getGebruiker().getStatus() == Status.MEDEWERKER) {
                scherm = FXMLLoader.load(getClass().getResource("MijnProfiel (Medewerker).fxml"));
                return true;
            }
            else if (appManager.getGebruiker().getStatus() == Status.KLANT) {
                scherm = FXMLLoader.load(getClass().getResource("MijnProfiel (Klant).fxml"));
                return true;
            }
        return false;
    }

    public void login(ActionEvent e) throws Exception {
        if (!tbGebruikersnaam.getText().trim().equals("") && !tbWachtwoord.getText().trim().equals("")) {
            if (loginUser(tbGebruikersnaam.getText(), tbWachtwoord.getText())) {
                showWindow(e);
            }
            else showMessage(Kleur.RED, "Gebruikersnaam en/of wachtwoord komen niet overeen.");
        }
        else showMessage(Kleur.RED, "Vul alle benodigde velden in aub.");
    }

    public void registreer(ActionEvent e) throws Exception {
        if (!tbGebruikersnaam.getText().trim().equals("") && !tbWachtwoord.getText().trim().equals("") && !tbNaam.getText().trim().equals("") && !tbWoonplaats.getText().trim().equals("")) {
            appManager.registreer(new Gebruiker(tbGebruikersnaam.getText(), tbWachtwoord.getText(), new Gegevens(tbNaam.getText(), tbEmail.getText(), tbWoonplaats.getText(), tbTelefoonNr.getText())));
            loginUser(tbGebruikersnaam.getText(), tbWachtwoord.getText());
            showWindow(e);
        }
        else showMessage(Kleur.RED, "Vul alle benodigde velden in aub.");
    }

    public void wijzigProfiel(ActionEvent e) throws Exception {
        if (tbPasswordVerif.getText().equals(appManager.getGebruiker().getWachtwoord())) {
            if (!tbPasswordVerif.getText().trim().equals("") && !tbNaam.getText().trim().equals("") && !tbEmail.getText().trim().equals("")) {
                if (!tbWachtwoord.getText().equals("")) {
                    appManager.getGebruiker().wijzigGegevens(tbWachtwoord.getText(), new Gegevens(tbNaam.getText(), tbEmail.getText(), tbWoonplaats.getText(), tbTelefoonNr.getText()));
                }
                else {
                    appManager.getGebruiker().wijzigGegevens(new Gegevens(tbNaam.getText(), tbEmail.getText(), tbWoonplaats.getText(), tbTelefoonNr.getText()));
                }
                showMessage(Kleur.GREEN, "Gegevens succesvol gewijzigd.");
                tbWachtwoord.setText("");
                tbPasswordVerif.setText("");
            }
            else showMessage(Kleur.RED, "Vul alle benodigde velden in aub.");
        }
        else showMessage(Kleur.RED, "Huidig wachtwoord klopt niet.");
    }

    public void showWindow(ActionEvent e){
        Scene gebruikersscherm = new Scene(scherm);
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(gebruikersscherm);
        stage.show();
    }
    public void initialize() throws Exception {
        appManager.gebruikerController = this;
        if (tbNaam != null && tbWoonplaats != null && tbEmail != null && tbTelefoonNr != null && btnProfielWijzigen != null) {
            tbNaam.setText(gebruiker.getGegevens().getNaam());
            tbWoonplaats.setText(gebruiker.getGegevens().getWoonplaats());
            tbEmail.setText(gebruiker.getGegevens().getEmail());
            tbTelefoonNr.setText(gebruiker.getGegevens().getTelefoonNr());
            lblIngelogdAls.setText("Ingelogd als: " + gebruiker.getGebruikersnaam());
        }
    }
    public void loguit(ActionEvent e) throws Exception {
        appManager.loguit();
        scherm = FXMLLoader.load(getClass().getResource("Hoofdscherm.fxml"));
        showWindow(e);
    }
    public void openregistreerScherm(ActionEvent e) throws Exception {
        scherm = FXMLLoader.load(getClass().getResource("Registreerscherm.fxml"));
        showWindow(e);
    }
    public void openReturnScherm(ActionEvent e) throws Exception {
        scherm = FXMLLoader.load(getClass().getResource("Hoofdscherm.fxml"));
        showWindow(e);
    }
    public void openMijnProfiel(ActionEvent e) throws Exception {
        if (gebruiker.getStatus() == Status.MEDEWERKER) {
            scherm = FXMLLoader.load(getClass().getResource("MijnProfiel (Medewerker).fxml"));
        }
        else scherm = FXMLLoader.load(getClass().getResource("MijnProfiel (Klant).fxml"));
        showWindow(e);
    }
    public void openMijnBoeken(ActionEvent e) throws Exception {
        scherm = FXMLLoader.load(getClass().getResource("MijnBoeken (Klant).fxml"));
        showWindow(e);
    }
    public void openGebruikerslijst(ActionEvent e) throws Exception {
        scherm = FXMLLoader.load(getClass().getResource("GebruikersLijst (Medewerker).fxml"));
        showWindow(e);
    }
    public void openBoekenLijst(ActionEvent e) throws Exception {
        if (appManager.getGebruiker().getStatus() == Status.MEDEWERKER) {
            scherm = FXMLLoader.load(getClass().getResource("Boekenlijst (Medewerker).fxml"));
        }
        else scherm = FXMLLoader.load(getClass().getResource("Boekenlijst (Klant).fxml"));
        showWindow(e);
    }
    public void openBoekToevoegen(ActionEvent e) throws Exception {
        scherm = FXMLLoader.load(getClass().getResource("BoekToevoegen (Medewerker).fxml"));
        showWindow(e);
    }
    public void showMessage(Kleur kleur, String message) throws Exception {
        if (kleur == Kleur.RED) lblmessage.setTextFill(Color.RED);
        if (kleur == Kleur.GREEN) lblmessage.setTextFill(Color.GREEN);
        lblmessage.setText(message);
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        Platform.runLater(() -> lblmessage.setText(""));
                    }
                },
                5000
        );
    }
    public void showPassword() throws Exception {
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

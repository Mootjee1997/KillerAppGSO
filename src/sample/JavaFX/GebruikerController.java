package sample.JavaFX;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sample.Core.AppManager;
import javafx.event.ActionEvent;
import sample.Enums.Kleur;
import sample.Enums.Status;
import sample.Models.Gebruiker;
import sample.Models.Gegevens;
import java.io.*;
import java.util.Properties;

public class GebruikerController {
    private Parent scherm;
    private AppManager appManager = AppManager.getInstance();
    private Gebruiker gebruiker = appManager.getGebruiker();
    private boolean voteGebruikersnaam, voteWachtwoord, voteEmail, voteNaam, voteHuidigWachtwoord;

    @FXML public CheckBox cbOnthoudtGebruikersnaam;
    @FXML public Button btnProfielWijzigen, btnRegistreer, btnLogin;
    @FXML public PasswordField tbWachtwoord, tbPasswordVerif;
    @FXML public Label lblmessage, lblIngelogdAls;
    @FXML public TextField tbGebruikersnaam, tbNaam,tbWoonplaats, tbEmail, tbTelefoonNr, tbWachtwoordVisible;

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
                saveProperties();
                showWindow(e);
            }
            else showMessage(Kleur.RED, "Gebruikersnaam en/of wachtwoord komen niet overeen.", 4000);
        }
        else showMessage(Kleur.RED, "Vul alle benodigde velden in aub.", 4000);
    }

    public void registreer(ActionEvent e) throws Exception {
        appManager.registreer(new Gebruiker(tbGebruikersnaam.getText(), tbWachtwoord.getText(), new Gegevens(tbNaam.getText(), tbEmail.getText(), tbWoonplaats.getText(), tbTelefoonNr.getText())));
        loginUser(tbGebruikersnaam.getText(), tbWachtwoord.getText());
        showWindow(e);
    }

    public void wijzigProfiel(ActionEvent e) throws Exception {
        if (!tbWachtwoord.getText().equals("")) {
            appManager.getGebruiker().wijzigGegevens(tbWachtwoord.getText(), new Gegevens(tbNaam.getText(), tbEmail.getText(), tbWoonplaats.getText(), tbTelefoonNr.getText()));
        } else {
            appManager.getGebruiker().wijzigGegevens(new Gegevens(tbNaam.getText(), tbEmail.getText(), tbWoonplaats.getText(), tbTelefoonNr.getText()));
        }
        showMessage(Kleur.GREEN, "Gegevens succesvol gewijzigd.", 4000);
        tbWachtwoord.setText(""); tbPasswordVerif.setText("");
    }

    public void initialize() throws Exception {
        loadProperties();
        startListeners();
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
    public void showWindow(ActionEvent e){
        Scene gebruikersscherm = new Scene(scherm);
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(gebruikersscherm);
        stage.show();
    }
    public void showMessage(Kleur kleur, String message, int ms) {
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
                ms
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
    public void startListeners() throws Exception {
        String green = "-fx-border-color: limegreen ; -fx-border-width: 2px ;";
        String red = "-fx-border-color: red ; -fx-border-width: 2px ;";
        String trans = "-fx-border-color: transparent ; -fx-border-width: 2px ;";
        if (btnRegistreer != null || btnProfielWijzigen != null) {
            if (tbGebruikersnaam != null && lblmessage != null) {
                tbGebruikersnaam.textProperty().addListener((observable, oldValue, newValue) -> Platform.runLater(() -> {
                    try {
                        if (tbGebruikersnaam.getText().length() > 0) {
                            if (btnRegistreer != null && appManager.zoekGebruiker(tbGebruikersnaam.getText().toLowerCase()) != null) {
                                if (lblmessage.getText().equals("")) {
                                    showMessage(Kleur.RED, "Gebruikersnaam al in gebruik.", 2500);
                                    tbGebruikersnaam.setStyle(red);
                                }
                                disable(1);
                            } else {
                                lblmessage.setText("");
                                tbGebruikersnaam.setStyle(green);
                                enable(1);
                            }
                        } else {
                            tbGebruikersnaam.setStyle(trans);
                            disable(1);
                        }
                    } catch (Exception e) { }
                }));
            }
            if (tbWachtwoord != null && lblmessage != null) {
                tbWachtwoord.textProperty().addListener((observable, oldValue, newValue) -> Platform.runLater(() -> {
                    if (tbWachtwoord.getText().length() > 0) {
                        if (tbWachtwoord.getText().length() < 8) {
                            if (lblmessage.getText().equals("")) {
                                showMessage(Kleur.RED, "Probeer minimaal 8 tekens.", 2500);
                                tbWachtwoord.setStyle(red);
                            }
                            disable(2);
                        } else {
                            lblmessage.setText("");
                            tbWachtwoord.setStyle(green);
                            enable(2);
                        }
                    } else {
                        tbWachtwoord.setStyle(trans);
                        disable(2);
                    }
                }));
            }
            if (tbNaam != null && lblmessage != null) {
                tbNaam.textProperty().addListener((observable, oldValue, newValue) -> Platform.runLater(() -> {
                    if (tbNaam.getText().length() > 0) {
                        if (tbNaam.getText().length() > 4 && tbNaam.getText().contains(" ") && !tbNaam.getText().substring(tbNaam.getLength() - 1).contains(" ")) {
                            tbNaam.setStyle(green);
                            enable(3);
                        } else {
                            showMessage(Kleur.RED, "Voer aub uw voor én achternaam in", 2500);
                            tbNaam.setStyle(red);
                            disable(3);
                        }
                    } else {
                        tbNaam.setStyle(trans);
                        disable(3);
                    }
                }));
            }
            if (tbEmail != null && lblmessage != null) {
                tbEmail.textProperty().addListener((observable, oldValue, newValue) -> Platform.runLater(() -> {
                    if (tbEmail.getText().length() > 0) {
                        if (!tbEmail.getText().contains("@") || !tbEmail.getText().contains(".") || tbEmail.getText().length() < 6 || tbEmail.getText().substring(tbEmail.getText().length() - 1).equals(".")) {
                            if (lblmessage.getText().equals("")) {
                                showMessage(Kleur.RED, "Email voldoet niet aan formaat.", 2500);
                                tbEmail.setStyle(red);
                            }
                            disable(4);
                        } else {
                            lblmessage.setText("");
                            tbEmail.setStyle(green);
                            enable(4);
                        }
                    } else {
                        tbEmail.setStyle(trans);
                        disable(4);
                    }
                }));
            }
            if (tbPasswordVerif != null && lblmessage != null) {
                tbPasswordVerif.textProperty().addListener((observable, oldValue, newValue) -> Platform.runLater(() -> {
                    if (tbPasswordVerif.getText().length() > 0) {
                        if (tbPasswordVerif.getText().equals(appManager.getGebruiker().getWachtwoord())) {
                            lblmessage.setText("");
                            tbPasswordVerif.setStyle(green);
                            enable(5);
                        } else {
                            if (lblmessage.getText().equals("")) {
                                showMessage(Kleur.RED, "Wachtwoord komt niet overeen met uw huidige.", 2500);
                                tbPasswordVerif.setStyle(red);
                            }
                            disable(5);
                        }
                    } else {
                        tbPasswordVerif.setStyle(trans);
                        disable(5);
                    }
                }));
            }
        }
    }
    public void enable(int x) {
        if (btnRegistreer != null) {
            if (x == 1) voteGebruikersnaam = true;
            if (x == 2) voteWachtwoord = true;
            if (x == 3) voteNaam = true;
            if (x == 4) voteEmail = true;
            if (voteGebruikersnaam && voteWachtwoord && voteNaam && voteEmail) btnRegistreer.setDisable(false);
            else btnRegistreer.setDisable(true);
        }
        if (btnProfielWijzigen != null) {
            if (x == 2 || tbWachtwoord.getText().equals("")) voteWachtwoord = true;
            if (x == 3) voteNaam = true;
            if (x == 4) voteEmail = true;
            if (x == 5) voteHuidigWachtwoord = true;
            if (voteWachtwoord && voteNaam && voteEmail && voteHuidigWachtwoord) btnProfielWijzigen.setDisable(false);
        }
    }
    public void disable(int x) {
        if (btnRegistreer != null) {
            if (x == 1) voteGebruikersnaam = false;
            if (x == 2) voteWachtwoord = false;
            if (x == 3) voteNaam = false;
            if (x == 4) voteEmail = false;
            btnRegistreer.setDisable(true);
        }
        if (btnProfielWijzigen != null) {
            if (x == 2) voteWachtwoord = false;
            if (x == 3) voteNaam = false;
            if (x == 4) voteEmail = false;
            if (x == 5) voteHuidigWachtwoord = false;
            btnProfielWijzigen.setDisable(true);
        }
    }
    public void saveProperties() {
        try {
            String gebruikersnaam;
            if (cbOnthoudtGebruikersnaam.isSelected()) gebruikersnaam = tbGebruikersnaam.getText();
            else gebruikersnaam = "";
            Properties props = new Properties();
            props.setProperty("Gebruikersnaam", gebruikersnaam);
            File file = new File("C:\\Users\\Mo\\Desktop\\UserPreferences");
            OutputStream out = new FileOutputStream(file);
            props.store(out, "UserPreferences");
            out. close();
        }
        catch (Exception e ) {
            e.printStackTrace();
        }
    }
    public void loadProperties() {
        if (btnLogin != null) {
            try {
                Properties props = new Properties();
                File file = new File("C:\\Users\\Mo\\Desktop\\UserPreferences");
                if (file.exists()) {
                    InputStream in = new FileInputStream(file);
                    props.load(in);
                    if (!props.getProperty("Gebruikersnaam").equals("")) {
                        tbGebruikersnaam.setText(props.getProperty("Gebruikersnaam"));
                        cbOnthoudtGebruikersnaam.setSelected(true);
                    }
                    else cbOnthoudtGebruikersnaam.setSelected(false);
                    in.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public GebruikerController() throws Exception {
        //Empty constructor
    }
}

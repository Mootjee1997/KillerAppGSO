package sample.javafx;
import sample.userproperties.UserProperties;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sample.core.AppManager;
import javafx.event.ActionEvent;
import sample.enums.Kleur;
import sample.enums.Status;
import sample.models.Gebruiker;
import sample.models.Gegevens;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GebruikerController {
    private Logger logger = Logger.getLogger("Logger");
    private Parent scherm;
    private AppManager appManager = AppManager.getInstance();
    private Gebruiker gebruiker = appManager.getGebruiker();
    private UserProperties props = new UserProperties();
    private boolean voteGebruikersnaam;
    private boolean voteWachtwoord;
    private boolean voteEmail;
    private boolean voteNaam;
    private boolean voteHuidigWachtwoord;

    @FXML public CheckBox cbOnthoudtGebruikersnaam;
    @FXML public Button btnProfielWijzigen;
    @FXML public Button btnRegistreer;
    @FXML public Button btnLogin;
    @FXML public PasswordField tbWachtwoord;
    @FXML public PasswordField tbPasswordVerif;
    @FXML public Label lblmessage;
    @FXML public Label lblIngelogdAls;
    @FXML public TextField tbGebruikersnaam;
    @FXML public TextField tbNaam;
    @FXML public TextField tbWoonplaats;
    @FXML public TextField tbEmail;
    @FXML public TextField tbTelefoonNr;
    @FXML public TextField tbWachtwoordVisible;

    public boolean loginUser(String gebruikersnaam, String wachtwoord) {
        try {
            if (appManager.login(gebruikersnaam, wachtwoord) != null)
                if (appManager.getGebruiker().getStatus() == Status.MEDEWERKER) {
                    scherm = FXMLLoader.load(getClass().getResource("MijnProfiel (Medewerker).fxml"));
                    return true;
                } else if (appManager.getGebruiker().getStatus() == Status.KLANT) {
                    scherm = FXMLLoader.load(getClass().getResource("MijnProfiel (Klant).fxml"));
                    return true;
                }
            return false;
        } catch (Exception ex) {
            logger.log( Level.WARNING, ex.toString(), ex);
        }
        return false;
    }

    public void login(ActionEvent e) {
        try {
            if (!tbGebruikersnaam.getText().trim().equals("") && !tbWachtwoord.getText().trim().equals("")) {
                if (loginUser(tbGebruikersnaam.getText(), tbWachtwoord.getText())) {
                    props.saveProperties(tbGebruikersnaam.getText(), cbOnthoudtGebruikersnaam.isSelected());
                    showWindow(e);
                } else showMessage(Kleur.RED, "Gebruikersnaam en/of wachtwoord komen niet overeen.", 4000);
            } else showMessage(Kleur.RED, "Vul alle benodigde velden in aub.", 4000);
        } catch (Exception ex) {
            logger.log( Level.WARNING, ex.toString(), ex);
        }
    }

    public void registreer(ActionEvent e) {
        try {
        appManager.registreer(new Gebruiker(tbGebruikersnaam.getText(), tbWachtwoord.getText(), new Gegevens(tbNaam.getText(), tbEmail.getText(), tbWoonplaats.getText(), tbTelefoonNr.getText())));
        loginUser(tbGebruikersnaam.getText(), tbWachtwoord.getText());
        showWindow(e);
        }
        catch (Exception ex) {
            logger.log( Level.WARNING, ex.toString(), ex);
        }
    }

    public void wijzigProfiel() {
        try {
            if (!tbWachtwoord.getText().equals("")) {
                appManager.getGebruiker().wijzigGegevens(tbWachtwoord.getText(), new Gegevens(tbNaam.getText(), tbEmail.getText(), tbWoonplaats.getText(), tbTelefoonNr.getText()));
            } else {
                appManager.getGebruiker().wijzigGegevens(new Gegevens(tbNaam.getText(), tbEmail.getText(), tbWoonplaats.getText(), tbTelefoonNr.getText()));
            }
            showMessage(Kleur.GREEN, "Gegevens succesvol gewijzigd.", 4000);
            tbWachtwoord.setText("");
            tbPasswordVerif.setText("");
        } catch (Exception ex) {
            logger.log( Level.WARNING, ex.toString(), ex);
        }
    }

    public void initialize() {
        startListeners();
        if (tbNaam != null && tbWoonplaats != null && tbEmail != null && tbTelefoonNr != null && btnProfielWijzigen != null) {
            tbNaam.setText(gebruiker.getGegevens().getNaam());
            tbWoonplaats.setText(gebruiker.getGegevens().getWoonplaats());
            tbEmail.setText(gebruiker.getGegevens().getEmail());
            tbTelefoonNr.setText(gebruiker.getGegevens().getTelefoonNr());
            lblIngelogdAls.setText("Ingelogd als: " + gebruiker.getGebruikersnaam());
        }
        if (btnLogin != null) {
            List<String> values = props.loadProperties();
            if (values.size() > 1) {
                if (!values.get(0).equals("")) tbGebruikersnaam.setText(values.get(0));
                if (values.get(1).equals("True")) cbOnthoudtGebruikersnaam.setSelected(true);
            }
        }
    }
    public void loguit(ActionEvent e) {
        loadScherm("Hoofdscherm.fxml", e);
    }
    public void openregistreerScherm(ActionEvent e) {
        loadScherm("Registreerscherm.fxml", e);
    }
    public void openReturnScherm(ActionEvent e) {
        loadScherm("Hoofdscherm.fxml", e);
    }
    public void openMijnProfiel(ActionEvent e) {
        if (gebruiker.getStatus() == Status.MEDEWERKER) {
            loadScherm("MijnProfiel (Medewerker).fxml", e);
        }
        else loadScherm("MijnProfiel (Klant).fxml", e);
    }
    public void openMijnBoeken(ActionEvent e) {
        loadScherm("MijnBoeken (Klant).fxml", e);
    }
    public void openGebruikerslijst(ActionEvent e) {
        loadScherm("GebruikersLijst (Medewerker).fxml", e);
    }
    public void openBoekenLijst(ActionEvent e) {
            if (appManager.getGebruiker().getStatus() == Status.MEDEWERKER) {
                loadScherm("Boekenlijst (Medewerker).fxml", e);
            }
            else loadScherm("Boekenlijst (Klant).fxml", e);
    }
    public void openBoekToevoegen(ActionEvent e) {
        loadScherm("BoekToevoegen (Medewerker).fxml", e);
    }
    public void loadScherm(String path, ActionEvent e) {
        try {
            scherm = FXMLLoader.load(getClass().getResource(path));
            showWindow(e);
        } catch (Exception ex) {
            logger.log( Level.WARNING, ex.toString(), ex);
        }
    }
    public void showWindow(ActionEvent e) {
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
    public void showPassword() {
        if (!tbWachtwoordVisible.isVisible()) {
            tbWachtwoord.setVisible(false);
            tbWachtwoordVisible.setVisible(true);
            tbWachtwoordVisible.setText(tbWachtwoord.getText());
        }
        else {
            tbWachtwoord.setVisible(true);
            tbWachtwoordVisible.setVisible(false);
        }
    }
    public void startListeners() {
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
                    } catch (Exception ex) {
                        logger.log( Level.WARNING, ex.toString(), ex);
                    }
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
                        if (btnProfielWijzigen != null) enable(2);
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
        if (x == 1) voteGebruikersnaam = true;
        if (x == 2 || (btnProfielWijzigen != null && tbWachtwoord.getText().equals(""))) voteWachtwoord = true;
        if (x == 3) voteNaam = true;
        if (x == 4) voteEmail = true;
        if (x == 5) voteHuidigWachtwoord = true;

        if (btnRegistreer != null) {
            if (voteGebruikersnaam && voteWachtwoord && voteNaam && voteEmail) btnRegistreer.setDisable(false);
        }
        if (btnProfielWijzigen != null) {
            if (voteWachtwoord && voteNaam && voteEmail && voteHuidigWachtwoord) btnProfielWijzigen.setDisable(false);
        }
    }
    public void disable(int x) {
        if (x == 1) voteGebruikersnaam = false;
        if (x == 2) voteWachtwoord = false;
        if (x == 3) voteNaam = false;
        if (x == 4) voteEmail = false;
        if (x == 5) voteHuidigWachtwoord = false;

        if (btnRegistreer != null) {
            btnRegistreer.setDisable(true);
        }
        if (btnProfielWijzigen != null) {
            btnProfielWijzigen.setDisable(true);
        }
    }
    public GebruikerController() {
        //Empty constructor
    }
}

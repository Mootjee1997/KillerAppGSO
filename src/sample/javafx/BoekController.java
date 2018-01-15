package sample.javafx;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import sample.core.AppManager;
import sample.core.SceneHandler;
import sample.enums.Kleur;
import sample.enums.Status;
import sample.models.*;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BoekController {
    private Logger logger = Logger.getLogger("Logger");
    private SceneHandler sceneHandler = new SceneHandler();
    private AppManager appManager = AppManager.getInstance();
    private Gebruiker gebruiker = appManager.getGebruiker();
    private boolean votelsBoekenLijst;
    private boolean votelsGebruikersLijst;
    private boolean votelsGeleendeBoeken;
    private boolean votelsBeschikbareExemplaren;
    private boolean voteTitel;
    private boolean voteDescriptie;
    private boolean voteAuteurs;
    private boolean voteUitgever;
    private boolean voteTotAantal;

    @FXML private Button btnLeenUit;
    @FXML private Button btnRetourneer;
    @FXML private Button btnToevoegenBoek;
    @FXML private Button btnBeschrijvingWijzigen;
    @FXML private Label lblIngelogdAls;
    @FXML private Label lblMessageBL;
    @FXML private ListView<String> lsBoekenLijst;
    @FXML private ListView<String> lsMijnBoeken;
    @FXML private ListView<String> lsGeleendeBoeken;
    @FXML private ListView<String> lsBeschikbareExemplaren;
    @FXML private ListView<String> lsGebruikersLijst;
    @FXML private TextArea tbDescriptie;
    @FXML private TextArea tbBeschrijving;
    @FXML private TextField tbTitel;
    @FXML private TextField tbTotAantal;
    @FXML private TextField tbBeschikbaar;
    @FXML private TextField tbAuteurs;
    @FXML private TextField tbUitgever;
    @FXML private TextField tbNaam;
    @FXML private TextField tbWoonplaats;
    @FXML private TextField tbEmail;
    @FXML private TextField tbTelefoonNr;
    @FXML private ComboBox cbAuteurs;
    @FXML private ComboBox cbUitgever;

    public void boekToevoegen() throws RemoteException {
        Uitgever uitgever;
        List<Auteur> auteursListBoek = new ArrayList<>();
        String auteurslijst = tbAuteurs.getText();
        String[] auteursList = auteurslijst.split(", ");
        //Toevoegen van de opgegeven Auteurs aan het nieuwe Boek-object
        for (String s : auteursList) {
            if (appManager.zoekAuteur(s) != null) {
                auteursListBoek.add(appManager.zoekAuteur(s));
            }
            else {
                Auteur auteur = new Auteur(new Gegevens(s));
                appManager.addAuteur(auteur);
                auteursListBoek.add(auteur);
            }
        }
        //Toevoegen van de opgegeven Uitgever aan het nieuwe boek-object
        if (appManager.zoekUitgever(tbUitgever.getText()) != null) {
            uitgever = appManager.zoekUitgever(tbUitgever.getText());
        }
        else {
            uitgever = new Uitgever(new Gegevens(tbUitgever.getText()));
            appManager.addUitgever(uitgever);
        }
        appManager.addBoek(new Boek(tbTitel.getText(), tbDescriptie.getText(), auteursListBoek, uitgever), tbTotAantal.getText());
        showMessage(Kleur.GREEN, "Boek succesvol toegevoegd.", 2500);
    }

    public void leenUit() throws RemoteException {
        int volgnummer = Integer.parseInt(lsBeschikbareExemplaren.getSelectionModel().getSelectedItem());
        Gebruiker g = appManager.zoekGebruiker(String.valueOf(lsGebruikersLijst.getSelectionModel().getSelectedItem()));
        appManager.leenUit(volgnummer, g);
        showMessage(Kleur.GREEN, "Boek succesvol uitgeleend aan: " + g.getGebruikersnaam() + ".", 2500);
        updateLists();
    }

    public void retourneer() throws RemoteException {
        int volgnummer = Integer.parseInt(lsGeleendeBoeken.getSelectionModel().getSelectedItem());
        Gebruiker g = appManager.zoekGebruiker(String.valueOf(lsGebruikersLijst.getSelectionModel().getSelectedItem()));
        appManager.retourneer(volgnummer, g);
        showMessage(Kleur.GREEN, "Boek succesvol geretourneerd.", 2500);
        updateLists();
    }

    public void beschrijvingWijzigen() throws RemoteException {
        int volgnummer = Integer.parseInt(lsBeschikbareExemplaren.getSelectionModel().getSelectedItem());
        String beschrijving = tbBeschrijving.getText();
        appManager.setBeschrijving(volgnummer, beschrijving);
        showMessage(Kleur.GREEN, "Beschrijving succesvol gewijzigd.", 2500);
    }

    public void initialize() {
        updateLists();
        startListeners();
        appManager.setBoekController(this);
        lblIngelogdAls.setText("Ingelogd als: " + gebruiker.getGebruikersnaam());
    }
    public void loguit(ActionEvent e) throws IOException {
        sceneHandler.loadScherm(FXMLLoader.load(getClass().getResource("Hoofdscherm.fxml")), e);
    }
    public void openMijnBoeken(ActionEvent e) throws IOException {
        sceneHandler.loadScherm(FXMLLoader.load(getClass().getResource("MijnBoeken (Klant).fxml")), e);
    }
    public void openMijnProfiel(ActionEvent e) throws IOException {
        if (gebruiker.getStatus() == Status.MEDEWERKER) {
            sceneHandler.loadScherm(FXMLLoader.load(getClass().getResource("MijnProfiel (Medewerker).fxml")), e);
        }
        else sceneHandler.loadScherm(FXMLLoader.load(getClass().getResource("MijnProfiel (Klant).fxml")), e);
    }
    public void openBoekenLijst(ActionEvent e) throws IOException {
        if (appManager.getGebruiker().getStatus() == Status.MEDEWERKER) {
            sceneHandler.loadScherm(FXMLLoader.load(getClass().getResource("Boekenlijst (Medewerker).fxml")), e);
        }
        else sceneHandler.loadScherm(FXMLLoader.load(getClass().getResource("Boekenlijst (Klant).fxml")), e);
    }
    public void openBoekToevoegen(ActionEvent e) throws IOException {
        sceneHandler.loadScherm(FXMLLoader.load(getClass().getResource("BoekToevoegen (Medewerker).fxml")), e);
    }
    public void openGebruikerslijst(ActionEvent e) throws IOException {
        sceneHandler.loadScherm(FXMLLoader.load(getClass().getResource("GebruikersLijst (Medewerker).fxml")), e);
    }
    public void selecteerAuteur() {
        if (!tbAuteurs.getText().contains(cbAuteurs.getSelectionModel().getSelectedItem().toString())) {
            if (tbAuteurs.getText().equals(""))
                tbAuteurs.setText(cbAuteurs.getSelectionModel().getSelectedItem().toString());
            else tbAuteurs.setText(tbAuteurs.getText() + ", " + cbAuteurs.getSelectionModel().getSelectedItem().toString());
        }
    }
    public void selecteerUitgever() {
        tbUitgever.setText(cbUitgever.getSelectionModel().getSelectedItem().toString());
    }
    public void updateTextBoxes(Boek boek, BoekExemplaar boekExemplaar, Gebruiker gebruiker) {
        if (boek != null) {
            tbTitel.setText(boek.getTitel());
            tbDescriptie.setText(boek.getDescriptie());
            tbUitgever.setText(boek.getUitgever().getGegevens().getNaam());
            tbAuteurs.setText("");
            if (tbTotAantal != null && tbBeschikbaar != null) {
                try {
                    tbTotAantal.setText(String.valueOf(boek.getBoekExemplaren().size()));
                    tbBeschikbaar.setText(String.valueOf(boek.getAantalBeschikbaar()));
                } catch (Exception ex) {
                    logger.log( Level.WARNING, ex.toString(), ex);
                }
            }
            for (Auteur auteur : boek.getAuteurs()) {
                if (tbAuteurs.getText().equals("")) {
                    tbAuteurs.setText(auteur.getGegevens().getNaam());
                } else {
                    tbAuteurs.setText(tbAuteurs.getText() + ", " + auteur.getGegevens().getNaam());
                }
            }
        }
        if (boekExemplaar != null) {
            tbBeschrijving.setText(boekExemplaar.getBeschrijving());
        }
        if (gebruiker != null) {
            tbNaam.setText(gebruiker.getGegevens().getNaam());
            tbWoonplaats.setText(gebruiker.getGegevens().getWoonplaats());
            tbEmail.setText(gebruiker.getGegevens().getEmail());
            tbTelefoonNr.setText(gebruiker.getGegevens().getTelefoonNr());
        }
    }
    public void updateLists() {
        try {
            if (lsBeschikbareExemplaren != null && lsBoekenLijst != null && lsBoekenLijst.getSelectionModel().getSelectedItem() != null) {
                ObservableList<String> beschikbareExemplaren = FXCollections.observableArrayList(appManager.getBeschikbareExemplaren(String.valueOf(lsBoekenLijst.getSelectionModel().getSelectedItem())));
                lsBeschikbareExemplaren.setItems(beschikbareExemplaren);
            }
            if (lsBoekenLijst != null) {
                ObservableList<String> boeken = FXCollections.observableArrayList(appManager.getBoeken());
                lsBoekenLijst.setItems(boeken);
            }
            if (lsGebruikersLijst != null) {
                ObservableList<String> gebruikers = FXCollections.observableArrayList(appManager.getGebruikers());
                lsGebruikersLijst.setItems(gebruikers);
            }
            if (lsMijnBoeken != null) {
                ObservableList<String> mijnBoeken = FXCollections.observableArrayList(appManager.getGeleendeBoeken(gebruiker));
                lsMijnBoeken.setItems(mijnBoeken);
            }
            if (cbAuteurs != null) {
                ObservableList<String> auteurs = FXCollections.observableArrayList(appManager.getAuteurs());
                cbAuteurs.setItems(auteurs);
            }
            if (cbUitgever != null) {
                ObservableList<String> uitgevers = FXCollections.observableArrayList(appManager.getUitgevers());
                cbUitgever.setItems(uitgevers);
            }
        }
        catch (Exception ex) {
            logger.log( Level.WARNING, ex.toString(), ex);
        }
    }
    public void updateMijnBoeken(List<String> newList) {
        if (lsMijnBoeken != null) {
            Platform.runLater(() -> {
                try {
                    int x = lsMijnBoeken.getItems().size();
                    if (x < newList.size()) {
                        showMessage(Kleur.GREEN, "Gefeliciteerd! Je hebt een nieuw boek geleend.", 6000);
                    }
                    else {
                        showMessage(Kleur.RED, "Een van uw boeken is geretourneerd.", 6000);
                    }
                    ObservableList<String> list = FXCollections.observableArrayList(newList);
                    lsMijnBoeken.setItems(list);
                }
                catch (Exception ex) {
                    logger.log( Level.WARNING, ex.toString(), ex);
                }
            });
        }
    }
    public void updateBoekList(List<String> newList) {
        if (lsBoekenLijst != null) {
            Platform.runLater(() -> {
                try {
                    showMessage(Kleur.GREEN, "Er is een nieuw boek bijgekomen!", 6000);
                    ObservableList<String> list = FXCollections.observableArrayList(newList);
                    lsBoekenLijst.setItems(list);
                }
                catch (Exception ex) {
                    logger.log( Level.WARNING, ex.toString(), ex);
                }
            });
        }
    }
    public void updateGebruikersList(List<String> newList) {
        if (lsGebruikersLijst != null) {
            Platform.runLater(() -> {
                try {
                    showMessage(Kleur.GREEN, "Er is een nieuwe gebruiker bijgekomen!", 6000);
                    ObservableList<String> list = FXCollections.observableArrayList(newList);
                    lsGebruikersLijst.setItems(list);
                }
                catch (Exception ex) {
                    logger.log( Level.WARNING, ex.toString(), ex);
                }
            });
        }
    }
    public void showMessage(Kleur kleur, String message, int ms) {
        if (kleur == Kleur.RED) lblMessageBL.setTextFill(Color.RED);
        if (kleur == Kleur.GREEN) lblMessageBL.setTextFill(Color.GREEN);
        lblMessageBL.setText(message);
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        Platform.runLater(() -> lblMessageBL.setText(""));
                    }
                },
                ms
        );
    }
    public void startListeners() {
        String green = "-fx-border-color: limegreen ; -fx-border-width: 2px ;";
        String red = "-fx-border-color: red ; -fx-border-width: 2px ;";
        String trans = "-fx-border-color: transparent ; -fx-border-width: 2px ;";

        if (lsGebruikersLijst != null) {
            lsGebruikersLijst.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> Platform.runLater(() -> {
                if (lsGebruikersLijst.getSelectionModel().getSelectedItem() != null) {
                    try {
                        Gebruiker g = appManager.zoekGebruiker(String.valueOf(lsGebruikersLijst.getSelectionModel().getSelectedItem()));
                        if (btnLeenUit == null) updateTextBoxes(null, null, g);
                        if (lsGeleendeBoeken != null) {
                            ObservableList<String> geleendeBoeken = FXCollections.observableArrayList(appManager.getGeleendeBoeken(appManager.zoekGebruiker(lsGebruikersLijst.getSelectionModel().getSelectedItem())));
                            lsGeleendeBoeken.setItems(geleendeBoeken);
                            tbBeschrijving.setText("");
                        }
                        enable(6);
                    } catch (Exception ex) {
                        logger.log( Level.WARNING, ex.toString(), ex);
                    }
                }
                else disable(6);
            }));
        }

        if (lsBoekenLijst != null) {
            lsBoekenLijst.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> Platform.runLater(() -> {
                if (lsBoekenLijst.getSelectionModel().getSelectedItem() != null) {
                    try {
                        if (tbBeschrijving != null) tbBeschrijving.setText("");
                        Boek b = appManager.zoekBoek(String.valueOf(lsBoekenLijst.getSelectionModel().getSelectedItem()));
                        updateTextBoxes(b, null, null);
                        if (lsBeschikbareExemplaren != null) {
                            ObservableList<String> beschikbareExemplaren = FXCollections.observableArrayList(appManager.getBeschikbareExemplaren(String.valueOf(lsBoekenLijst.getSelectionModel().getSelectedItem())));
                            lsBeschikbareExemplaren.setItems(beschikbareExemplaren);
                        }
                        enable(7);
                    } catch (Exception ex) {
                        logger.log( Level.WARNING, ex.toString(), ex);
                    }
                }
                else disable(7);
            }));
        }

        if (lsBeschikbareExemplaren != null) {
            lsBeschikbareExemplaren.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> Platform.runLater(() -> {
                if (lsBeschikbareExemplaren.getSelectionModel().getSelectedItem() != null) {
                    try {
                        BoekExemplaar b = appManager.zoekBoekExemplaar(Integer.parseInt(String.valueOf(lsBeschikbareExemplaren.getSelectionModel().getSelectedItem())));
                        updateTextBoxes(null, b, null);
                        enable(8);
                    } catch (Exception ex) {
                        logger.log( Level.WARNING, ex.toString(), ex);
                    }
                }
                else disable(8);
            }));
        }

        if (lsGeleendeBoeken != null) {
            lsGeleendeBoeken.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> Platform.runLater(() -> {
                if (lsGeleendeBoeken.getSelectionModel().getSelectedItem() != null) {
                    try {
                        BoekExemplaar b = appManager.zoekBoekExemplaar(Integer.parseInt(lsGeleendeBoeken.getSelectionModel().getSelectedItem()));
                        updateTextBoxes(null, b, null);
                        enable(9);
                    } catch (Exception ex) {
                        logger.log( Level.WARNING, ex.toString(), ex);
                    }
                }
                else disable(9);
            }));
        }

        if (lsMijnBoeken != null) {
            lsMijnBoeken.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> Platform.runLater(() -> {
                if (lsMijnBoeken.getSelectionModel().getSelectedItem() != null) {
                    try {
                        Boek b = appManager.zoekBoek(appManager.zoekBoekExemplaar(Integer.parseInt(String.valueOf(lsMijnBoeken.getSelectionModel().getSelectedItem()))).getBoek().getTitel());
                        updateTextBoxes(b, null, null);
                    } catch (Exception ex) {
                        logger.log( Level.WARNING, ex.toString(), ex);
                    }
                }
            }));
        }

        if (btnToevoegenBoek != null) {
            if (tbTitel != null && lblMessageBL != null) {
                tbTitel.textProperty().addListener((observable, oldValue, newValue) -> Platform.runLater(() -> {
                    if (tbTitel.getText().length() > 0) {
                        if (tbTitel.getText().length() > 1) {
                            lblMessageBL.setText("");
                            tbTitel.setStyle(green);
                            enable(1);
                        } else {
                            showMessage(Kleur.RED, "Titel moet minimaal 2 letters hebben.", 2500);
                            tbTitel.setStyle(red);
                            disable(1);
                        }
                    } else {
                        tbTitel.setStyle(trans);
                        disable(1);
                    }
                }));
            }
            if (tbDescriptie != null && lblMessageBL != null) {
                tbDescriptie.textProperty().addListener((observable, oldValue, newValue) -> Platform.runLater(() -> {
                    if (tbDescriptie.getText().length() > 0) {
                        if (tbDescriptie.getText().length() > 10) {
                            lblMessageBL.setText("");
                            tbDescriptie.setStyle(green);
                            enable(2);
                        } else {
                            showMessage(Kleur.RED, "Voer minimaal een descriptie in van 10 tekens.", 2500);
                            tbDescriptie.setStyle(red);
                            disable(2);
                        }
                    } else {
                        tbDescriptie.setStyle(trans);
                        disable(2);
                    }
                }));
            }
            if (tbTotAantal != null && lblMessageBL != null) {
                tbTotAantal.textProperty().addListener((observable, oldValue, newValue) -> Platform.runLater(() -> {
                    if (tbTotAantal.getText().length() > 0) {
                        if (tbTotAantal.getText().matches("[0-9]+")) {
                            if (tbTotAantal.getText().length() > 0 && Integer.parseInt(tbTotAantal.getText()) > 0 && Integer.parseInt(tbTotAantal.getText()) < 21) {
                                tbTotAantal.setStyle(green);
                                enable(3);
                            } else {
                                showMessage(Kleur.RED, "Voer aub een geldig aantal exemplaren in (1-20)", 3500);
                                tbTotAantal.setStyle(red);
                                disable(3);
                            }
                        } else {
                            showMessage(Kleur.RED, "Voer aub een geldige waarde in.", 3500);
                            tbTotAantal.setStyle(red);
                            disable(3);
                        }
                    } else {
                        tbTotAantal.setStyle(trans);
                        disable(3);
                    }
                }));
            }
            if (tbAuteurs != null && lblMessageBL != null) {
                tbAuteurs.textProperty().addListener((observable, oldValue, newValue) -> Platform.runLater(() -> {
                    if (tbAuteurs.getText().length() > 0) {
                        tbAuteurs.setStyle(green);
                        enable(4);
                    } else {
                        showMessage(Kleur.RED, "Voer aub een Auteur in of selecteer er een uit de lijst.", 5500);
                        tbAuteurs.setStyle(red);
                        disable(4);
                    }
                }));
            }
            if (tbUitgever != null && lblMessageBL != null) {
                tbUitgever.textProperty().addListener((observable, oldValue, newValue) -> Platform.runLater(() -> {
                    if (tbUitgever.getText().length() > 0) {
                        tbUitgever.setStyle(green);
                        enable(5);
                    } else {
                        showMessage(Kleur.RED, "Voer aub een Uitgever in of selecteer er een uit de lijst.", 5500);
                        tbUitgever.setStyle(red);
                        disable(5);
                    }
                }));
            }
        }
    }
    public void enable(int x) {
        if (x == 1) voteTitel = true;
        if (x == 2) voteDescriptie = true;
        if (x == 3) voteTotAantal = true;
        if (x == 4) voteAuteurs = true;
        if (x == 5) voteUitgever = true;
        if (x == 6) votelsGebruikersLijst = true;
        if (x == 7) votelsBoekenLijst = true;
        if (x == 8) votelsBeschikbareExemplaren = true;
        if (x == 9) votelsGeleendeBoeken = true;

        if (btnToevoegenBoek != null && voteTitel && voteDescriptie && voteTotAantal && voteAuteurs && voteUitgever) {
            btnToevoegenBoek.setDisable(false);
        }
        if (btnLeenUit != null && votelsGebruikersLijst && votelsBoekenLijst && votelsBeschikbareExemplaren) {
            btnLeenUit.setDisable(false);
        }
        if (btnRetourneer != null && votelsGebruikersLijst && votelsGeleendeBoeken) {
            btnRetourneer.setDisable(false);
        }
        if (btnBeschrijvingWijzigen != null && votelsBeschikbareExemplaren) {
            btnBeschrijvingWijzigen.setDisable(false);
        }
    }
    public void disable(int x) {
        if (x == 1) voteTitel = false;
        if (x == 2) voteDescriptie = false;
        if (x == 3) voteTotAantal = false;
        if (x == 4) voteAuteurs = false;
        if (x == 5) voteUitgever = false;
        if (x == 6) votelsGebruikersLijst = false;
        if (x == 7) votelsBoekenLijst = false;
        if (x == 8) votelsBeschikbareExemplaren = false;
        if (x == 9) votelsGeleendeBoeken = false;

        if (btnToevoegenBoek != null) {
            btnToevoegenBoek.setDisable(true);
        }
        if (btnLeenUit != null) {
            btnLeenUit.setDisable(true);
        }
        if (btnRetourneer != null) {
            btnRetourneer.setDisable(true);
        }
        if (btnBeschrijvingWijzigen != null) {
            btnBeschrijvingWijzigen.setDisable(true);
        }
    }
    public BoekController() {
        //Empty constructor
    }
}

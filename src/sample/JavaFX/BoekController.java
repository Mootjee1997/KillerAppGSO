package sample.JavaFX;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sample.Core.AppManager;
import sample.Enums.Kleur;
import sample.Enums.Status;
import sample.Models.*;
import java.util.ArrayList;

public class BoekController {
    private Parent scherm;
    private AppManager appManager = AppManager.getInstance();
    private Gebruiker gebruiker = appManager.getGebruiker();
    private ObservableList<String> boeken, auteurs, uitgevers, gebruikers, mijnBoeken, beschikbareExemplaren, geleendeBoeken;
    private boolean votelsBoekenLijst, votelsGebruikersLijst, votelsGeleendeBoeken, votelsBeschikbareExemplaren, voteTitel, voteDescriptie, voteAuteurs, voteUitgever, voteTotAantal;

    @FXML private Button btnLeenUit, btnRetourneer, btnToevoegenBoek, btnBeschrijvingWijzigen;
    @FXML private Label lblIngelogdAls, lblMessageBL;
    @FXML private ListView<String> lsBoekenLijst, lsMijnBoeken, lsGeleendeBoeken, lsBeschikbareExemplaren, lsGebruikersLijst;
    @FXML private TextArea tbDescriptie, tbBeschrijving;
    @FXML private TextField tbTitel, tbTotAantal, tbBeschikbaar,tbAuteurs, tbUitgever, tbNaam, tbWoonplaats, tbEmail, tbTelefoonNr;
    @FXML private ComboBox cbAuteurs, cbUitgever;

    public void boekToevoegen(ActionEvent e) throws Exception {
        Uitgever uitgever;
        ArrayList<Auteur> auteursListBoek = new ArrayList<>();
        String auteurs = tbAuteurs.getText();
        String[] auteursList = auteurs.split(", ");
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
        //updateLists();
    }

    public void leenUit(ActionEvent e) throws Exception {
        int volgnummer = Integer.parseInt(lsBeschikbareExemplaren.getSelectionModel().getSelectedItem());
        Gebruiker gebruiker = appManager.zoekGebruiker(String.valueOf(lsGebruikersLijst.getSelectionModel().getSelectedItem()));
        appManager.leenUit(volgnummer, gebruiker);
        showMessage(Kleur.GREEN, "Boek succesvol uitgeleend aan: " + gebruiker.getGebruikersnaam() + ".", 2500);
        updateLists();
    }

    public void retourneer(ActionEvent e) throws Exception {
        int volgnummer = Integer.parseInt(lsGeleendeBoeken.getSelectionModel().getSelectedItem());
        Gebruiker gebruiker = appManager.zoekGebruiker(String.valueOf(lsGebruikersLijst.getSelectionModel().getSelectedItem()));
        appManager.retourneer(volgnummer, gebruiker);
        showMessage(Kleur.GREEN, "Boek succesvol geretourneerd.", 2500);
        updateLists();
    }

    public void beschrijvingWijzigen(ActionEvent e) throws Exception {
        int volgnummer = Integer.parseInt(lsBeschikbareExemplaren.getSelectionModel().getSelectedItem());
        String beschrijving = tbBeschrijving.getText();
        appManager.setBeschrijving(volgnummer, beschrijving);
        showMessage(Kleur.GREEN, "Beschrijving succesvol gewijzigd.", 2500);
    }

    public void initialize() throws Exception {
        updateLists();
        startListeners();
        appManager.setBoekController(this);
        lblIngelogdAls.setText("Ingelogd als: " + gebruiker.getGebruikersnaam());
    }
    public void loguit(ActionEvent e) throws Exception {
        appManager.loguit();
        scherm = FXMLLoader.load(getClass().getResource("Hoofdscherm.fxml"));
        showWindow(e);
    }
    public void openMijnBoeken(ActionEvent e) throws Exception {
        scherm = FXMLLoader.load(getClass().getResource("MijnBoeken (Klant).fxml"));
        showWindow(e);
    }
    public void openMijnProfiel(ActionEvent e) throws Exception {
        if (appManager.getGebruiker().getStatus() == Status.MEDEWERKER) {
            scherm = FXMLLoader.load(getClass().getResource("MijnProfiel (Medewerker).fxml"));
        }
        else scherm = FXMLLoader.load(getClass().getResource("MijnProfiel (Klant).fxml"));
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
    public void openGebruikerslijst(ActionEvent e) throws Exception {
        scherm = FXMLLoader.load(getClass().getResource("GebruikersLijst (Medewerker).fxml"));
        showWindow(e);
    }
    public void selecteerAuteur(ActionEvent e) throws Exception {
        if (!tbAuteurs.getText().contains(cbAuteurs.getSelectionModel().getSelectedItem().toString())) {
            if (tbAuteurs.getText().equals(""))
                tbAuteurs.setText(cbAuteurs.getSelectionModel().getSelectedItem().toString());
            else tbAuteurs.setText(tbAuteurs.getText() + ", " + cbAuteurs.getSelectionModel().getSelectedItem().toString());
        }
    }
    public void selecteerUitgever(ActionEvent e) throws Exception {
        tbUitgever.setText(cbUitgever.getSelectionModel().getSelectedItem().toString());
    }
    public void updateTextBoxes(Boek boek, BoekExemplaar boekExemplaar, Gebruiker gebruiker) throws Exception {
        if (boek != null) {
            tbTitel.setText(boek.getTitel());
            tbDescriptie.setText(boek.getDescriptie());
            tbUitgever.setText(boek.getUitgever().getGegevens().getNaam());
            tbAuteurs.setText("");
            if (tbTotAantal != null && tbBeschikbaar != null) {
                try {
                    tbTotAantal.setText(String.valueOf(boek.getBoekExemplaren().size()));
                    tbBeschikbaar.setText(String.valueOf(boek.getAantalBeschikbaar()));
                } catch (Exception e) {
                    e.printStackTrace();
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
    public void updateLists() throws Exception {
        if (lsBeschikbareExemplaren != null && lsBoekenLijst != null && lsBoekenLijst.getSelectionModel().getSelectedItem() != null) {
            beschikbareExemplaren = FXCollections.observableArrayList(appManager.getBeschikbareExemplaren(String.valueOf(lsBoekenLijst.getSelectionModel().getSelectedItem())));
            lsBeschikbareExemplaren.setItems(beschikbareExemplaren);
        }
        if (lsBoekenLijst != null) {
            boeken = FXCollections.observableArrayList(appManager.getBoeken());
            lsBoekenLijst.setItems(boeken);
        }
        if (lsGebruikersLijst != null) {
            gebruikers = FXCollections.observableArrayList(appManager.getGebruikers());
            lsGebruikersLijst.setItems(gebruikers);
        }
        if (lsMijnBoeken != null) {
            mijnBoeken = FXCollections.observableArrayList(appManager.getGeleendeBoeken(gebruiker));
            lsMijnBoeken.setItems(mijnBoeken);
        }
        if (cbAuteurs != null) {
            auteurs = FXCollections.observableArrayList(appManager.getAuteurs());
            cbAuteurs.setItems(auteurs);
        }
        if (cbUitgever != null) {
            uitgevers = FXCollections.observableArrayList(appManager.getUitgevers());
            cbUitgever.setItems(uitgevers);
        }
    }
    public void updateMijnBoeken(ArrayList<String> newList) {
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
                    ObservableList<String> mijnBoeken = FXCollections.observableArrayList(newList);
                    lsMijnBoeken.setItems(mijnBoeken);
                }
                catch (Exception ex) {
                    System.out.println(ex);
                    ex.printStackTrace();
                }
            });
        }
    }
    public void updateBoekList(ArrayList<String> newList) {
        if (lsBoekenLijst != null) {
            Platform.runLater(() -> {
                try {
                    showMessage(Kleur.GREEN, "Er is een nieuw boek bijgekomen!", 6000);
                    ObservableList<String> boekenlijst = FXCollections.observableArrayList(newList);
                    lsBoekenLijst.setItems(boekenlijst);
                }
                catch (Exception ex) {
                    System.out.println(ex);
                }
            });
        }
    }
    public void updateGebruikersList(ArrayList<String> newList) {
        if (lsGebruikersLijst != null) {
            Platform.runLater(() -> {
                try {
                    showMessage(Kleur.GREEN, "Er is een nieuwe gebruiker bijgekomen!", 6000);
                    ObservableList<String> gebruikerslijst = FXCollections.observableArrayList(newList);
                    lsGebruikersLijst.setItems(gebruikerslijst);
                }
                catch (Exception ex) {
                    System.out.println(ex);
                }
            });
        }
    }
    public void showWindow(ActionEvent e){
        Scene gebruikersscherm = new Scene(scherm);
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(gebruikersscherm);
        stage.show();
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
    public void startListeners() throws Exception {
        String green = "-fx-border-color: limegreen ; -fx-border-width: 2px ;";
        String red = "-fx-border-color: red ; -fx-border-width: 2px ;";
        String trans = "-fx-border-color: transparent ; -fx-border-width: 2px ;";

        if (lsGebruikersLijst != null) {
            lsGebruikersLijst.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> Platform.runLater(() -> {
                if (lsGebruikersLijst.getSelectionModel().getSelectedItem() != null) {
                    try {
                        Gebruiker gebruiker = appManager.zoekGebruiker(String.valueOf(lsGebruikersLijst.getSelectionModel().getSelectedItem()));
                        if (btnLeenUit == null) updateTextBoxes(null, null, gebruiker);
                        if (lsGeleendeBoeken != null) {
                            geleendeBoeken = FXCollections.observableArrayList(appManager.getGeleendeBoeken(appManager.zoekGebruiker(lsGebruikersLijst.getSelectionModel().getSelectedItem())));
                            lsGeleendeBoeken.setItems(geleendeBoeken);
                            tbBeschrijving.setText("");
                        }
                        enable(6);
                    } catch (Exception ex) {
                        ex.printStackTrace();
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
                        Boek boek = appManager.zoekBoek(String.valueOf(lsBoekenLijst.getSelectionModel().getSelectedItem()));
                        updateTextBoxes(boek, null, null);
                        if (lsBeschikbareExemplaren != null) {
                            beschikbareExemplaren = FXCollections.observableArrayList(appManager.getBeschikbareExemplaren(String.valueOf(lsBoekenLijst.getSelectionModel().getSelectedItem())));
                            lsBeschikbareExemplaren.setItems(beschikbareExemplaren);
                        }
                        enable(7);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                else disable(7);
            }));
        }

        if (lsBeschikbareExemplaren != null) {
            lsBeschikbareExemplaren.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> Platform.runLater(() -> {
                if (lsBeschikbareExemplaren.getSelectionModel().getSelectedItem() != null) {
                    try {
                        BoekExemplaar boekExemplaar = appManager.zoekBoekExemplaar(Integer.parseInt(String.valueOf(lsBeschikbareExemplaren.getSelectionModel().getSelectedItem())));
                        updateTextBoxes(null, boekExemplaar, null);
                        enable(8);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else disable(8);
            }));
        }

        if (lsGeleendeBoeken != null) {
            lsGeleendeBoeken.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> Platform.runLater(() -> {
                if (lsGeleendeBoeken.getSelectionModel().getSelectedItem() != null) {
                    try {
                        BoekExemplaar boekExemplaar = appManager.zoekBoekExemplaar(Integer.parseInt(lsGeleendeBoeken.getSelectionModel().getSelectedItem()));
                        updateTextBoxes(null, boekExemplaar, null);
                        enable(9);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else disable(9);
            }));
        }

        if (lsMijnBoeken != null) {
            lsMijnBoeken.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> Platform.runLater(() -> {
                if (lsMijnBoeken.getSelectionModel().getSelectedItem() != null) {
                    try {
                        Boek boek = appManager.zoekBoek(appManager.zoekBoekExemplaar(Integer.parseInt(String.valueOf(lsMijnBoeken.getSelectionModel().getSelectedItem()))).getBoek().getTitel());
                        updateTextBoxes(boek, null, null);
                    } catch (Exception e) {
                        e.printStackTrace();
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
        if (btnToevoegenBoek != null) {
            if (x == 1) voteTitel = true;
            if (x == 2) voteDescriptie = true;
            if (x == 3) voteTotAantal = true;
            if (x == 4) voteAuteurs = true;
            if (x == 5) voteUitgever = true;
            if (voteTitel && voteDescriptie && voteAuteurs && voteUitgever) btnToevoegenBoek.setDisable(false);
            else btnToevoegenBoek.setDisable(true);
        }
        if (btnLeenUit != null) {
            if (x == 6) votelsGebruikersLijst = true;
            if (x == 7) votelsBoekenLijst = true;
            if (x == 8) votelsBeschikbareExemplaren = true;
            if (votelsGebruikersLijst && votelsBoekenLijst && votelsBeschikbareExemplaren) btnLeenUit.setDisable(false);
        }
        if (btnRetourneer != null) {
            if (x == 6) votelsGebruikersLijst = true;
            if (x == 9) votelsGeleendeBoeken = true;
            if (votelsGebruikersLijst && votelsGeleendeBoeken) btnRetourneer.setDisable(false);
        }
        if (btnBeschrijvingWijzigen != null) {
            if (x == 8) votelsBeschikbareExemplaren = true;
            if (votelsBeschikbareExemplaren) btnBeschrijvingWijzigen.setDisable(false);
        }
    }
    public void disable(int x) {
        if (btnToevoegenBoek != null) {
            if (x == 1) voteTitel = false;
            if (x == 2) voteDescriptie = false;
            if (x == 3) voteTotAantal = false;
            if (x == 4) voteAuteurs = false;
            if (x == 5) voteUitgever = false;
            btnToevoegenBoek.setDisable(true);
        }
        if (btnLeenUit != null) {
            if (x == 6) votelsGebruikersLijst = false;
            if (x == 7) votelsBoekenLijst = false;
            if (x == 8) votelsBeschikbareExemplaren = false;
            btnLeenUit.setDisable(true);
        }
        if (btnRetourneer != null) {
            if (x == 6) votelsGebruikersLijst = false;
            if (x == 9) votelsGeleendeBoeken = false;
            btnRetourneer.setDisable(true);
        }
        if (btnBeschrijvingWijzigen != null) {
            if (x == 8) votelsBeschikbareExemplaren = false;
            btnBeschrijvingWijzigen.setDisable(true);
        }
    }
    public BoekController() throws Exception {
        //Empty constructor
    }
}

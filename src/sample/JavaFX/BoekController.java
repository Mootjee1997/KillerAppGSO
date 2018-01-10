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

    @FXML private Button btnLeenUit;
    @FXML private Label lblIngelogdAls, lblMessageBL;
    @FXML private ListView<String> lsBoekenLijst, lsMijnBoeken, lsGeleendeBoeken, lsBeschikbareExemplaren, lsGebruikersLijst;
    @FXML private TextArea tbDescriptie, tbBeschrijving;
    @FXML private TextField tbTitel, tbTotAantal, tbBeschikbaar,tbAuteurs, tbUitgever, tbNaam, tbWoonplaats, tbEmail, tbTelefoonNr;
    @FXML private ComboBox cbAuteurs, cbUitgever;

    public void boekToevoegen(ActionEvent e) throws Exception {
        if (!tbTitel.getText().trim().equals("") && !tbDescriptie.getText().trim().equals("") && !tbTotAantal.getText().trim().equals("") && !tbAuteurs.getText().trim().equals("") && !tbUitgever.getText().trim().equals("")) {
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
            showMessage(Kleur.GREEN, "Boek succesvol toegevoegd.");
        }
        else showMessage(Kleur.RED, "Vul alle benodigde velden in aub.");
    }

    public void leenUit(ActionEvent e) throws Exception {
        if (lsBeschikbareExemplaren.getSelectionModel().getSelectedItem() != null && lsGebruikersLijst.getSelectionModel().getSelectedItem() != null) {
            int volgnummer = Integer.parseInt(lsBeschikbareExemplaren.getSelectionModel().getSelectedItem());
            Gebruiker gebruiker = appManager.zoekGebruiker(String.valueOf(lsGebruikersLijst.getSelectionModel().getSelectedItem()));
            appManager.leenUit(volgnummer, gebruiker);
            updateLists();
            showMessage(Kleur.GREEN, "Boek succesvol uitgeleend aan: " + gebruiker.getGebruikersnaam() + ".");
        }
        else showMessage(Kleur.RED, "Selecteer eerst een boekexemplaar en gebruiker uit de lijst.");
    }

    public void retourneer(ActionEvent e) throws Exception {
        if (lsGeleendeBoeken.getSelectionModel().getSelectedItem() != null && lsGebruikersLijst.getSelectionModel().getSelectedItem() != null) {
            int volgnummer = Integer.parseInt(lsGeleendeBoeken.getSelectionModel().getSelectedItem());
            Gebruiker gebruiker = appManager.zoekGebruiker(String.valueOf(lsGebruikersLijst.getSelectionModel().getSelectedItem()));
            appManager.retourneer(volgnummer, gebruiker);
            updateLists();
            showMessage(Kleur.GREEN, "Boek succesvol geretourneerd.");
        }
        else showMessage(Kleur.RED, "Selecteer eerst een boekexemplaar en gebruiker uit de lijsten.");
    }

    public void beschrijvingWijzigen(ActionEvent e) throws Exception {
        if (lsBeschikbareExemplaren.getSelectionModel().getSelectedItem() != null) {
            int volgnummer = Integer.parseInt(lsBeschikbareExemplaren.getSelectionModel().getSelectedItem());
            String beschrijving = tbBeschrijving.getText();
            appManager.setBeschrijving(volgnummer, beschrijving);
            showMessage(Kleur.GREEN, "Beschrijving succesvol gewijzigd.");
        }
        else showMessage(Kleur.RED, "Selecteer eerst een boekexemplaar uit de lijst.");
    }

    public void showWindow(ActionEvent e){
        Scene gebruikersscherm = new Scene(scherm);
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(gebruikersscherm);
        stage.show();
    }
    public void initialize() throws Exception {
        updateLists();
        appManager.boekController = this;
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
    public void getSelectedItemInfo() throws Exception {
        if (tbBeschrijving != null) {
            tbBeschrijving.setText("");
        }
        if (lsGebruikersLijst != null && lsGebruikersLijst.getSelectionModel().getSelectedItem() != null && btnLeenUit == null) {
            Gebruiker gebruiker = appManager.zoekGebruiker(String.valueOf(lsGebruikersLijst.getSelectionModel().getSelectedItem()));
            tbNaam.setText(gebruiker.getGegevens().getNaam());
            tbWoonplaats.setText(gebruiker.getGegevens().getWoonplaats());
            tbEmail.setText(gebruiker.getGegevens().getEmail());
            tbTelefoonNr.setText(gebruiker.getGegevens().getTelefoonNr());
            updateLists();
        }
        if (lsGeleendeBoeken != null && lsGeleendeBoeken.getSelectionModel().getSelectedItem() != null) {
            BoekExemplaar boek = appManager.zoekBoekExemplaar(Integer.parseInt(lsGeleendeBoeken.getSelectionModel().getSelectedItem()));
            tbBeschrijving.setText(boek.getBeschrijving());
        }
        if (lsBeschikbareExemplaren != null && lsBeschikbareExemplaren.getSelectionModel().getSelectedItem() != null) {
            tbBeschrijving.setText("");
            BoekExemplaar boek = appManager.zoekBoekExemplaar(Integer.parseInt(String.valueOf(lsBeschikbareExemplaren.getSelectionModel().getSelectedItem())));
            tbBeschrijving.setText(boek.getBeschrijving());
        }

        Boek boek = null;
        if (lsBoekenLijst != null && lsBoekenLijst.getSelectionModel().getSelectedItem() != null) {
            boek = appManager.zoekBoek(String.valueOf(lsBoekenLijst.getSelectionModel().getSelectedItem()));
        }
        if (lsMijnBoeken != null && lsMijnBoeken.getSelectionModel().getSelectedItem() != null) {
            boek = appManager.zoekBoek(appManager.zoekBoekExemplaar(Integer.parseInt(String.valueOf(lsMijnBoeken.getSelectionModel().getSelectedItem()))).getBoek().getTitel());
        }
        if (boek != null) {
            if (lsBeschikbareExemplaren != null) {
                beschikbareExemplaren = FXCollections.observableArrayList(appManager.getBeschikbareExemplaren(String.valueOf(lsBoekenLijst.getSelectionModel().getSelectedItem())));
                lsBeschikbareExemplaren.setItems(beschikbareExemplaren);
            }
            if (tbTotAantal != null && tbBeschikbaar != null) {
                tbTotAantal.setText(String.valueOf(boek.getBoekExemplaren().size()));
                tbBeschikbaar.setText(String.valueOf(boek.getAantalBeschikbaar()));
            }
            tbTitel.setText(boek.getTitel());
            tbDescriptie.setText(boek.getDescriptie());
            tbUitgever.setText(boek.getUitgever().getGegevens().getNaam());
            tbAuteurs.setText("");
            for (Auteur auteur : boek.getAuteurs()) {
                if (tbAuteurs.getText().equals("")) {
                    tbAuteurs.setText(auteur.getGegevens().getNaam());
                }
                else {
                    tbAuteurs.setText(tbAuteurs.getText() + ", " + auteur.getGegevens().getNaam());
                }
            }
        }
        if (lsBeschikbareExemplaren != null && lsBeschikbareExemplaren.getSelectionModel().getSelectedItem() == null) {
            tbBeschrijving.setText("");
        }
    }
    public void updateLists() throws Exception {
        if (lsBeschikbareExemplaren != null && lsBoekenLijst != null && lsBoekenLijst.getSelectionModel().getSelectedItem() != null) {
            beschikbareExemplaren = FXCollections.observableArrayList(appManager.getBeschikbareExemplaren(String.valueOf(lsBoekenLijst.getSelectionModel().getSelectedItem())));
            lsBeschikbareExemplaren.setItems(beschikbareExemplaren);
            getSelectedItemInfo();
        }
        if (lsGeleendeBoeken != null && lsGebruikersLijst != null && lsGebruikersLijst.getSelectionModel().getSelectedItem() != null) {
            geleendeBoeken = FXCollections.observableArrayList(appManager.getGeleendeBoeken(appManager.zoekGebruiker(lsGebruikersLijst.getSelectionModel().getSelectedItem())));
            lsGeleendeBoeken.setItems(geleendeBoeken);
            tbBeschrijving.setText("");
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
    public void updateBoekList(ArrayList<String> newList) {
        if (lsBoekenLijst != null) {
            Platform.runLater(() -> {
                try {
                    showMessage(Kleur.GREEN, "Er is een nieuw boek bijgekomen!");
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
                    showMessage(Kleur.GREEN, "Er is een nieuwe gebruiker bijgekomen!");
                    ObservableList<String> gebruikerslijst = FXCollections.observableArrayList(newList);
                    lsGebruikersLijst.setItems(gebruikerslijst);
                }
                catch (Exception ex) {
                    System.out.println(ex);
                }
            });
        }
    }
    public void showMessage(Kleur kleur, String message) throws Exception {
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
                5000
        );
    }
    public BoekController() throws Exception { }
}

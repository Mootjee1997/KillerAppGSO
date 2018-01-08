package sample.JavaFX;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.Core.AppManager;
import sample.Models.*;
import java.util.ArrayList;

public class BoekController {
    private Parent scherm;
    private AppManager appManager = AppManager.getInstance();
    private Gebruiker gebruiker = appManager.getGebruiker();
    private ObservableList<Boek> boeken = FXCollections.observableArrayList(appManager.getBoeken());
    private ObservableList<Auteur> auteurs = FXCollections.observableArrayList(appManager.getAuteurs());
    private ObservableList<Uitgever> uitgevers = FXCollections.observableArrayList(appManager.getUitgevers());
    private ObservableList<Gebruiker> gebruikers = FXCollections.observableArrayList(appManager.getGebruikers());
    private ObservableList<BoekExemplaar> mijnBoeken = FXCollections.observableArrayList(gebruiker.getGeleendeBoeken());

    @FXML private Button btnLeenUit, btnRetourneer, btnBeschrijvingWijzigen;
    @FXML private Label lblIngelogdAls;
    @FXML private ListView<Boek> lsBoekenLijst;
    @FXML private ListView<Gebruiker> lsGebruikersLijst;
    @FXML private ListView<BoekExemplaar> lsMijnBoeken, lsGeleendeBoeken, lsBeschikbareExemplaren;
    @FXML private TextField tbTitel, tbDescriptie, tbTotAantal, tbBeschikbaar,tbAuteurs, tbUitgever, tbNaam, tbWoonplaats, tbEmail, tbTelefoonNr, tbBeschrijving;
    @FXML private ComboBox cbAuteurs, cbUitgever;

    public void showWindow(ActionEvent e){
        Scene gebruikersscherm = new Scene(scherm);
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(gebruikersscherm);
        stage.show();
    }

    @FXML public void initialize() throws Exception {
        laadGegevens();
        lblIngelogdAls.setText("Ingelogd als: " + gebruiker.getGebruikersnaam());
    }

    @FXML public void boekToevoegen(ActionEvent e) throws Exception {
        if (!tbTitel.getText().trim().equals("") && !tbDescriptie.getText().trim().equals("") && !tbTotAantal.getText().trim().equals("") && !tbAuteurs.getText().trim().equals("") && !tbUitgever.getText().trim().equals("")) {
            String auteurs = tbAuteurs.getText();
            String[] auteursList = auteurs.split(", ");
            ArrayList<Auteur> auteursListBoek = new ArrayList<>();
            Uitgever uitgever;
            for (String s : auteursList) {
                if (appManager.zoekAuteur(s) != null) { auteursListBoek.add(appManager.zoekAuteur(s)); }
                else {
                    Auteur auteur = new Auteur(new Gegevens(s));
                    auteursListBoek.add(auteur);
                    appManager.addAuteur(auteur);
                }
            }
            if (appManager.zoekUitgever(tbUitgever.getText()) != null) {
                uitgever = appManager.zoekUitgever(tbUitgever.getText());
            }
            else {
                uitgever = new Uitgever(new Gegevens(tbUitgever.getText()));
                appManager.addUitgever(uitgever);
            }
            appManager.addBoek(new Boek(tbTitel.getText(), tbDescriptie.getText(), auteursListBoek, uitgever));
        }
    }

    @FXML public void leenUit(ActionEvent e) throws Exception {
        int volgnummer = lsBeschikbareExemplaren.getSelectionModel().getSelectedItem().getVolgnummer();
        Boek boek = appManager.zoekBoek(String.valueOf(lsBoekenLijst.getSelectionModel().getSelectedItem()));
        Gebruiker gebruiker = appManager.zoekGebruiker(String.valueOf(lsGebruikersLijst.getSelectionModel().getSelectedItem()));
        appManager.leenUit(volgnummer, boek, gebruiker);
    }

    @FXML public void retourneer(ActionEvent e) throws Exception {
        Gebruiker gebruiker = appManager.zoekGebruiker(String.valueOf(lsGebruikersLijst.getSelectionModel().getSelectedItem()));
        BoekExemplaar boekExemplaar = appManager.zoekBoekExemplaar(Integer.parseInt(String.valueOf(lsGeleendeBoeken.getSelectionModel().getSelectedItem())));
        appManager.retourneer(boekExemplaar, gebruiker);
    }

    @FXML public void beschrijvingWijzigen(ActionEvent e) throws Exception {
        BoekExemplaar boekExemplaar = appManager.zoekBoekExemplaar(Integer.parseInt(String.valueOf(lsGeleendeBoeken.getSelectionModel().getSelectedItem())));
        boekExemplaar.setBeschrijving(tbBeschrijving.getText());
        appManager.setBeschrijving(boekExemplaar);
    }

    @FXML public void loguit(ActionEvent e) throws Exception {
        appManager.loguit();
        scherm = FXMLLoader.load(getClass().getResource("Hoofdscherm.fxml"));
        showWindow(e);
    }

    @FXML public void getGebruikerInfo() throws Exception {
        if (tbBeschrijving != null) tbBeschrijving.setText("");
        if (btnBeschrijvingWijzigen != null) btnBeschrijvingWijzigen.setDisable(true);
        if (btnRetourneer != null) btnRetourneer.setDisable(true);
        Gebruiker gebruiker = appManager.zoekGebruiker(String.valueOf(lsGebruikersLijst.getSelectionModel().getSelectedItem()));
        tbNaam.setText(gebruiker.getGegevens().getNaam());
        tbWoonplaats.setText(gebruiker.getGegevens().getWoonplaats());
        tbEmail.setText(gebruiker.getGegevens().getEmail());
        tbTelefoonNr.setText(gebruiker.getGegevens().getTelefoonNr());
        getGeleendeBoeken();
    }
    @FXML public void getGeleendeBoeken() throws Exception {
        if (lsGeleendeBoeken != null) {
            ObservableList<BoekExemplaar> boeken = FXCollections.observableArrayList(appManager.zoekGebruiker(String.valueOf(lsGebruikersLijst.getSelectionModel().getSelectedItem())).getGeleendeBoeken());
            lsGeleendeBoeken.setItems(boeken);
        }
    }
    @FXML public void getBoekInfo() throws Exception {
        Boek boek;
        if (lsBoekenLijst != null) { boek = appManager.zoekBoek(String.valueOf(lsBoekenLijst.getSelectionModel().getSelectedItem())); }
        else boek = appManager.zoekBoek(appManager.zoekBoekExemplaar(Integer.parseInt(String.valueOf(lsMijnBoeken.getSelectionModel().getSelectedItem()))).getBoek().getTitel());

        if (lsBeschikbareExemplaren != null) {
            tbBeschrijving.setText("");
            ObservableList<BoekExemplaar> boeken = FXCollections.observableArrayList(appManager.zoekBoek(String.valueOf(lsBoekenLijst.getSelectionModel().getSelectedItem())).getBeschikbareExemplaren());
            lsBeschikbareExemplaren.setItems(boeken);
        }
        if (tbTotAantal != null && tbBeschikbaar != null) {
            tbTotAantal.setText(String.valueOf(boek.getBoekExemplaren().size()));
            tbBeschikbaar.setText(String.valueOf(boek.getAantalBeschikbaar()));
        }
        if (btnLeenUit != null && boek.getAantalBeschikbaar() == 0) { btnLeenUit.setDisable(true); }

        tbTitel.setText(boek.getTitel());
        tbDescriptie.setText(boek.getDescriptie());
        tbUitgever.setText(boek.getUitgever().getGegevens().getNaam());
        tbAuteurs.setText("");
        for (Auteur auteur : boek.getAuteurs()) {
            if (tbAuteurs.getText().equals("")) {
                tbAuteurs.setText(auteur.getGegevens().getNaam());
            }
            else tbAuteurs.setText(tbAuteurs.getText() + ", " + auteur.getGegevens().getNaam());
        }
    }
    @FXML public void getBeschrijving() throws Exception {
        tbBeschrijving.setText("");
        if (lsGeleendeBoeken != null) {
            Gebruiker gebruiker = appManager.zoekGebruiker(String.valueOf(lsGebruikersLijst.getSelectionModel().getSelectedItem()));
            BoekExemplaar boek = gebruiker.getGeleendBoek(appManager.zoekBoekExemplaar(Integer.parseInt(String.valueOf(lsGeleendeBoeken.getSelectionModel().getSelectedItem()))).getBoek().getTitel());
            tbBeschrijving.setText(boek.getBeschrijving());
        }
        if (lsBeschikbareExemplaren != null) {
            BoekExemplaar boek = appManager.zoekBoekExemplaar(Integer.parseInt(String.valueOf(lsBeschikbareExemplaren.getSelectionModel().getSelectedItem())));
            tbBeschrijving.setText(boek.getBeschrijving());
        }
        if (btnRetourneer != null) btnRetourneer.setDisable(false);
        if (btnBeschrijvingWijzigen != null) btnBeschrijvingWijzigen.setDisable(false);
    }
    @FXML public void selecteerAuteur(ActionEvent e) throws Exception {
        if (tbAuteurs.getText().equals("")) tbAuteurs.setText(cbAuteurs.getSelectionModel().getSelectedItem().toString());
        else tbAuteurs.setText(tbAuteurs.getText() + ", " + cbAuteurs.getSelectionModel().getSelectedItem().toString());
        cbAuteurs.setPromptText("Lijst van eerdere auteurs");
    }
    @FXML public void selecteerUitgever(ActionEvent e) throws Exception {
        tbUitgever.setText(cbUitgever.getSelectionModel().getSelectedItem().toString());
        cbUitgever.setPromptText("Lijst van eerdere uitgevers");
    }
    @FXML public void openMijnBoeken(ActionEvent e) throws Exception {
        scherm = FXMLLoader.load(getClass().getResource("MijnBoeken (Klant).fxml"));
        showWindow(e);
    }
    @FXML public void openMijnProfiel(ActionEvent e) throws Exception {
        if (appManager.getGebruiker().getStatus().equals("Medewerker")) {
            scherm = FXMLLoader.load(getClass().getResource("MijnProfiel (Medewerker).fxml"));
        }
        else scherm = FXMLLoader.load(getClass().getResource("MijnProfiel (Klant).fxml"));
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
    @FXML public void openGebruikerslijst(ActionEvent e) throws Exception {
        scherm = FXMLLoader.load(getClass().getResource("GebruikersLijst (Medewerker).fxml"));
        showWindow(e);
    }
    @FXML public void laadGegevens() throws Exception {
        if (lsMijnBoeken != null) { lsMijnBoeken.setItems(mijnBoeken); }
        if (lsBoekenLijst != null) { lsBoekenLijst.setItems(boeken); }
        if (lsGebruikersLijst != null) { lsGebruikersLijst.setItems(gebruikers); }
        if (cbAuteurs != null && cbUitgever != null) { cbAuteurs.setItems(auteurs);cbUitgever.setItems(uitgevers); }
    }
    @FXML public void enableBtnLeenUit() throws Exception {
        if (lsBeschikbareExemplaren.getSelectionModel().getSelectedItem() != null && lsGebruikersLijst.getSelectionModel().getSelectedItem() != null) btnLeenUit.setDisable(false);
    }
    public BoekController() throws Exception { }
}

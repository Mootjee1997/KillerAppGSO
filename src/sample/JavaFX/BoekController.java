package sample.JavaFX;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import sample.Core.AppManager;
import sample.Models.Boek;
import sample.Models.Gebruiker;

public class BoekController {
    private AppManager appManager = AppManager.getInstance();
    private Gebruiker gebruiker = appManager.getGebruiker();
    private ObservableList<Boek> boeken = FXCollections.observableArrayList(appManager.getBoeken());

    @FXML private ListView<Boek> lsBoekenLijst;
    @FXML private TextField titelBl;
    @FXML private TextField descriptieBl;
    @FXML private TextField totAantalBl;
    @FXML private TextField beschikbaarBl;
    @FXML private TextField auteurBl;
    @FXML private TextField uitgeverBl;
    @FXML private Button btnMijnProfiel;
    @FXML private Button btnBoekenLijst;
    @FXML private Button btnMijnBoeken;
    @FXML private Button btnLeenUit;

    @FXML public void laadBoeken() throws Exception {
        lsBoekenLijst.setItems(boeken);
    }

    @FXML public void getBoekInfo() throws Exception {
        Boek boek = appManager.zoekBoek(String.valueOf(lsBoekenLijst.getSelectionModel().getSelectedItems()));
        titelBl.setText(boek.getTitel());
        descriptieBl.setText(boek.getTitel());
        totAantalBl.setText(String.valueOf(boek.getBoekExemplaren().size()));
        beschikbaarBl.setText(String.valueOf(boek.getAantalBeschikbaar()));
        uitgeverBl.setText(boek.getUitgever().getGegevens().getNaam());
    }

    public BoekController() throws Exception {
    }
}

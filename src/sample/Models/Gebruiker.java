package sample.Models;
import sample.Core.AppManager;

import java.io.Serializable;
import java.util.ArrayList;

public class Gebruiker implements Serializable {
    private int id;
    private String status, gebruikersnaam, wachtwoord;
    private Gegevens gegevens;
    private ArrayList<BoekExemplaar> geleendeBoeken;

    //Getters voor de attributen
    public int getId (){
        return this.id;
    }
    public String getStatus (){return this.status; }
    public String getGebruikersnaam (){
        return this.gebruikersnaam;
    }
    public String getWachtwoord () {return this.wachtwoord; }
    public Gegevens getGegevens() {
        return gegevens;
    }
    public ArrayList<BoekExemplaar> getGeleendeBoeken() {
        return geleendeBoeken;
    }

    //Setters voor de attributen
    public void setId(int id) {
        this.id = id;
    }

    //Constructor voor het aanmaken van een account zonder telefoonNr
    public Gebruiker(String gebruikersnaam, String wachtwoord, Gegevens gegevens){
        this.gebruikersnaam = gebruikersnaam;
        this.wachtwoord = wachtwoord;
        this.status = "Klant";
        this.gegevens = gegevens;
        this.geleendeBoeken = new ArrayList<>();
    }

    //Constructor voor het ophalen van een gebruiker uit de database
    public Gebruiker(int id, String gebruikersnaam, String wachtwoord, String status, Gegevens gegevens){
        this.id = id;
        this.gebruikersnaam = gebruikersnaam;
        this.wachtwoord = wachtwoord;
        this.status = status;
        this.gegevens = gegevens;
        this.geleendeBoeken = new ArrayList<>();
    }

    //Methode voor het wijzigen van de accountgegevens
    public boolean wijzigGegevens (String wachtwoord, Gegevens gegevens) throws Exception {
        this.wachtwoord = wachtwoord;
        this.gegevens = gegevens;
        return AppManager.getInstance().getServer().wijzigGegevens(this);
    }

    public void addGeleendeBoek(BoekExemplaar boekExemplaar) {
        this.geleendeBoeken.add(boekExemplaar);
    }

    public void deleteGeleendeBoek(BoekExemplaar boekExemplaar) {
        this.geleendeBoeken.remove(boekExemplaar);
    }
}

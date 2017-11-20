package sample.Models;
import java.util.ArrayList;

public class Gebruiker {
    private int id;
    private String status;
    private String gebruikersnaam;
    private String wachtwoord;
    private String naam;
    private String email;
    private String woonplaats;
    private String telefoonNr;
    private ArrayList<Boek> geleendeBoeken;

    //Getters/setters voor de attributen
    public int getId (){
        return this.id;
    }
    public String getStatus (){return this.status; }
    public String getGebruikersnaam (){
        return this.gebruikersnaam;
    }
    public String getWachtwoord () {return this.wachtwoord; }
    public String getNaam (){
        return this.naam;
    }
    public String getEmail (){
        return this.email;
    }
    public String getWoonplaats (){
        return this.woonplaats;
    }
    public String getTelefoonNr (){
        return this.telefoonNr;
    }
    public ArrayList<Boek> getGeleendeBoeken() {
        return geleendeBoeken;
    }
    public void setGeleendeBoeken (Boek boek) {
        this.geleendeBoeken.add(boek);
        //ToDO: voeg het toe aan database
    }

    //Constructor voor het aanmaken van een account zonder telefoonNr
    public Gebruiker(String gebruikersnaam, String wachtwoord, String naam, String email, String woonplaats){
        this.gebruikersnaam = gebruikersnaam;
        this.wachtwoord = wachtwoord;
        this.naam = naam;
        this.email = email;
        this.woonplaats = woonplaats;
        this.geleendeBoeken = new ArrayList<>();
    }
    //Constructor voor het aanmaken van een account met telefoonNr
    public Gebruiker(String gebruikersnaam, String wachtwoord, String naam, String email, String woonplaats, String telefoonNr){
        this.gebruikersnaam = gebruikersnaam;
        this.wachtwoord = wachtwoord;
        this.naam = naam;
        this.email = email;
        this.woonplaats = woonplaats;
        this.telefoonNr = telefoonNr;
        this.geleendeBoeken = new ArrayList<>();
    }
    //Constructor voor het ophalen van een gebruiker uit de database
    public Gebruiker(int id, String gebruikersnaam, String wachtwoord, String naam, String email, String woonplaats, String telefoonNr){
        this.id = id;
        this.gebruikersnaam = gebruikersnaam;
        this.wachtwoord = wachtwoord;
        this.naam = naam;
        this.email = email;
        this.woonplaats = woonplaats;
        this.telefoonNr = telefoonNr;
        this.geleendeBoeken = new ArrayList<>();
    }

    //Methode voor het wijzigen van de accountgegevens
    public boolean wijzigGegevens (String wachtwoord, String naam, String email, String woonplaats, String telefoonNr) {
        this.wachtwoord = wachtwoord;
        this.naam = naam;
        this.email = email;
        this.woonplaats = woonplaats;
        this.telefoonNr = telefoonNr;
        return true;
    }
}
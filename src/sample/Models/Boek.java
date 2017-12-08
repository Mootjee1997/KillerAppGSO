package sample.Models;
import sample.Repositories.BoekRepository;

public class Boek {
    private int id;
    private String titel;
    private String descriptie;
    private int aantalBeschikbaar;
    private int aantalExemplaren;
    private Auteur auteur;
    private Uitgever uitgever;
    private BoekRepository boekRepository;

    //Getters voor de attributen
    public int getId (){return this.id; }
    public String getTitel () {return  this.titel; }
    public String getDescriptie () {return  this.descriptie; }
    public int getAantalBeschikbaar (){return this.aantalBeschikbaar; }
    public int getAantalExemplaren (){return this.aantalExemplaren; }
    public Auteur getAuteur() { return auteur; }
    public Uitgever getUitgever() {
        return uitgever;
    }

    //Constructor voor het aanmaken van een boek
    public Boek(String titel, String descriptie, int aantalBeschikbaar, int aantalExemplaren, Auteur auteur, Uitgever uitgever){
        this.titel = titel;
        this.descriptie = descriptie;
        this.aantalBeschikbaar = aantalBeschikbaar;
        this.aantalExemplaren = aantalExemplaren;
        this.auteur = auteur;
        this.uitgever = uitgever;
    }
    //Constructor voor het ophalen van een boek uit de database
    public Boek(int id, String titel, String descriptie, int aantalBeschikbaar, int aantalExemplaren){
        this.id = id;
        this.titel = titel;
        this.descriptie = descriptie;
        this.aantalBeschikbaar = aantalBeschikbaar;
        this.aantalExemplaren = aantalExemplaren;
    }

    public boolean leenUit(Gebruiker gebruiker) throws Exception {
        this.aantalBeschikbaar--;
        gebruiker.setGeleendeBoeken(this);
        return boekRepository.leenUit(this, gebruiker);
    }

    public boolean retourneer(Gebruiker gebruiker) throws Exception {
        gebruiker.getGeleendeBoeken().remove(this);
        this.aantalBeschikbaar++;
        return boekRepository.retourneer(this, gebruiker);
    }
}

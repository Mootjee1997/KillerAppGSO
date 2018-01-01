package sample.Models;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Boek implements Serializable {
    private int id;
    private String titel, descriptie;
    private ArrayList<Auteur> auteurs;
    private Uitgever uitgever;
    private ArrayList<BoekExemplaar> boekExemplaren;

    //Getters voor de attributen
    public int getId (){return this.id; }
    public String getTitel () {return  this.titel; }
    public String getDescriptie () {return  this.descriptie; }
    public ArrayList<Auteur> getAuteurs() { return auteurs; }
    public Uitgever getUitgever() {
        return uitgever;
    }
    public ArrayList<BoekExemplaar> getBoekExemplaren() {
        return boekExemplaren;
    }

    //Setters voor de attributen
    public void setId(int id) {
        this.id = id;
    }
    public void setUitgever(Uitgever uitgever) {
        this.uitgever = uitgever;
    }
    public void setAuteurs(Auteur auteur) {
        this.auteurs.add(auteur);
    }
    public void setBoekExemplaren(BoekExemplaar boekExemplaar) {this.boekExemplaren.add(boekExemplaar); }

    //Constructor voor het aanmaken van een boek
    public Boek(String titel, String descriptie, ArrayList<Auteur> auteurs, Uitgever uitgever){
        this.titel = titel;
        this.descriptie = descriptie;
        this.auteurs = auteurs;
        this.uitgever = uitgever;
        this.boekExemplaren = new ArrayList<>();
    }

    //Constructor voor het ophalen van een boek uit de database
    public Boek(int id, String titel, String descriptie){
        this.id = id;
        this.titel = titel;
        this.descriptie = descriptie;
        this.boekExemplaren = new ArrayList<>();
        this.auteurs = new ArrayList<>();
    }

    public int getAantalBeschikbaar(){
        int i = 0;
        for (BoekExemplaar boek : boekExemplaren) {
            if (boek.getBeschikbaar()) {
                i++;
            }
        }
        return i;
    }

    public BoekExemplaar getExemplaar() {
        for (BoekExemplaar b : boekExemplaren) {
            if (b.getBeschikbaar()) {
                b.setBeschikbaar(false);
                return b;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return this.titel;
    }
}

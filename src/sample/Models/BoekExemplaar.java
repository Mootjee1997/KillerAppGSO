package sample.Models;
import java.io.Serializable;

public class BoekExemplaar implements Serializable {
    private int id;
    private Boek boek;
    private String beschrijving;
    private boolean beschikbaar;

    //Getters voor de attributen
    public int getId (){return this.id; }
    public Boek getBoek() {return this.boek; }
    public String getBeschrijving() {
        return beschrijving;
    }
    public boolean getBeschikbaar() {return this.beschikbaar; }

    //Setters voor de attributen
    public void setBoek(Boek boek) {
        this.boek = boek;
    }
    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }
    public void setBeschikbaar(boolean beschikbaar) {
        this.beschikbaar = beschikbaar;
    }

    //Constructor voor het aanmaken van een boek
    public BoekExemplaar(Boek boek, String beschrijving){
        this.boek = boek;
        this.beschrijving = beschrijving;
        this.beschikbaar = true;
    }

    //Constructor voor het ophalen van een boek uit de database
    public BoekExemplaar(int id, Boek boek, String beschrijving, boolean beschikbaar){
        this.id = id;
        this.boek = boek;
        this.beschrijving = beschrijving;
        this.beschikbaar = beschikbaar;
    }

    //Constructor voor het ophalen van een boek uit de database
    public BoekExemplaar(int id, String beschrijving, boolean beschikbaar){
        this.id = id;
        this.beschrijving = beschrijving;
        this.beschikbaar = beschikbaar;
    }

    public void retourneer(){
        this.beschikbaar = true;
    }
}

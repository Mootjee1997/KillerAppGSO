package sample.Models;
import sample.Core.AppManager;
import java.io.Serializable;
import java.util.ArrayList;

public class Boek implements Serializable {
    private int id;
    private String titel, descriptie;
    private ArrayList<BoekExemplaar> boekExemplaren;
    private ArrayList<Auteur> auteurs;
    private Uitgever uitgever;

    public int getId (){return this.id; }
    public String getTitel () {return  this.titel; }
    public String getDescriptie () {return  this.descriptie; }
    public ArrayList<Auteur> getAuteurs() { return auteurs; }
    public Uitgever getUitgever() {
        return uitgever;
    }
    public ArrayList<BoekExemplaar> getBoekExemplaren() throws Exception {
        ArrayList<BoekExemplaar> exemplaren = new ArrayList<>();
        for (BoekExemplaar boek : boekExemplaren) {
            exemplaren.add(AppManager.getInstance().zoekBoekExemplaar(boek.getVolgnummer()));
        }
        return exemplaren;
    }
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

    public Boek(String titel, String descriptie, ArrayList<Auteur> auteurs, Uitgever uitgever){
        this.titel = titel;
        this.descriptie = descriptie;
        this.auteurs = auteurs;
        this.uitgever = uitgever;
        this.boekExemplaren = new ArrayList<>();
    }

    public Boek(int id, String titel, String descriptie){
        this.id = id;
        this.titel = titel;
        this.descriptie = descriptie;
        this.auteurs = new ArrayList<>();
        this.boekExemplaren = new ArrayList<>();
    }

    public int getAantalBeschikbaar() throws Exception {
        int i = 0;
        for (BoekExemplaar boek : getBoekExemplaren()) if (boek.getBeschikbaar()) i++;
        return i;
    }

    @Override
    public String toString() {
        return this.titel;
    }
}

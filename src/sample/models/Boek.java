package sample.models;
import sample.core.AppManager;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class Boek implements Serializable {
    private int id;
    private String titel;
    private String descriptie;
    private Uitgever uitgever;
    private List<BoekExemplaar> boekExemplaren;
    private List<Auteur> auteurs;

    public int getId (){return this.id; }
    public String getTitel () {return  this.titel; }
    public String getDescriptie () {return  this.descriptie; }
    public Uitgever getUitgever() {
        return uitgever;
    }
    public List<Auteur> getAuteurs() { return auteurs; }
    public List<BoekExemplaar> getBoekExemplaren() throws RemoteException {
        ArrayList<BoekExemplaar> exemplaren = new ArrayList<>();
        for (BoekExemplaar boek : boekExemplaren) {
            exemplaren.add(AppManager.getInstance().zoekBoekExemplaar(boek.getVolgnummer()));
        }
        return exemplaren;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setAuteurs(Auteur auteur) {
        this.auteurs.add(auteur);
    }
    public void setUitgever(Uitgever uitgever) {
        this.uitgever = uitgever;
    }
    public void setBoekExemplaren(BoekExemplaar boekExemplaar) {this.boekExemplaren.add(boekExemplaar); }

    public Boek(String titel, String descriptie, List<Auteur> auteurs, Uitgever uitgever){
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

    public int getAantalBeschikbaar() throws RemoteException {
        int i = 0;
        for (BoekExemplaar boek : getBoekExemplaren()) if (boek.getBeschikbaar()) i++;
        return i;
    }

    @Override
    public String toString() {
        return this.titel;
    }
}

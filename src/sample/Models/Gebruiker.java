package sample.Models;
import sample.Core.AppManager;
import sample.Enums.Status;
import java.io.Serializable;
import java.util.ArrayList;

public class Gebruiker implements Serializable {
    private int id;
    private String gebruikersnaam, wachtwoord;
    private Status status;
    private Gegevens gegevens;
    private ArrayList<BoekExemplaar> geleendeBoeken;

    public int getId (){
        return this.id;
    }
    public Status getStatus (){return this.status; }
    public String getGebruikersnaam (){
        return this.gebruikersnaam;
    }
    public String getWachtwoord () {return this.wachtwoord; }
    public Gegevens getGegevens() {
        return gegevens;
    }
    public ArrayList<String> getGeleendeBoeken() throws Exception {
        ArrayList<String> geleend = new ArrayList<>();
        for (BoekExemplaar b : geleendeBoeken) {
            geleend.add(String.valueOf(b.getVolgnummer()));
        }
        return geleend;
    }
    public void setId(int id) {
        this.id = id;
    }

    public Gebruiker(String gebruikersnaam, String wachtwoord, Gegevens gegevens){
        this.gebruikersnaam = gebruikersnaam;
        this.wachtwoord = wachtwoord;
        this.status = Status.KLANT;
        this.gegevens = gegevens;
        this.geleendeBoeken = new ArrayList<>();
    }

    public Gebruiker(int id, String gebruikersnaam, String wachtwoord, Status status, Gegevens gegevens){
        this.id = id;
        this.gebruikersnaam = gebruikersnaam;
        this.wachtwoord = wachtwoord;
        this.status = status;
        this.gegevens = gegevens;
        this.geleendeBoeken = new ArrayList<>();
    }

    public boolean wijzigGegevens (Gegevens gegevens) throws Exception {
        this.gegevens = gegevens;
        return AppManager.getInstance().getServer().wijzigGegevens(this);
    }
    public boolean wijzigGegevens (String wachtwoord, Gegevens gegevens) throws Exception {
        this.wachtwoord = wachtwoord;
        this.gegevens = gegevens;
        return AppManager.getInstance().getServer().wijzigGegevens(this);
    }
    public void addGeleendeBoek(BoekExemplaar boekExemplaar) {
        this.geleendeBoeken.add(boekExemplaar);
    }
    public void deleteGeleendeBoek(int volgnummer) {
        BoekExemplaar boekExemplaar = null;
        for (BoekExemplaar boek : geleendeBoeken) {
            if (boek.getVolgnummer() == volgnummer) {
                boekExemplaar = boek;
            }
        }
        geleendeBoeken.remove(boekExemplaar);
    }

    @Override
    public String toString() {
        return this.gebruikersnaam;
    }
}

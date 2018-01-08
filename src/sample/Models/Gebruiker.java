package sample.Models;
import sample.Core.AppManager;
import java.io.Serializable;
import java.util.ArrayList;

public class Gebruiker implements Serializable {
    private int id;
    private String status, gebruikersnaam, wachtwoord;
    private Gegevens gegevens;
    private ArrayList<BoekExemplaar> geleendeBoeken;

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
    public ArrayList<BoekExemplaar> getGeleendeBoeken() throws Exception {
        ArrayList<BoekExemplaar> geleend = new ArrayList<>();
        for (BoekExemplaar b : geleendeBoeken) {
            geleend.add(AppManager.getInstance().zoekBoekExemplaar(b.getVolgnummer()));
        }
        return geleend;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Gebruiker(String gebruikersnaam, String wachtwoord, Gegevens gegevens){
        this.gebruikersnaam = gebruikersnaam;
        this.wachtwoord = wachtwoord;
        this.status = "Klant";
        this.gegevens = gegevens;
        this.geleendeBoeken = new ArrayList<>();
    }

    public Gebruiker(int id, String gebruikersnaam, String wachtwoord, String status, Gegevens gegevens){
        this.id = id;
        this.gebruikersnaam = gebruikersnaam;
        this.wachtwoord = wachtwoord;
        this.status = status;
        this.gegevens = gegevens;
        this.geleendeBoeken = new ArrayList<>();
    }

    public boolean wijzigGegevens (String wachtwoord, Gegevens gegevens) throws Exception {
        this.wachtwoord = wachtwoord;
        this.gegevens = gegevens;
        return AppManager.getInstance().getServer().wijzigGegevens(this);
    }

    public void addGeleendeBoek(BoekExemplaar boekExemplaar) {
        boekExemplaar.setBeschikbaar(false);
        this.geleendeBoeken.add(boekExemplaar);
    }

    public void deleteGeleendeBoek(BoekExemplaar boekExemplaar) {
        boekExemplaar.setBeschikbaar(true);
        this.geleendeBoeken.remove(boekExemplaar);
    }

    public BoekExemplaar getGeleendBoek(String titel) throws Exception {
        for (BoekExemplaar boek : getGeleendeBoeken()) {
            if (boek.getBoek().getTitel().equals(titel)) { return boek; }
        }
        return null;
    }

    @Override
    public String toString() {
        return this.gebruikersnaam;
    }
}

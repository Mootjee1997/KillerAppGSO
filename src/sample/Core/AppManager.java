package sample.Core;
import sample.Contexts.IBoekContext;
import sample.Contexts.IGebruikerContext;
import sample.Models.Boek;
import sample.Models.Gebruiker;
import sample.Repositories.BoekRepository;
import sample.Repositories.GebruikerRepository;
import java.util.ArrayList;

public class AppManager {
    private static Gebruiker gebruiker;
    private static GebruikerRepository gebruikerRepository;
    private static BoekRepository boekRepository;
    private ArrayList<Gebruiker> gebruikers;
    private ArrayList<Boek> boeken;

    public Gebruiker getGebruiker() {
        return gebruiker;
    }

    public AppManager() {
        this.gebruikerRepository = new GebruikerRepository(new IGebruikerContext());
        this.boekRepository = new BoekRepository(new IBoekContext());
        this.gebruikers = new ArrayList<>();
        this.boeken = new ArrayList<>();
    }

    public static Gebruiker login(String gebruikernaam, String wachtwoord) throws Exception {
        return gebruiker = gebruikerRepository.login(gebruikernaam, wachtwoord);
    }

    public boolean registreer(Gebruiker gebruiker) throws  Exception {
        return this.gebruikers.add(gebruikerRepository.registreer(gebruiker));
    }

    public Gebruiker zoekGebruiker(String gebruikernaam){
        for (Gebruiker g: gebruikers) {
            if (g.getGebruikersnaam() == gebruikernaam) { return g; }
        }
        return null;
    }

    public ArrayList<Boek> zoekBoek(String titel){
        ArrayList<Boek> zoekresultaten = new ArrayList<>();
        for (Boek b: boeken) {
            if (b.getTitel() == titel) { zoekresultaten.add(b); }
        }
        return zoekresultaten;
    }

    public boolean addBoek(Boek boek) throws Exception {
        return this.boeken.add(boekRepository.addBoek(boek));
    }

    public void loguit(){
        this.gebruiker = null;
    }
}

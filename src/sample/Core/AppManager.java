package sample.Core;
import fontys.IRemotePropertyListener;
import fontys.IRemotePublisherForListener;
import sample.Models.*;
import sample.Server.*;
import java.beans.PropertyChangeEvent;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class AppManager implements IRemotePropertyListener {
    private static AppManager appManager = null;
    public static AppManager getInstance() throws Exception {
        if (appManager == null) {
            appManager = new AppManager();
        }
        return appManager;
    }
    private IRemotePublisherForListener publisherForListener;
    private IServer server;
    private Gebruiker gebruiker;
    private ArrayList<Auteur> auteurs;
    private ArrayList<Uitgever> uitgevers;
    private ArrayList<Boek> boeken;
    private ArrayList<BoekExemplaar> boekExemplaren;
    private ArrayList<Gebruiker> gebruikers;

    public AppManager() throws Exception {
        server = (IServer) Naming.lookup("rmi://localhost:1099/Server");
        getGebruikers();
        getBoeken();
        getBoekExemplaren();
    }

    public Gebruiker login(String gebruikernaam, String wachtwoord) throws Exception {
        return gebruiker = server.login(gebruikernaam, wachtwoord);
    }
    public void registreer(Gebruiker gebruiker) throws  Exception {
        gebruiker.setId(server.registreer(gebruiker));
        gebruikers.add(gebruiker);
    }

    public boolean leenUit(int volgnummer, Boek boek, Gebruiker gebruiker) throws Exception {
        return server.leenUit(volgnummer, boek, gebruiker);
    }
    public boolean retourneer(BoekExemplaar boek, Gebruiker gebruiker) throws Exception {
        return server.retourneer(boek, gebruiker);
    }

    public boolean addAuteur(Auteur auteur) throws Exception {
        return server.addAuteur(auteur);
    }
    public boolean addUitgever(Uitgever uitgever) throws Exception {
        return server.addUitgever(uitgever);
    }
    public boolean addBoek(Boek boek) throws Exception {
        return server.addBoek(boek);
    }

    public Auteur zoekAuteur(String naam) {
        for (Auteur a : auteurs) { if (a.getGegevens().getNaam().equals(naam)) { return a; } }
        return null;
    }
    public Uitgever zoekUitgever(String naam) {
        for (Uitgever u : uitgevers) { if (u.getGegevens().getNaam().equals(naam)) { return u; } }
        return null;
    }
    public Boek zoekBoek(String titel) throws RemoteException {
        for (Boek b: boeken) { if (b.getTitel().equals(titel)) { return b; } }
        return null;
    }
    public BoekExemplaar zoekBoekExemplaar(int volgnummer) throws RemoteException {
        for (BoekExemplaar b: boekExemplaren) { if (b.getVolgnummer() == volgnummer) { return b; } }
        return null;
    }
    public Gebruiker zoekGebruiker(String gebruikernaam) throws RemoteException {
        for (Gebruiker g: gebruikers) { if (g.getGebruikersnaam() == gebruikernaam) { return g; } }
        return null;
    }

    public ArrayList<Auteur> getAuteurs() throws Exception {
        if (auteurs == null) { auteurs = server.getAuteurs(); }
        return auteurs;
    }
    public ArrayList<Uitgever> getUitgevers() throws Exception {
        if (uitgevers == null) { uitgevers = server.getUitgevers(); }
        return uitgevers;
    }
    public ArrayList<Boek> getBoeken() throws Exception {
        if (boeken == null){ boeken = server.getBoeken(); }
        return boeken;
    }
    public ArrayList<BoekExemplaar> getBoekExemplaren() throws Exception {
        if (boekExemplaren == null){ boekExemplaren = server.getBoekExemplaren(); }
        return boekExemplaren;
    }
    public ArrayList<Gebruiker> getGebruikers() throws Exception {
        if (gebruikers == null){ gebruikers = server.getGebruikers(); }
        return gebruikers;
    }

    public void setBeschrijving(BoekExemplaar boekExemplaar) throws Exception {
        zoekBoekExemplaar(boekExemplaar.getVolgnummer()).setBeschrijving(boekExemplaar.getBeschrijving());
        server.setBeschrijving(boekExemplaar);
    }
    public Gebruiker getGebruiker() {
        return gebruiker;
    }
    public IServer getServer() {
        return server;
    }
    public void loguit(){
        gebruiker = null;
    }

    public void propertyChange(PropertyChangeEvent propertyChangeEvent) throws RemoteException {

    }
}

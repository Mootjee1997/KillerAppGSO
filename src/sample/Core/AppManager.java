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
    }

    public boolean leenUit(int volgnummer, Gebruiker gebruiker) throws Exception {
        return server.leenUit(volgnummer, gebruiker);
    }
    public boolean retourneer(int volgnummer, Gebruiker gebruiker) throws Exception {
        return server.retourneer(volgnummer, gebruiker);
    }

    public boolean addAuteur(Auteur auteur) throws Exception {
        return server.addAuteur(auteur);
    }
    public boolean addUitgever(Uitgever uitgever) throws Exception {
        return server.addUitgever(uitgever);
    }
    public void addBoek(Boek boek, String aantalExemplaren) throws Exception {
        server.addBoek(boek, aantalExemplaren);
    }

    public Auteur zoekAuteur(String naam) throws Exception {
        return server.zoekAuteur(naam);
    }
    public Uitgever zoekUitgever(String naam) throws Exception {
        return server.zoekUitgever(naam);
    }
    public Boek zoekBoek(String titel) throws Exception {
        return server.zoekBoek(titel);
    }
    public BoekExemplaar zoekBoekExemplaar(int volgnummer) throws Exception {
        return server.zoekBoekExemplaar(volgnummer);
    }
    public Gebruiker zoekGebruiker(String gebruikernaam) throws Exception {
        return server.zoekGebruiker(gebruikernaam);
    }

    public ArrayList<String> getAuteurs() throws Exception {
        return server.getAuteurs();
    }
    public ArrayList<String> getUitgevers() throws Exception {
        return server.getUitgevers();
    }
    public ArrayList<String> getBoeken() throws Exception {
        return server.getBoeken();
    }
    public ArrayList<String> getBoekExemplaren() throws Exception {
        return server.getBoekExemplaren();
    }
    public ArrayList<String> getGebruikers() throws Exception {
        return server.getGebruikers();
    }
    public ArrayList<String> getGeleendeBoeken(Gebruiker gebruiker)throws Exception {
        return server.getGeleendeBoeken(gebruiker);
    }
    public ArrayList<String> getBeschikbareExemplaren(String titel) throws Exception {
        return server.getBeschikbareExemplaren(titel);
    }
    public void setBeschrijving(int volgnummer, String beschrijving) throws Exception {
        server.setBeschrijving(volgnummer, beschrijving);
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

    public void propertyChange(PropertyChangeEvent evt) throws RemoteException {

    }
}

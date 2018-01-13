package sample.Core;
import fontys.IRemotePropertyListener;
import sample.JavaFX.BoekController;
import sample.JavaFX.GebruikerController;
import sample.Models.*;
import sample.Server.*;
import java.beans.PropertyChangeEvent;
import java.io.Serializable;
import java.rmi.Naming;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class AppManager extends UnicastRemoteObject implements IRemotePropertyListener, Serializable {
    private static AppManager appManager = null;
    public static AppManager getInstance() throws Exception {
        if (appManager == null) {
            appManager = new AppManager();
        }
        return appManager;
    }
    public AppManager() throws Exception {
        server = (IServer) Naming.lookup("rmi://localhost:1099/Server");
        server.subscribePublisher(this, "BoekAdd");
        server.subscribePublisher(this, "GebruikerAdd");
        server.subscribePublisher(this, "MijnBoeken");
    }
    private IServer server;
    private Gebruiker gebruiker;
    private BoekController boekController;
    public void setBoekController(BoekController boekController) {
        this.boekController = boekController;
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
        return server.zoekGebruiker(gebruikernaam.toLowerCase());
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

    public void propertyChange(PropertyChangeEvent evt) {
        try {
            if (evt.getPropertyName().equals("BoekAdd")) {
                boekController.updateBoekList((ArrayList<String>) evt.getNewValue());
            }
            if (evt.getPropertyName().equals("GebruikerAdd")) {
                boekController.updateGebruikersList((ArrayList<String>) evt.getNewValue());
            }
            if (evt.getPropertyName().equals("MijnBoeken")) {
                boekController.updateMijnBoeken((ArrayList<String>) evt.getNewValue());
            }
        }
        catch (Exception ex) {
            System.out.println(ex);
        }
    }
}

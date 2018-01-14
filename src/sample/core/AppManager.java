package sample.core;
import fontys.IRemotePropertyListener;
import sample.javafx.BoekController;
import sample.models.*;
import sample.server.*;
import java.beans.PropertyChangeEvent;
import java.io.Serializable;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AppManager extends UnicastRemoteObject implements IRemotePropertyListener, Serializable {
    public void setBoekController(BoekController boekController) {
        this.boekController = boekController;
    }
    private transient Logger logger = Logger.getLogger("Logger");
    private static AppManager appManager = null;
    public static AppManager getInstance() {
        if (appManager == null) {
            try {
                appManager = new AppManager();
            }
            catch (Exception ex) {
                System.out.println(ex);
            }
        }
        return appManager;
    }
    public AppManager() throws RemoteException {
        try {
            server = (IServer) Naming.lookup("rmi://localhost:1099/server");
            server.subscribePublisher(this, "BoekAdd");
            server.subscribePublisher(this, "GebruikerAdd");
            server.subscribePublisher(this, "MijnBoeken");
        }
        catch(Exception ex) {
            logger.log( Level.WARNING, ex.toString(), ex);
        }
    }
    private IServer server;
    private Gebruiker gebruiker;
    private BoekController boekController;

    public Gebruiker login(String gebruikernaam, String wachtwoord) throws RemoteException {
        gebruiker = server.login(gebruikernaam, wachtwoord);
        return gebruiker;
    }
    public void registreer(Gebruiker gebruiker) throws  RemoteException {
        gebruiker.setId(server.registreer(gebruiker));
    }

    public boolean leenUit(int volgnummer, Gebruiker gebruiker) throws RemoteException {
        return server.leenUit(volgnummer, gebruiker);
    }
    public boolean retourneer(int volgnummer, Gebruiker gebruiker) throws RemoteException {
        return server.retourneer(volgnummer, gebruiker);
    }

    public boolean addAuteur(Auteur auteur) throws RemoteException {
        return server.addAuteur(auteur);
    }
    public boolean addUitgever(Uitgever uitgever) throws RemoteException {
        return server.addUitgever(uitgever);
    }
    public void addBoek(Boek boek, String aantalExemplaren) throws RemoteException {
        server.addBoek(boek, aantalExemplaren);
    }

    public Auteur zoekAuteur(String naam) throws RemoteException {
        return server.zoekAuteur(naam);
    }
    public Uitgever zoekUitgever(String naam) throws RemoteException {
        return server.zoekUitgever(naam);
    }
    public Boek zoekBoek(String titel) throws RemoteException {
        return server.zoekBoek(titel);
    }
    public BoekExemplaar zoekBoekExemplaar(int volgnummer) throws RemoteException {
        return server.zoekBoekExemplaar(volgnummer);
    }
    public Gebruiker zoekGebruiker(String gebruikernaam) throws RemoteException {
        return server.zoekGebruiker(gebruikernaam);
    }

    public List<String> getAuteurs() throws RemoteException {
        return server.getAuteurs();
    }
    public List<String> getUitgevers() throws RemoteException {
        return server.getUitgevers();
    }
    public List<String> getBoeken() throws RemoteException {
        return server.getBoeken();
    }
    public List<String> getGebruikers() throws RemoteException {
        return server.getGebruikers();
    }
    public List<String> getGeleendeBoeken(Gebruiker gebruiker)throws RemoteException {
        return server.getGeleendeBoeken(gebruiker);
    }
    public List<String> getBeschikbareExemplaren(String titel) throws RemoteException {
        return server.getBeschikbareExemplaren(titel);
    }
    public void setBeschrijving(int volgnummer, String beschrijving) throws RemoteException {
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
                boekController.updateBoekList((List<String>) evt.getNewValue());
            }
            if (evt.getPropertyName().equals("GebruikerAdd")) {
                boekController.updateGebruikersList((List<String>) evt.getNewValue());
            }
            if (evt.getPropertyName().equals("MijnBoeken")) {
                boekController.updateMijnBoeken((List<String>) evt.getNewValue());
            }
        }
        catch (Exception ex) {
            logger.log( Level.WARNING, ex.toString(), ex);
        }
    }
}

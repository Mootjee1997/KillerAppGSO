package sample.Core;
import sample.Models.Boek;
import sample.Models.BoekExemplaar;
import sample.Models.Gebruiker;
import sample.Server.*;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class AppManager {
    private static AppManager appManager = null;
    public static AppManager getInstance() throws Exception {
        if (appManager == null) {
            appManager = new AppManager();
        }
        return appManager;
    }
    private IServer server;
    private Gebruiker gebruiker;
    private ArrayList<Boek> boeken;
    private ArrayList<Gebruiker> gebruikers;

    public AppManager() throws Exception {
        server = (IServer) Naming.lookup("rmi://localhost:1099/Server");
        getGebruikers();
        getBoeken();
    }

    public Gebruiker login(String gebruikernaam, String wachtwoord) throws Exception {
        return gebruiker = server.login(gebruikernaam, wachtwoord);
    }

    public void registreer(Gebruiker gebruiker) throws  Exception {
        server.registreer(gebruiker);
    }

    public Gebruiker zoekGebruiker(String gebruikernaam) throws RemoteException {
        for (Gebruiker g: gebruikers) {
            if (g.getGebruikersnaam() == gebruikernaam) { return g; }
        }
        return null;
    }

    public Boek zoekBoek(String titel) throws RemoteException {
        for (Boek b: boeken) {
            if (b.getTitel() == titel) { return b; }
        }
        return null;
    }

    public boolean leenUit(Boek boek, Gebruiker gebruiker) throws Exception {
        return server.leenUit(boek, gebruiker);
    }

    public boolean retourneer(BoekExemplaar boek, Gebruiker gebruiker) throws Exception {
        return server.retourneer(boek, gebruiker);
    }

    public boolean addBoek(Boek boek) throws Exception {
        return server.addBoek(boek);
    }

    public ArrayList<Boek> getBoeken() throws Exception {
        if (boeken == null){
            boeken = server.getBoeken();
        }
        return boeken;
    }

    public ArrayList<Gebruiker> getGebruikers() throws Exception {
        if (gebruikers == null){
            gebruikers = server.getGebruikers();
        }
        return gebruikers;
    }

    public void loguit(){
        gebruiker = null;
    }

    public Gebruiker getGebruiker() {
        return gebruiker;
    }
    public IServer getServer() {
        return server;
    }
}

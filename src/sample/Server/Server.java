package sample.Server;
import fontys.RemotePublisher;
import sample.Contexts.IBoekContext;
import sample.Contexts.IGebruikerContext;
import sample.Models.*;
import sample.Repositories.BoekRepository;
import sample.Repositories.GebruikerRepository;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;

public class Server extends UnicastRemoteObject implements IServer {
    private int port = 1099;
    private Registry registry = null;
    private RemotePublisher publisher;
    private ArrayList<Auteur> auteurs;
    private ArrayList<Uitgever> uitgevers;
    private ArrayList<Boek> boeken;
    private ArrayList<BoekExemplaar> boekExemplaren;
    private ArrayList<Gebruiker> gebruikers;
    private BoekRepository boekRepository = new BoekRepository(new IBoekContext());
    private GebruikerRepository gebruikerRepository = new GebruikerRepository(new IGebruikerContext());

    public Server() throws RemoteException {
        super();
        try {
            registry = createRegistry();

            if (registry != null) {
                registry.rebind("Server", this);
                System.out.println("Server: bound and ready for use.");
            }
            getGebruikers();
            getBoeken();
        }
        catch (Exception e){
            System.out.println("Server could not be bound.");
            e.printStackTrace();
        }
    }
    public Registry createRegistry(){
        try {
            registry = LocateRegistry.createRegistry(port);
            System.out.println("Server: created Registry on portnumber: " + port + ".");
        } catch (RemoteException e) {
            System.out.println("Could not create Registry.");
            e.printStackTrace();
        }
        return registry;
    }

    public Gebruiker login(String gebruikernaam, String wachtwoord) throws Exception {
        return gebruikerRepository.login(gebruikernaam, wachtwoord);
    }
    public int registreer(Gebruiker gebruiker) throws Exception {
        gebruiker.setId(gebruikerRepository.registreer(gebruiker));
        this.gebruikers.add(gebruiker);
        return gebruiker.getId();
    }

    public boolean leenUit(int volgnummer, Boek boek, Gebruiker gebruiker) throws Exception {
        BoekExemplaar boekExemplaar = zoekBoekExemplaar(volgnummer);
        zoekGebruiker(gebruiker.getGebruikersnaam()).addGeleendeBoek(boekExemplaar);
        return boekRepository.leenUit(boekExemplaar, gebruiker);
    }
    public boolean retourneer(BoekExemplaar boek, Gebruiker gebruiker) throws Exception {
        BoekExemplaar boekExemplaar = zoekBoekExemplaar(boek.getVolgnummer());
        zoekGebruiker(gebruiker.getGebruikersnaam()).deleteGeleendeBoek(boekExemplaar);
        return boekRepository.retourneer(boek, gebruiker);
    }

    public boolean addAuteur(Auteur auteur) throws SQLException, ClassNotFoundException {
        return boekRepository.addAuteur(auteur);
    }
    public boolean addUitgever(Uitgever uitgever) throws SQLException, ClassNotFoundException {
        return boekRepository.addUitgever(uitgever);
    }
    public boolean addBoek(Boek boek) throws Exception {
        for (BoekExemplaar boekExemplaar : boek.getBoekExemplaren()) { this.boekExemplaren.add(boekExemplaar); }
        this.boeken.add(boek);
        return this.boeken.add(boekRepository.addBoek(boek));
    }

    public Boek zoekBoek(String titel) throws RemoteException {
        for (Boek b: boeken) { if (b.getTitel().equals(titel)) { return b; } }
        return null;
    }
    public BoekExemplaar zoekBoekExemplaar(int volgnummer) throws RemoteException {
        for (BoekExemplaar b : boekExemplaren) {
            if (b.getVolgnummer() == volgnummer) {
                return b;
            }
        }
        return null;
    }
    public Gebruiker zoekGebruiker(String gebruikernaam) throws RemoteException {
        for (Gebruiker g: gebruikers) { if (g.getGebruikersnaam().equals(gebruikernaam)) { return g; } }
        return null;
    }

    public ArrayList<Auteur> getAuteurs() throws Exception {
        if (auteurs == null) { auteurs = boekRepository.getAuteurs(); }
        return auteurs;
    }
    public ArrayList<Uitgever> getUitgevers() throws Exception {
        if (uitgevers == null) { uitgevers = boekRepository.getUitgevers(); }
        return uitgevers;
    }
    public ArrayList<Boek> getBoeken() throws Exception {
        if (boeken == null){ boeken = boekRepository.getBoeken(); }
        return boeken;
    }
    public ArrayList<BoekExemplaar> getBoekExemplaren() throws Exception {
        if (boekExemplaren == null) { boekExemplaren = boekRepository.getBoekExemplaren(); }
        return boekExemplaren;
    }
    public ArrayList<Gebruiker> getGebruikers() throws Exception {
        if (gebruikers == null) { gebruikers = gebruikerRepository.getGebruikers(); }
        return gebruikers;
    }

    public boolean wijzigGegevens(Gebruiker gebruiker) throws Exception {
        return gebruikerRepository.wijzigGegevens(gebruiker);
    }
    public boolean setBeschrijving(BoekExemplaar boekExemplaar) throws Exception {
        zoekBoekExemplaar(boekExemplaar.getVolgnummer()).setBeschrijving(boekExemplaar.getBeschrijving());
        return boekRepository.setBeschrijving(boekExemplaar);
    }
}

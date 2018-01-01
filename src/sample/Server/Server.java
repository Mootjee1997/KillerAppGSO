package sample.Server;
import sample.Contexts.IBoekContext;
import sample.Contexts.IGebruikerContext;
import sample.Models.Boek;
import sample.Models.BoekExemplaar;
import sample.Models.Gebruiker;
import sample.Repositories.BoekRepository;
import sample.Repositories.GebruikerRepository;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Server extends UnicastRemoteObject implements IServer {
    private int port = 1099;
    private Registry registry = null;
    private ArrayList<Boek> boeken;
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

    @Override
    public Gebruiker login(String gebruikernaam, String wachtwoord) throws Exception {
        return gebruikerRepository.login(gebruikernaam, wachtwoord);
    }

    @Override
    public int registreer(Gebruiker gebruiker) throws Exception {
        return gebruikerRepository.registreer(gebruiker);
    }

    @Override
    public boolean addBoek(Boek boek) throws Exception {
        return this.boeken.add(boekRepository.addBoek(boek));
    }

    @Override
    public boolean leenUit(Boek boek, Gebruiker gebruiker) throws Exception {
        BoekExemplaar boekExemplaar = boek.getExemplaar();
        zoekGebruiker(gebruiker.getGebruikersnaam()).addGeleendeBoek(boekExemplaar);
        return boekRepository.leenUit(boekExemplaar, gebruiker);
    }

    @Override
    public boolean retourneer(BoekExemplaar boek, Gebruiker gebruiker) throws Exception {
        boek.retourneer();
        gebruiker.deleteGeleendeBoek(boek);
        return boekRepository.retourneer(boek, gebruiker);
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

    @Override
    public boolean wijzigGegevens(Gebruiker gebruiker) throws Exception {
        return gebruikerRepository.wijzigGegevens(gebruiker);
    }

    @Override
    public ArrayList<Boek> getBoeken() throws Exception {
        if (boeken == null){
            boeken = boekRepository.getBoeken();
            System.out.println("Booklist loaded from database and ready for use.");
        }
        return boeken;
    }

    @Override
    public ArrayList<Gebruiker> getGebruikers() throws Exception {
        if (gebruikers == null){
            gebruikers = gebruikerRepository.getGebruikers();
            System.out.println("Userlist loaded from database and ready for use.");
        }
        return gebruikers;
    }
}

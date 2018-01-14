package sample.server;
import fontys.IRemotePropertyListener;
import fontys.RemotePublisher;
import sample.contexts.IBoekContext;
import sample.contexts.IGebruikerContext;
import sample.enums.Status;
import sample.models.*;
import sample.repositories.BoekRepository;
import sample.repositories.GebruikerRepository;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server extends UnicastRemoteObject implements IServer {
    private int port = 1099;
    private static final String BOEKADD = "BoekAdd";
    private static final String GEBRUIKERADD = "GebruikerAdd";
    private static final String MIJNBOEKEN = "MijnBoeken";
    private transient Logger logger = Logger.getLogger("Logger");
    private transient Registry registry = null;
    private RemotePublisher publisher;
    private List<Auteur> auteurs;
    private List<Uitgever> uitgevers;
    private List<Gebruiker> gebruikers;
    private List<BoekExemplaar> boekExemplaren;
    private List<Boek> boeken;
    private BoekRepository boekRepository = new BoekRepository(new IBoekContext());
    private GebruikerRepository gebruikerRepository = new GebruikerRepository(new IGebruikerContext());

    public Server() throws RemoteException {
        super();
        try {
            registry = createRegistry();
            publisher = new RemotePublisher();

            if (registry != null) {
                IServer server = this;
                registry.rebind("server", server);
                publisher.registerProperty(BOEKADD);
                publisher.registerProperty(GEBRUIKERADD);
                publisher.registerProperty(MIJNBOEKEN);
                System.out.println("server: bound and ready for use.");
                getAuteurs();
                getUitgevers();
                getGebruikers();
                getBoekExemplaren();
                getBoeken();
            }
        }
        catch (Exception ex){
            logger.log( Level.WARNING, ex.toString(), "server could not be bound.");
        }
    }
    public Registry createRegistry() throws RemoteException {
        try {
            registry = LocateRegistry.createRegistry(port);
            System.out.println("server: created Registry on portnumber: " + port + ".");
        }
        catch (Exception ex) {
            logger.log( Level.WARNING, ex.toString(), ex);
        }
        return registry;
    }

    public Gebruiker login(String gebruikernaam, String wachtwoord) throws RemoteException {
        return gebruikerRepository.login(gebruikernaam, wachtwoord);
    }
    public int registreer(Gebruiker gebruiker) throws RemoteException {
        gebruiker.setId(gebruikerRepository.registreer(gebruiker));
        this.gebruikers.add(gebruiker);
        publisher.inform(GEBRUIKERADD, getGebruikers(), getGebruikers());
        return gebruiker.getId();
    }

    public boolean leenUit(int volgnummer, Gebruiker gebruiker) throws RemoteException {
        BoekExemplaar boekExemplaar = zoekBoekExemplaar(volgnummer);
        boekExemplaar.setBeschikbaar(false);
        zoekGebruiker(gebruiker.getGebruikersnaam()).addGeleendeBoek(boekExemplaar);
        publisher.inform(MIJNBOEKEN, getGeleendeBoeken(gebruiker), getGeleendeBoeken(gebruiker));
        return boekRepository.leenUit(boekExemplaar, gebruiker);
    }
    public boolean retourneer(int volgnummer, Gebruiker gebruiker) throws RemoteException {
        BoekExemplaar boekExemplaar = zoekBoekExemplaar(volgnummer);
        boekExemplaar.setBeschikbaar(true);
        zoekGebruiker(gebruiker.getGebruikersnaam()).deleteGeleendeBoek(volgnummer);
        publisher.inform(MIJNBOEKEN, getGeleendeBoeken(gebruiker), getGeleendeBoeken(gebruiker));
        return boekRepository.retourneer(boekExemplaar, gebruiker);
    }

    public boolean addAuteur(Auteur auteur) throws RemoteException {
        auteurs.add(auteur);
        return boekRepository.addAuteur(auteur);
    }
    public boolean addUitgever(Uitgever uitgever) throws RemoteException {
        uitgevers.add(uitgever);
        return boekRepository.addUitgever(uitgever);
    }
    public void addBoekExemplaar(Boek boek) throws Exception {
        int volgnr = generateVolgnummer();
        int id = boekRepository.addBoekExemplaar(boek, volgnr);
        BoekExemplaar boekExemplaar = new BoekExemplaar(id, boek, "", true, volgnr);
        boekExemplaren.add(boekExemplaar);
        boek.setBoekExemplaren(boekExemplaar);
    }
    public void addBoek(Boek boek, String aantalExemplaren) throws RemoteException {
        int x = Integer.parseInt(aantalExemplaren);
        boek = boekRepository.addBoek(boek);
        for (int i = 0; i < x; i++) {
            try {
                addBoekExemplaar(boek);
            }
            catch(Exception ex) {
                logger.log( Level.WARNING, ex.toString(), ex);
            }
        }
        this.boeken.add(boek);
        publisher.inform(BOEKADD, getBoeken(), getBoeken());
    }

    public Auteur zoekAuteur(String naam) throws RemoteException {
        for (Auteur a : auteurs) { if (a.getGegevens().getNaam().equals(naam)) { return a; } }
        return null;
    }
    public Uitgever zoekUitgever(String naam) throws RemoteException {
        for (Uitgever u : uitgevers) { if (u.getGegevens().getNaam().equals(naam)) { return u; } }
        return null;
    }
    public Boek zoekBoek(String titel) throws RemoteException {
        for (Boek b: boeken) { if (b.getTitel().equals(titel)) { return b; } }
        return null;
    }
    public BoekExemplaar zoekBoekExemplaar(int volgnummer) throws RemoteException {
        for (BoekExemplaar b : boekExemplaren) if (b.getVolgnummer() == volgnummer) return b;
        return null;
    }
    public Gebruiker zoekGebruiker(String gebruikernaam) throws RemoteException {
        for (Gebruiker g: gebruikers) { if (g.getGebruikersnaam().equalsIgnoreCase(gebruikernaam)) { return g; } }
        return null;
    }

    public List<String> getAuteurs() throws RemoteException {
        if (auteurs == null) { auteurs = boekRepository.getAuteurs(); }
        List<String> auteurslist = new ArrayList<>();
        for (Auteur auteur : auteurs) {
            auteurslist.add(auteur.getGegevens().getNaam());
        }
        return auteurslist;
    }
    public List<String> getUitgevers() throws RemoteException {
        if (uitgevers == null) { uitgevers = boekRepository.getUitgevers(); }
        List<String> uitgeverslist = new ArrayList<>();
        for (Uitgever uitgever : uitgevers) {
            uitgeverslist.add(uitgever.getGegevens().getNaam());
        }
        return uitgeverslist;
    }
    public List<String> getBoeken() throws RemoteException {
        if (boeken == null){ boeken = boekRepository.getBoeken(); }
        List<String> boeklist = new ArrayList<>();
        for (Boek boek : boeken) {
            boeklist.add(boek.getTitel());
        }
        return boeklist;
    }
    public List<String> getBoekExemplaren() throws RemoteException {
        if (boekExemplaren == null) { boekExemplaren = boekRepository.getBoekExemplaren(); }
        List<String> boekExemplarenlist = new ArrayList<>();
        for (BoekExemplaar boekExemplaar : boekExemplaren) {
            boekExemplarenlist.add(boekExemplaar.getBoek().getTitel());
        }
        return boekExemplarenlist;
    }
    public List<String> getGebruikers() throws RemoteException {
        if (gebruikers == null) { gebruikers = gebruikerRepository.getGebruikers(); }
        List<String> gebruikerslist = new ArrayList<>();
        for (Gebruiker gebruiker : gebruikers) {
            if (gebruiker.getStatus() == Status.KLANT) {
                gebruikerslist.add(gebruiker.getGebruikersnaam());
            }
        }
        return gebruikerslist;
    }
    public List<String> getGeleendeBoeken(Gebruiker gebruiker) throws RemoteException {
        try {
            return zoekGebruiker(gebruiker.getGebruikersnaam()).getGeleendeBoeken();
        }
        catch (Exception ex) {
            logger.log( Level.WARNING, ex.toString(), ex);
        }
        return new ArrayList<>();
    }

    public List<String> getBeschikbareExemplaren(String titel) throws RemoteException {
        List<String> beschikbareExemplaren = new ArrayList<>();
        for (BoekExemplaar boekExemplaar : boekExemplaren) {
            if (boekExemplaar.getBoek().getId() == zoekBoek(titel).getId() && boekExemplaar.getBeschikbaar()) {
                beschikbareExemplaren.add(String.valueOf(boekExemplaar.getVolgnummer()));
            }
        }
        return beschikbareExemplaren;
    }
    public int generateVolgnummer() {
        int x = 0;
        for (BoekExemplaar boek : boekExemplaren) {
            if (boek.getVolgnummer() > x) {
                x = boek.getVolgnummer();
            }
        }
        return x + 1;
    }

    public boolean wijzigGegevens(Gebruiker gebruiker) throws RemoteException {
        return gebruikerRepository.wijzigGegevens(gebruiker);
    }
    public boolean setBeschrijving(int volgnummer, String beschrijving) throws RemoteException {
        zoekBoekExemplaar(volgnummer).setBeschrijving(beschrijving);
        return boekRepository.setBeschrijving(zoekBoekExemplaar(volgnummer));
    }

    public void subscribePublisher(IRemotePropertyListener listener, String property) throws RemoteException {
        publisher.subscribeRemoteListener(listener, property);
    }
}

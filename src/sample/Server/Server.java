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
            getAuteurs();
            getUitgevers();
            getBoekExemplaren();
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

    public boolean leenUit(int volgnummer, Gebruiker gebruiker) throws Exception {
        BoekExemplaar boekExemplaar = zoekBoekExemplaar(volgnummer);
        boekExemplaar.setBeschikbaar(false);
        zoekGebruiker(gebruiker.getGebruikersnaam()).addGeleendeBoek(boekExemplaar);
        return boekRepository.leenUit(boekExemplaar, gebruiker);
    }
    public boolean retourneer(int volgnummer, Gebruiker gebruiker) throws Exception {
        BoekExemplaar boekExemplaar = zoekBoekExemplaar(volgnummer);
        boekExemplaar.setBeschikbaar(true);
        zoekGebruiker(gebruiker.getGebruikersnaam()).deleteGeleendeBoek(boekExemplaar);
        return boekRepository.retourneer(boekExemplaar, gebruiker);
    }

    public boolean addAuteur(Auteur auteur) throws Exception {
        return boekRepository.addAuteur(auteur);
    }
    public boolean addUitgever(Uitgever uitgever) throws Exception {
        return boekRepository.addUitgever(uitgever);
    }
    public void addBoekExemplaar(Boek boek) throws Exception {
        int volgnr = generateVolgnummer();
        int id = boekRepository.addBoekExemplaar(boek, volgnr);
        BoekExemplaar boekExemplaar = new BoekExemplaar(id, boek, "", true, volgnr);
        boekExemplaren.add(boekExemplaar);
        boek.setBoekExemplaren(boekExemplaar);
    }
    public void addBoek(Boek boek, String aantalExemplaren) throws Exception {
        int x = Integer.parseInt(aantalExemplaren);
        for (Auteur auteur : boek.getAuteurs()) {
            addAuteur(auteur);
        }
        addUitgever(boek.getUitgever());
        boek = boekRepository.addBoek(boek);
        for (int i = 0; i < x; i++) {
            addBoekExemplaar(boek);
        }
        this.boeken.add(boek);
    }

    public Auteur zoekAuteur(String naam) throws Exception {
        for (Auteur a : auteurs) { if (a.getGegevens().getNaam().equals(naam)) { return a; } }
        return null;
    }
    public Uitgever zoekUitgever(String naam) throws Exception {
        for (Uitgever u : uitgevers) { if (u.getGegevens().getNaam().equals(naam)) { return u; } }
        return null;
    }
    public Boek zoekBoek(String titel) throws Exception {
        for (Boek b: boeken) { if (b.getTitel().equals(titel)) { return b; } }
        return null;
    }
    public BoekExemplaar zoekBoekExemplaar(int volgnummer) throws Exception {
        for (BoekExemplaar b : boekExemplaren) {
            if (b.getVolgnummer() == volgnummer) {
                return b;
            }
        }
        return null;
    }
    public Gebruiker zoekGebruiker(String gebruikernaam) throws Exception {
        for (Gebruiker g: gebruikers) { if (g.getGebruikersnaam().equals(gebruikernaam)) { return g; } }
        return null;
    }

    public ArrayList<String> getAuteurs() throws Exception {
        if (auteurs == null) { auteurs = boekRepository.getAuteurs(); }
        ArrayList<String> auteurslist = new ArrayList<>();
        for (Auteur auteur : auteurs) {
            auteurslist.add(auteur.getGegevens().getNaam());
        }
        return auteurslist;
    }
    public ArrayList<String> getUitgevers() throws Exception {
        if (uitgevers == null) { uitgevers = boekRepository.getUitgevers(); }
        ArrayList<String> uitgeverslist = new ArrayList<>();
        for (Uitgever uitgever : uitgevers) {
            uitgeverslist.add(uitgever.getGegevens().getNaam());
        }
        return uitgeverslist;
    }
    public ArrayList<String> getBoeken() throws Exception {
        if (boeken == null){ boeken = boekRepository.getBoeken(); }
        ArrayList<String> boeklist = new ArrayList<>();
        for (Boek boek : boeken) {
            boeklist.add(boek.getTitel());
        }
        return boeklist;
    }
    public ArrayList<String> getBoekExemplaren() throws Exception {
        if (boekExemplaren == null) { boekExemplaren = boekRepository.getBoekExemplaren(); }
        ArrayList<String> boekExemplarenlist = new ArrayList<>();
        for (BoekExemplaar boekExemplaar : boekExemplaren) {
            boekExemplarenlist.add(boekExemplaar.getBoek().getTitel());
        }
        return boekExemplarenlist;
    }
    public ArrayList<String> getGebruikers() throws Exception {
        if (gebruikers == null) { gebruikers = gebruikerRepository.getGebruikers(); }
        ArrayList<String> gebruikerslist = new ArrayList<>();
        for (Gebruiker gebruiker : gebruikers) {
            if (gebruiker.getStatus().equals("Klant")) {
                gebruikerslist.add(gebruiker.getGebruikersnaam());
            }
        }
        return gebruikerslist;
    }
    public ArrayList<String> getGeleendeBoeken(Gebruiker gebruiker) throws Exception {
        ArrayList<String> geleendeBoeken = new ArrayList<>();
        for (BoekExemplaar boek : gebruiker.getGeleendeBoeken()) {
            geleendeBoeken.add(String.valueOf(boek.getVolgnummer()));
        }
        return geleendeBoeken;
    }
    public ArrayList<String> getBeschikbareExemplaren(String titel) throws Exception {
        ArrayList<String> beschikbareExemplaren = new ArrayList<>();
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

    public boolean wijzigGegevens(Gebruiker gebruiker) throws Exception {
        return gebruikerRepository.wijzigGegevens(gebruiker);
    }
    public boolean setBeschrijving(int volgnummer, String beschrijving) throws Exception {
        zoekBoekExemplaar(volgnummer).setBeschrijving(beschrijving);
        return boekRepository.setBeschrijving(zoekBoekExemplaar(volgnummer));
    }
}

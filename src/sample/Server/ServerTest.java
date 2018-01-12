package sample.Server;
import org.junit.Test;
import sample.Enums.Status;
import sample.Models.*;
import sample.Server.Server;
import java.rmi.RemoteException;
import java.util.ArrayList;
import static org.junit.Assert.*;

public class ServerTest {
//    Server server;
//    Gebruiker gebruiker;
//
//    public ServerTest() throws RemoteException {
//        gebruiker = new Gebruiker(1, "Gebruikersnaam", "Wachtwoord", Status.MEDEWERKER, new Gegevens("Naam"));
//        if (server == null) {
//            server = new Server();
//        }
//    }
//
//    @Test
//    public void createRegistry() throws Exception {
//        server.createRegistry();
//        assertNotNull(server.getRegistry());
//    }
//
//    @Test
//    public void login() throws Exception {
//        Gebruiker gebruiker = server.login("Medewerker", "Medewerker");
//        assertEquals("Medewerker", gebruiker.getGebruikersnaam());
//    }
//
//    @Test
//    public void registreer() throws Exception {
//        server.registreer(gebruiker);
//        assertTrue(server.getGebruikers().contains(gebruiker));
//    }
//
//    @Test
//    public void leenUit() throws Exception {
//        int volgnummer = 3;
//        Gebruiker gebruiker = server.zoekGebruiker("Klant");
//        server.leenUit(volgnummer, gebruiker);
//        assertTrue(server.zoekGebruiker(gebruiker.getGebruikersnaam()).getGeleendeBoeken().contains(String.valueOf(volgnummer)));
//    }
//
//    @Test
//    public void retourneer() throws Exception {
//        int volgnummer = 3;
//        Gebruiker gebruiker = server.zoekGebruiker("Klant");
//        server.retourneer(volgnummer, gebruiker);
//        assertFalse(server.zoekGebruiker(gebruiker.getGebruikersnaam()).getGeleendeBoeken().contains(String.valueOf(volgnummer)));
//    }
//
//    @Test
//    public void addAuteur() throws Exception {
//        Auteur auteur = new Auteur(new Gegevens("TestAuteur1"));
//        server.addAuteur(auteur);
//        assertTrue(server.getAuteurs().contains(auteur.getGegevens().getNaam()));
//    }
//
//    @Test
//    public void addUitgever() throws Exception {
//        Uitgever uitgever = new Uitgever(new Gegevens("TestUitgever1"));
//        server.addUitgever(uitgever);
//        assertTrue(server.getUitgevers().contains(uitgever.getGegevens().getNaam()));
//    }
//
//    @Test
//    public void addBoek() throws Exception {
//        ArrayList<Auteur> auteurs = new ArrayList<>();
//        Auteur auteur = new Auteur(new Gegevens("TestAuteur1")); auteurs.add(auteur);
//        Uitgever uitgever = new Uitgever(new Gegevens("TestUitgever1"));
//        Boek boek = new Boek("Titel1", "Descriptie1", auteurs, uitgever);
//        server.addBoek(boek, "1");
//
//        assertNotNull(server.zoekBoek(boek.getTitel()));
//    }
//
//    @Test
//    public void zoekAuteur() throws Exception {
//        assertNotNull(server.zoekAuteur("Paul van Loon"));
//    }
//
//    @Test
//    public void zoekUitgever() throws Exception {
//        assertNotNull(server.zoekUitgever("Leopold"));
//    }
//
//    @Test
//    public void zoekBoek() throws Exception {
//        assertNotNull(server.zoekBoek("De griezelbus"));
//    }
//
//    @Test
//    public void zoekBoekExemplaar() throws Exception {
//        assertNotNull(server.zoekBoekExemplaar(4));
//    }
//
//    @Test
//    public void zoekGebruiker() throws Exception {
//        assertNotNull(server.zoekGebruiker("Klant"));
//    }
//
//    @Test
//    public void getAuteurs() throws Exception {
//        ArrayList<String> list = server.getAuteurs();
//        assertTrue(list.contains("Paul van Loon"));
//    }
//
//    @Test
//    public void getUitgevers() throws Exception {
//        ArrayList<String> list = server.getUitgevers();
//        assertTrue(list.contains("Leopold"));
//    }
//
//    @Test
//    public void getBoeken() throws Exception {
//        ArrayList<String> list = server.getBoeken();
//        assertTrue(list.contains("De griezelbus"));
//    }
//
//    @Test
//    public void getBoekExemplaren() throws Exception {
//        ArrayList<String> list = server.getBoekExemplaren();
//        assertTrue(list.contains("2"));
//    }
//
//    @Test
//    public void getGebruikers() throws Exception {
//        ArrayList<String> list = server.getGebruikers();
//        assertTrue(list.contains("Klant"));
//    }
//
//    @Test
//    public void getGeleendeBoeken() throws Exception {
//        int volgnummer = 2;
//        Gebruiker gebruiker = server.zoekGebruiker("Klant");
//        server.leenUit(volgnummer, gebruiker);
//        assertTrue(server.getGeleendeBoeken(gebruiker).contains(volgnummer));
//    }
//
//    @Test
//    public void getBeschikbareExemplaren() throws Exception {
//        ArrayList<String> list = server.getBeschikbareExemplaren("De griezelbus");
//        assertTrue(list.size() > 0);
//    }
//
//    @Test
//    public void setBeschrijving() throws Exception {
//        int volgnummer = 5;
//        server.setBeschrijving(volgnummer, "Nieuwe beschrijving voor test2");
//        assertEquals("Nieuwe beschrijving voor test", server.zoekBoekExemplaar(volgnummer).getBeschrijving());
//    }
}
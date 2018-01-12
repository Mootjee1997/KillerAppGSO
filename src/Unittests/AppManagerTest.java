package Unittests;
import org.junit.Test;
import sample.Core.AppManager;
import sample.Models.*;
import java.util.ArrayList;
import static org.junit.Assert.*;

public class AppManagerTest {
    AppManager appManager = AppManager.getInstance();
    public AppManagerTest() throws Exception { }

    @Test
    public void getInstance() throws Exception {
        assertNotNull(AppManager.getInstance());
    }

    @Test
    public void login() throws Exception {
        String gebruikersnaam = "Medewerker";
        String wachtwoord = "medewerker";
        appManager.login(gebruikersnaam, wachtwoord);
        assertEquals(gebruikersnaam, appManager.getGebruiker().getGebruikersnaam());
    }

    @Test
    public void loguit() throws Exception {
        appManager.loguit();
        assertNull(appManager.getGebruiker());
    }

    @Test
    public void registreer() throws Exception {
        Gebruiker gebruiker = new Gebruiker("TestAccount", "Test12345", new Gegevens("Test Account", "Test@gmail.com", "Eindhoven", "0647550040"));
        appManager.registreer(gebruiker);
        assertNotNull(appManager.zoekGebruiker(gebruiker.getGebruikersnaam()));
    }

    @Test
    public void leenUit() throws Exception {
        int volgnummer = 1;
        Gebruiker gebruiker = appManager.zoekGebruiker("Klant");
        appManager.leenUit(volgnummer, gebruiker);
        assertTrue(appManager.zoekGebruiker(gebruiker.getGebruikersnaam()).getGeleendeBoeken().contains(String.valueOf(volgnummer)));
    }

    @Test
    public void retourneer() throws Exception {
        int volgnummer = 1;
        Gebruiker gebruiker = appManager.zoekGebruiker("Klant");
        appManager.retourneer(volgnummer, gebruiker);
        assertFalse(appManager.zoekGebruiker(gebruiker.getGebruikersnaam()).getGeleendeBoeken().contains(String.valueOf(volgnummer)));
    }

    @Test
    public void addAuteur() throws Exception {
        Auteur auteur = new Auteur(new Gegevens("TestAuteur"));
        appManager.addAuteur(auteur);
        assertTrue(appManager.getAuteurs().contains(auteur.getGegevens().getNaam()));
    }

    @Test
    public void addUitgever() throws Exception {
        Uitgever uitgever = new Uitgever(new Gegevens("TestUitgever"));
        appManager.addUitgever(uitgever);
        assertTrue(appManager.getUitgevers().contains(uitgever.getGegevens().getNaam()));
    }

    @Test
    public void addBoek() throws Exception {
        ArrayList<Auteur> auteurs = new ArrayList<>();
        Auteur auteur = new Auteur(new Gegevens("TestAuteur")); auteurs.add(auteur);
        Uitgever uitgever = new Uitgever(new Gegevens("TestUitgever"));
        Boek boek = new Boek("Titel", "Descriptie", auteurs, uitgever);
        appManager.addBoek(boek, "1");

        assertNotNull(appManager.zoekBoek(boek.getTitel()));
    }

    @Test
    public void zoekAuteur() throws Exception {
        assertNotNull(appManager.zoekAuteur("Paul van Loon"));
    }

    @Test
    public void zoekUitgever() throws Exception {
        assertNotNull(appManager.zoekUitgever("Leopold"));
    }

    @Test
    public void zoekBoek() throws Exception {
        assertNotNull(appManager.zoekBoek("De griezelbus"));
    }

    @Test
    public void zoekBoekExemplaar() throws Exception {
        assertNotNull(appManager.zoekBoekExemplaar(4));
    }

    @Test
    public void zoekGebruiker() throws Exception {
        assertNotNull(appManager.zoekGebruiker("Klant"));
    }

    @Test
    public void getAuteurs() throws Exception {
        ArrayList<String> list = appManager.getAuteurs();
        assertTrue(list.contains("Paul van Loon"));
    }

    @Test
    public void getUitgevers() throws Exception {
        ArrayList<String> list = appManager.getUitgevers();
        assertTrue(list.contains("Leopold"));
    }

    @Test
    public void getBoeken() throws Exception {
        ArrayList<String> list = appManager.getBoeken();
        assertTrue(list.contains("De griezelbus"));
    }

    @Test
    public void getGebruikers() throws Exception {
        ArrayList<String> list = appManager.getGebruikers();
        assertTrue(list.contains("Klant"));
    }

    @Test
    public void getGeleendeBoeken() throws Exception {
        int volgnummer = 2;
        Gebruiker gebruiker = appManager.zoekGebruiker("Klant");
        appManager.leenUit(volgnummer, gebruiker);
        assertTrue(appManager.getGeleendeBoeken(gebruiker).contains(String.valueOf(volgnummer)));
    }

    @Test
    public void getBeschikbareExemplaren() throws Exception {
        ArrayList<String> list = appManager.getBeschikbareExemplaren("De griezelbus");
        assertTrue(list.contains("1"));
    }

    @Test

    public void setBeschrijving() throws Exception {
        int volgnummer = 5;
        appManager.setBeschrijving(volgnummer, "Nieuwe beschrijving voor test");
        assertEquals("Nieuwe beschrijving voor test", appManager.zoekBoekExemplaar(volgnummer).getBeschrijving());
    }

    @Test
    public void getGebruiker() throws Exception {
        appManager.login("Medewerker", "Medewerker");
        assertTrue(appManager.getGebruiker().getGebruikersnaam().equals("Medewerker"));
    }
}
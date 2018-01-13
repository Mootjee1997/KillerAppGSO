package Unittests;
import org.junit.Test;
import sample.Enums.Status;
import sample.Models.*;
import java.util.ArrayList;
import static org.junit.Assert.*;

public class GebruikerTest {
    final String gebruikersnaam = "Gebruikersnaam";
    Gebruiker gebruiker;

    public GebruikerTest() {
        gebruiker = new Gebruiker(1, gebruikersnaam, "medewerker", Status.MEDEWERKER, new Gegevens("Naam"));
    }

    @Test
    public void getGeleendeBoeken() throws Exception {
        ArrayList<Auteur> auteurs = new ArrayList<>();
        Auteur auteur = new Auteur(new Gegevens("Auteur")); auteurs.add(auteur);
        Uitgever uitgever = new Uitgever(new Gegevens("Uitgever"));
        Boek boek = new Boek("Titel", "Descriptie", auteurs, uitgever);
        BoekExemplaar boekExemplaar = new BoekExemplaar(1, boek, "Beschrijving", true, 1);
        gebruiker.addGeleendeBoek(boekExemplaar);

        assertTrue(gebruiker.getGeleendeBoeken().contains(String.valueOf(boekExemplaar.getVolgnummer())));
    }

    @Test
    public void addGeleendeBoek() throws Exception {
        ArrayList<Auteur> auteurs = new ArrayList<>();
        Auteur auteur = new Auteur(new Gegevens("Auteur1")); auteurs.add(auteur);
        Uitgever uitgever = new Uitgever(new Gegevens("Uitgever1"));
        Boek boek = new Boek("Titel1", "Descriptie1", auteurs, uitgever);
        BoekExemplaar boekExemplaar = new BoekExemplaar(1, boek, "Beschrijving1", true, 1);
        gebruiker.addGeleendeBoek(boekExemplaar);

        assertTrue(gebruiker.getGeleendeBoeken().contains(String.valueOf(boekExemplaar.getVolgnummer())));
    }

    @Test
    public void wijzigGegevens() throws Exception {
        Gegevens gegevens = new Gegevens("Naam", "Naam@gmail.com", "Eindhoven", "0647550040");
        try {
            gebruiker.wijzigGegevens(gegevens);
        }
        catch (Exception ex) { }
        assertEquals(gegevens, gebruiker.getGegevens());
    }

    @Test
    public void getId() throws Exception {
        assertEquals(1, gebruiker.getId());
    }

    @Test
    public void getStatus() throws Exception {
        assertEquals(Status.MEDEWERKER, gebruiker.getStatus());
    }

    @Test
    public void getGebruikersnaam() throws Exception {
        assertEquals(gebruikersnaam, gebruiker.getGebruikersnaam());
    }

    @Test
    public void getWachtwoord() throws Exception {
        assertEquals("medewerker", gebruiker.getWachtwoord());
    }

    @Test
    public void getGegevens() throws Exception {
        Gegevens gegevens = new Gegevens("Naam");
        assertEquals(gegevens.getNaam(), gebruiker.getGegevens().getNaam());
    }

    @Test
    public void setId() throws Exception {
        gebruiker.setId(2);
        assertEquals(2, gebruiker.getId());
    }

    @Test
    public void deleteGeleendeBoek() throws Exception {
        ArrayList<Auteur> auteurs = new ArrayList<>();
        Auteur auteur = new Auteur(new Gegevens("Auteur2")); auteurs.add(auteur);
        Uitgever uitgever = new Uitgever(new Gegevens("Uitgever2"));
        Boek boek = new Boek("Titel2", "Descriptie2", auteurs, uitgever);
        BoekExemplaar boekExemplaar = new BoekExemplaar(1, boek, "Beschrijving2", true, 1);
        gebruiker.addGeleendeBoek(boekExemplaar);
        gebruiker.deleteGeleendeBoek(boekExemplaar.getVolgnummer());

        assertFalse(gebruiker.getGeleendeBoeken().contains(boekExemplaar));
    }

    @Test
    public void getToString() throws Exception {
        assertEquals(gebruikersnaam, gebruiker.toString());
    }

}
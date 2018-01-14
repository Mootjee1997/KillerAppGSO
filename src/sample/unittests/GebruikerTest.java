package sample.unittests;
import org.junit.Test;
import sample.enums.Status;
import sample.models.*;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.*;

public class GebruikerTest {
    private static final String GEBRUIKERSNAAM = "Gebruikersnaam";
    private Logger logger = Logger.getLogger("Logger");
    private Gebruiker gebruiker;
    private BoekExemplaar boekExemplaar;

    public GebruikerTest() {
        gebruiker = new Gebruiker(1, GEBRUIKERSNAAM, "medewerker", Status.MEDEWERKER, new Gegevens("Naam"));
    }

    @Test
    public void addGeleendeBoek() {
        try {
            ArrayList<Auteur> auteurs = new ArrayList<>();
            Auteur auteur = new Auteur(new Gegevens("Auteur1"));
            auteurs.add(auteur);
            Uitgever uitgever = new Uitgever(new Gegevens("Uitgever1"));
            Boek boek = new Boek("Titel1", "Descriptie1", auteurs, uitgever);
            boekExemplaar = new BoekExemplaar(1, boek, "Beschrijving1", true, 1);
            gebruiker.addGeleendeBoek(boekExemplaar);

            assertTrue(gebruiker.getGeleendeBoeken().contains(String.valueOf(boekExemplaar.getVolgnummer())));
        }
        catch (Exception ex) {
            logger.log( Level.WARNING, ex.toString(), ex);
        }
    }

    @Test
    public void getGeleendeBoeken() {
        try {
            assertTrue(gebruiker.getGeleendeBoeken().contains(String.valueOf(boekExemplaar.getVolgnummer())));
        } catch (Exception ex) {
            logger.log( Level.WARNING, ex.toString(), ex);
        }
    }

    @Test
    public void wijzigGegevens() {
        Gegevens gegevens = new Gegevens("Naam", "Naam@gmail.com", "Eindhoven", "0647550040");
        try {
            gebruiker.wijzigGegevens(gegevens);
        }
        catch (Exception ex) {
            logger.log( Level.WARNING, ex.toString(), ex);
        }
        assertEquals(gegevens, gebruiker.getGegevens());
    }

    @Test
    public void getId() {
        assertEquals(1, gebruiker.getId());
    }

    @Test
    public void getStatus() {
        assertEquals(Status.MEDEWERKER, gebruiker.getStatus());
    }

    @Test
    public void getGebruikersnaam() {
        assertEquals(GEBRUIKERSNAAM, gebruiker.getGebruikersnaam());
    }

    @Test
    public void getWachtwoord() {
        assertEquals("medewerker", gebruiker.getWachtwoord());
    }

    @Test
    public void getGegevens() {
        Gegevens gegevens = new Gegevens("Naam");
        assertEquals(gegevens.getNaam(), gebruiker.getGegevens().getNaam());
    }

    @Test
    public void setId() {
        gebruiker.setId(2);
        assertEquals(2, gebruiker.getId());
    }

    @Test
    public void deleteGeleendeBoek() {
        try {
            ArrayList<Auteur> auteurs = new ArrayList<>();
            Auteur auteur = new Auteur(new Gegevens("Auteur2"));
            auteurs.add(auteur);
            Uitgever uitgever = new Uitgever(new Gegevens("Uitgever2"));
            Boek boek = new Boek("Titel2", "Descriptie2", auteurs, uitgever);
            BoekExemplaar boekExemplaar = new BoekExemplaar(1, boek, "Beschrijving2", true, 1);
            gebruiker.addGeleendeBoek(boekExemplaar);
            gebruiker.deleteGeleendeBoek(boekExemplaar.getVolgnummer());

            assertFalse(gebruiker.getGeleendeBoeken().contains(boekExemplaar));
        }
        catch (Exception ex) {
            logger.log( Level.WARNING, ex.toString(), ex);
        }
    }

    @Test
    public void getToString() {
        assertEquals(GEBRUIKERSNAAM, gebruiker.toString());
    }

}
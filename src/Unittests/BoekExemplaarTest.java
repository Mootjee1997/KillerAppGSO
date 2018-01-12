package Unittests;
import org.junit.Test;
import sample.Models.*;
import java.util.ArrayList;
import static org.junit.Assert.*;

public class BoekExemplaarTest {
    BoekExemplaar boekExemplaar;
    ArrayList<Auteur> auteurs;
    Auteur auteur;
    Uitgever uitgever;
    Boek boek;

    public BoekExemplaarTest() {
        auteurs = new ArrayList<>();
        auteur = new Auteur(new Gegevens("Auteur")); auteurs.add(auteur);
        uitgever = new Uitgever(new Gegevens("Uitgever"));
        boek = new Boek("Titel", "Descriptie", auteurs, uitgever);
        boekExemplaar = new BoekExemplaar(1, boek, "Beschrijving", true, 1);
    }

    @Test
    public void getId() throws Exception {
        assertEquals(1, boekExemplaar.getId());
    }

    @Test
    public void getVolgnummer() throws Exception {
        assertEquals(1, boekExemplaar.getVolgnummer());
    }

    @Test
    public void getBoek() throws Exception {
        assertEquals(boek, boekExemplaar.getBoek());
    }

    @Test
    public void getBeschrijving() throws Exception {
        assertEquals("Beschrijving", boekExemplaar.getBeschrijving());
    }

    @Test
    public void getBeschikbaar() throws Exception {
        assertTrue(boekExemplaar.getBeschikbaar());
    }

    @Test
    public void setBoek() throws Exception {
        ArrayList<Auteur> auteurs = new ArrayList<>();
        Auteur auteur = new Auteur(new Gegevens("Auteur1")); auteurs.add(auteur);
        Uitgever uitgever = new Uitgever(new Gegevens("Uitgever1"));
        Boek boek = new Boek("Titel1", "Descriptie1", auteurs, uitgever);
        boekExemplaar.setBoek(boek);

        assertEquals(boek, boekExemplaar.getBoek());
    }

    @Test
    public void setBeschrijving() throws Exception {
        boekExemplaar.setBeschrijving("Beschrijving2");
        assertEquals("Beschrijving2", boekExemplaar.getBeschrijving());
    }

    @Test
    public void setBeschikbaar() throws Exception {
        boekExemplaar.setBeschikbaar(false);
        assertFalse(boekExemplaar.getBeschikbaar());
    }

    @Test
    public void getToString() throws Exception {
        assertEquals(1, boekExemplaar.getVolgnummer());
    }

}
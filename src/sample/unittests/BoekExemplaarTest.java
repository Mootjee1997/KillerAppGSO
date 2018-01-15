package sample.unittests;
import org.junit.Test;
import sample.models.*;
import java.util.ArrayList;
import static org.junit.Assert.*;

public class BoekExemplaarTest {
    private BoekExemplaar boekExemplaar;
    private ArrayList<Auteur> auteurs;
    private Auteur auteur;
    private Uitgever uitgever;
    private Boek boek;

    public BoekExemplaarTest() {
        auteurs = new ArrayList<>();
        auteur = new Auteur(new Gegevens("Auteur")); auteurs.add(auteur);
        uitgever = new Uitgever(new Gegevens("Uitgever"));
        boek = new Boek("Titel", "Descriptie", auteurs, uitgever);
        boekExemplaar = new BoekExemplaar(1, boek, "Beschrijving", true, 1);
    }

    @Test
    public void getId() {
        assertEquals(1, boekExemplaar.getId());
    }

    @Test
    public void getVolgnummer() {
        assertEquals(1, boekExemplaar.getVolgnummer());
    }

    @Test
    public void getBoek() {
        assertEquals(boek, boekExemplaar.getBoek());
    }

    @Test
    public void getBeschrijving() {
        assertEquals("Beschrijving", boekExemplaar.getBeschrijving());
    }

    @Test
    public void getBeschikbaar() {
        assertTrue(boekExemplaar.getBeschikbaar());
    }

    @Test
    public void setBoek() {
        ArrayList<Auteur> auteurs1 = new ArrayList<>();
        Auteur auteur1 = new Auteur(new Gegevens("Auteur1")); auteurs1.add(auteur1);
        Uitgever uitgever1 = new Uitgever(new Gegevens("Uitgever1"));
        Boek boek1 = new Boek("Titel", "Descriptie1", auteurs1, uitgever1);
        boekExemplaar.setBoek(boek1);

        assertEquals(boek1, boekExemplaar.getBoek());
    }

    @Test
    public void setBeschrijving() {
        boekExemplaar.setBeschrijving("Beschrijving2");
        assertEquals("Beschrijving2", boekExemplaar.getBeschrijving());
    }

    @Test
    public void setBeschikbaar() {
        boekExemplaar.setBeschikbaar(false);
        assertFalse(boekExemplaar.getBeschikbaar());
    }

    @Test
    public void getToString() {
        assertEquals(1, boekExemplaar.getVolgnummer());
    }

}
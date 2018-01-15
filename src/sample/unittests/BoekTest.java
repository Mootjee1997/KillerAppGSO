package sample.unittests;
import org.junit.Test;
import sample.models.*;
import java.util.ArrayList;
import static org.junit.Assert.*;

public class BoekTest {
    private static final String TITEL = "Titel";
    private ArrayList<Auteur> auteurs;
    private Auteur auteur;
    private Uitgever uitgever;
    private Boek boek;

    public BoekTest() {
        auteurs = new ArrayList<>();
        auteur = new Auteur(new Gegevens("Auteur")); auteurs.add(auteur);
        uitgever = new Uitgever(new Gegevens("Uitgever"));
        boek = new Boek(TITEL, "Descriptie", auteurs, uitgever);
    }

    @Test
    public void getId() {
        boek.setId(1);
        assertEquals(1, boek.getId());
    }

    @Test
    public void getTitel() {
        assertEquals(TITEL, boek.getTitel());
    }

    @Test
    public void getDescriptie() {
        assertEquals("Descriptie", boek.getDescriptie());
    }

    @Test
    public void getAuteurs() {
        assertEquals(auteurs, boek.getAuteurs());
    }

    @Test
    public void getUitgever() {
        assertEquals(uitgever, boek.getUitgever());
    }

    @Test
    public void setId() {
        boek.setId(1);
        assertEquals(1, boek.getId());
    }

    @Test
    public void setAuteurs() {
        Auteur auteur1 = new Auteur(new Gegevens("Auteur1"));
        boek.setAuteurs(auteur1);
        assertTrue(boek.getAuteurs().contains(auteur1));
    }

    @Test
    public void setUitgever() {
        Uitgever uitgever1 = new Uitgever(new Gegevens("Uitgever1"));
        boek.setUitgever(uitgever1);
        assertTrue(boek.getUitgever().equals(uitgever1));
    }

    @Test
    public void getToString() {
        assertEquals(TITEL, boek.getTitel());
    }

}
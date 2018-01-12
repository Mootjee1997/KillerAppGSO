package Unittests;
import org.junit.Test;
import sample.Models.*;
import java.util.ArrayList;
import static org.junit.Assert.*;

public class BoekTest {
    ArrayList<Auteur> auteurs;
    Auteur auteur;
    Uitgever uitgever;
    Boek boek;

    public BoekTest() {
        auteurs = new ArrayList<>();
        auteur = new Auteur(new Gegevens("Auteur")); auteurs.add(auteur);
        uitgever = new Uitgever(new Gegevens("Uitgever"));
        boek = new Boek("Titel", "Descriptie", auteurs, uitgever);
    }

    @Test
    public void getId() throws Exception {
        boek.setId(1);
        assertEquals(1, boek.getId());
    }

    @Test
    public void getTitel() throws Exception {
        assertEquals("Titel", boek.getTitel());
    }

    @Test
    public void getDescriptie() throws Exception {
        assertEquals("Descriptie", boek.getDescriptie());
    }

    @Test
    public void getAuteurs() throws Exception {
        assertEquals(auteurs, boek.getAuteurs());
    }

    @Test
    public void getUitgever() throws Exception {
        assertEquals(uitgever, boek.getUitgever());
    }

    @Test
    public void setId() throws Exception {
        boek.setId(1);
        assertEquals(1, boek.getId());
    }

    @Test
    public void setAuteurs() throws Exception {
        Auteur auteur = new Auteur(new Gegevens("Auteur1"));
        boek.setAuteurs(auteur);
        assertTrue(boek.getAuteurs().contains(auteur));
    }

    @Test
    public void setUitgever() throws Exception {
        Uitgever uitgever = new Uitgever(new Gegevens("Uitgever1"));
        boek.setUitgever(uitgever);
        assertTrue(boek.getUitgever().equals(uitgever));
    }

    @Test
    public void getToString() throws Exception {
        assertEquals("Titel", boek.getTitel());
    }

}
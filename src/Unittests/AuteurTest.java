package Unittests;
import org.junit.Test;
import sample.Models.Auteur;
import sample.Models.Gegevens;
import static org.junit.Assert.*;

public class AuteurTest {
    Gegevens gegevens;
    Auteur auteur;

    public AuteurTest() {
        gegevens = new Gegevens("Naam");
        auteur = new Auteur(gegevens);
    }

    @Test
    public void getGegevens() throws Exception {
        assertTrue(gegevens == auteur.getGegevens());
    }

    @Test
    public void getToString() {
        assertEquals("Naam", auteur.toString());
    }
}
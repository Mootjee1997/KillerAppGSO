package sample.unittests;
import org.junit.Test;
import sample.models.Auteur;
import sample.models.Gegevens;
import static org.junit.Assert.*;

public class AuteurTest {
    Gegevens gegevens;
    Auteur auteur;

    public AuteurTest() {
        gegevens = new Gegevens("Naam");
        auteur = new Auteur(gegevens);
    }

    @Test
    public void getGegevens() {
        assertTrue(gegevens == auteur.getGegevens());
    }

    @Test
    public void getToString() {
        assertEquals("Naam", auteur.toString());
    }
}
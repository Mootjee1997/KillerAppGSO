package sample.unittests;
import org.junit.Test;
import sample.models.Gegevens;
import static org.junit.Assert.*;

public class GegevensTest {
    Gegevens gegevens;

    public GegevensTest() {
        gegevens = new Gegevens("Naam", "Naam@gmail.com", "Eindhoven", "0647550040");
    }

    @Test
    public void getNaam() {
        assertEquals("Naam", gegevens.getNaam());
    }

    @Test
    public void getEmail() {
        assertEquals("Naam@gmail.com", gegevens.getEmail());
    }

    @Test
    public void getWoonplaats() {
        assertEquals("Eindhoven", gegevens.getWoonplaats());
    }

    @Test
    public void getTelefoonNr() {
        assertEquals("0647550040", gegevens.getTelefoonNr());
    }

}
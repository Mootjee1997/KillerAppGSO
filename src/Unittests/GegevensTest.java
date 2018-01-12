package Unittests;
import org.junit.Test;
import sample.Models.Gegevens;
import static org.junit.Assert.*;

public class GegevensTest {
    Gegevens gegevens;

    public GegevensTest() {
        gegevens = new Gegevens("Naam", "Naam@gmail.com", "Eindhoven", "0647550040");
    }

    @Test
    public void getNaam() throws Exception {
        assertEquals("Naam", gegevens.getNaam());
    }

    @Test
    public void getEmail() throws Exception {
        assertEquals("Naam@gmail.com", gegevens.getEmail());
    }

    @Test
    public void getWoonplaats() throws Exception {
        assertEquals("Eindhoven", gegevens.getWoonplaats());
    }

    @Test
    public void getTelefoonNr() throws Exception {
        assertEquals("0647550040", gegevens.getTelefoonNr());
    }

}
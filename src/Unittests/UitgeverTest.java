package Unittests;
import org.junit.Test;
import sample.Models.Gegevens;
import sample.Models.Uitgever;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UitgeverTest {
    Gegevens gegevens;
    Uitgever uitgever;

    public UitgeverTest() {
        gegevens = new Gegevens("Naam");
        uitgever = new Uitgever(gegevens);
    }

    @Test
    public void getGegevens() throws Exception {
        assertTrue(gegevens == uitgever.getGegevens());
    }

    @Test
    public void getToString() {
        assertEquals("Naam", uitgever.toString());
    }
}
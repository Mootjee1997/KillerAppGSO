package sample.unittests;
import org.junit.Test;
import sample.models.Gegevens;
import sample.models.Uitgever;
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
    public void getGegevens() {
        assertTrue(gegevens == uitgever.getGegevens());
    }

    @Test
    public void getToString() {
        assertEquals("Naam", uitgever.toString());
    }
}
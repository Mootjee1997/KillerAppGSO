package sample.unittests;
import org.junit.Test;
import sample.core.AppManager;
import sample.models.*;
import java.rmi.RemoteException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.Assert.*;

public class AppManagerTest {
    private static final String KLANT = "Klant";
    private static final String MEDEWERKER = "Medewerker";
    private static final String DEGRIEZELBUS = "De griezelbus";
    private Logger logger = Logger.getLogger("Logger");
    private AppManager appManager = AppManager.getInstance();

    public AppManagerTest() {
        //Lege constructor
    }

    @Test
    public void getInstance() throws RemoteException {
        assertNotNull(AppManager.getInstance());
    }

    @Test
    public void login() throws RemoteException {
        appManager.login(MEDEWERKER, MEDEWERKER);
        assertEquals(MEDEWERKER, appManager.getGebruiker().getGebruikersnaam());
    }

    @Test
    public void loguit() throws RemoteException {
        appManager.loguit();
        assertNull(appManager.getGebruiker());
    }

    @Test
    public void registreer() throws RemoteException {
        Gebruiker gebruiker = new Gebruiker("TestAccount", "Test12345", new Gegevens("Test Account", "Test@gmail.com", "Eindhoven", "0647550040"));
        appManager.registreer(gebruiker);
        assertNotNull(appManager.zoekGebruiker(gebruiker.getGebruikersnaam()));
    }

    @Test
    public void leenUit() throws RemoteException {
        try {
            int volgnummer = 1;
            Gebruiker gebruiker = appManager.zoekGebruiker(KLANT);
            appManager.leenUit(volgnummer, gebruiker);
            assertTrue(appManager.zoekGebruiker(gebruiker.getGebruikersnaam()).getGeleendeBoeken().contains(String.valueOf(volgnummer)));
        }
        catch (Exception ex) {
            logger.log( Level.WARNING, ex.toString(), ex);
        }
    }

    @Test
    public void retourneer() throws RemoteException {
        try {
            int volgnummer = 1;
            Gebruiker gebruiker = appManager.zoekGebruiker(KLANT);
            appManager.retourneer(volgnummer, gebruiker);
            assertFalse(appManager.zoekGebruiker(gebruiker.getGebruikersnaam()).getGeleendeBoeken().contains(String.valueOf(volgnummer)));
        }
        catch (Exception ex) {
            logger.log( Level.WARNING, ex.toString(), ex);
        }
    }

    @Test
    public void addAuteur() throws RemoteException {
        Auteur auteur = new Auteur(new Gegevens("TestAuteur"));
        appManager.addAuteur(auteur);
        assertTrue(appManager.getAuteurs().contains(auteur.getGegevens().getNaam()));
    }

    @Test
    public void addUitgever() throws RemoteException {
        Uitgever uitgever = new Uitgever(new Gegevens("TestUitgever"));
        appManager.addUitgever(uitgever);
        assertTrue(appManager.getUitgevers().contains(uitgever.getGegevens().getNaam()));
    }

    @Test
    public void zoekAuteur() throws RemoteException {
        assertNotNull(appManager.zoekAuteur("Paul van Loon"));
    }

    @Test
    public void zoekUitgever() throws RemoteException {
        assertNotNull(appManager.zoekUitgever("Leopold"));
    }

    @Test
    public void zoekBoek() throws RemoteException {
        assertNotNull(appManager.zoekBoek(DEGRIEZELBUS));
    }

    @Test
    public void zoekBoekExemplaar() throws RemoteException {
        assertNotNull(appManager.zoekBoekExemplaar(4));
    }

    @Test
    public void zoekGebruiker() throws RemoteException {
        assertNotNull(appManager.zoekGebruiker(KLANT));
    }

    @Test
    public void getAuteurs() throws RemoteException {
        List<String> list = appManager.getAuteurs();
        assertTrue(list.contains("Paul van Loon"));
    }

    @Test
    public void getUitgevers() throws RemoteException {
        List<String> list = appManager.getUitgevers();
        assertTrue(list.contains("Leopold"));
    }

    @Test
    public void getBoeken() throws RemoteException {
        List<String> list = appManager.getBoeken();
        assertTrue(list.contains(DEGRIEZELBUS));
    }

    @Test
    public void getGebruikers() throws RemoteException {
        List<String> list = appManager.getGebruikers();
        assertTrue(list.contains(KLANT));
    }

    @Test
    public void getGeleendeBoeken() throws RemoteException {
        int volgnummer = 2;
        Gebruiker gebruiker = appManager.zoekGebruiker(KLANT);
        appManager.leenUit(volgnummer, gebruiker);
        assertTrue(appManager.getGeleendeBoeken(gebruiker).contains(String.valueOf(volgnummer)));
    }

    @Test
    public void getBeschikbareExemplaren() throws RemoteException {
        List<String> list = appManager.getBeschikbareExemplaren(DEGRIEZELBUS);
        assertTrue(list.contains("1"));
    }

    @Test

    public void setBeschrijving() throws RemoteException {
        int volgnummer = 5;
        appManager.setBeschrijving(volgnummer, "Nieuwe beschrijving voor test");
        assertEquals("Nieuwe beschrijving voor test", appManager.zoekBoekExemplaar(volgnummer).getBeschrijving());
    }

    @Test
    public void getGebruiker() throws RemoteException {
        appManager.login(MEDEWERKER, MEDEWERKER);
        assertTrue(appManager.getGebruiker().getGebruikersnaam().equals(MEDEWERKER));
    }
}
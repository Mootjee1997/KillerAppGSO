package sample.Contexts;
import sample.Models.Boek;
import sample.Models.Gebruiker;

public class IBoekContext {
    public Boek addBoek(Boek boek) throws Exception {
        return null;
    }

    public boolean leenUit(Boek boek, Gebruiker gebruiker) throws Exception {
        return false;
    }

    public boolean retourneer(Boek boek, Gebruiker gebruiker) throws Exception {
        return false;
    }
}

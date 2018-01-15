package sample.repositories;
import sample.contexts.IGebruikerContext;
import sample.models.Gebruiker;
import java.util.List;

public class GebruikerRepository {
    private final IGebruikerContext context;

    public GebruikerRepository(IGebruikerContext context){
     this.context = context;
    }

    public Gebruiker login(String gebruikernaam, String wachtwoord) {
        return context.login(gebruikernaam, wachtwoord);
    }

    public int registreer(Gebruiker gebruiker) {
        return context.registreer(gebruiker);
    }

    public boolean wijzigGegevens(Gebruiker gebruiker) {
        return context.wijzigGegevens(gebruiker);
    }

    public List<Gebruiker> getGebruikers() {
        return context.getGebruikers();
    }
}

package sample.Repositories;
import sample.Contexts.IGebruikerContext;
import sample.Models.Gebruiker;

public class GebruikerRepository {
    private final IGebruikerContext context;

    public GebruikerRepository(IGebruikerContext context){
     this.context = context;
    }

    public Gebruiker login(String gebruikernaam, String wachtwoord) throws Exception {
        return context.login(gebruikernaam, wachtwoord);
    }

    public Gebruiker registreer(Gebruiker gebruiker) throws Exception {
        return context.registreer(gebruiker);
    }
}

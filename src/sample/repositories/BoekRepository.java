package sample.repositories;
import sample.contexts.IBoekContext;
import sample.models.*;
import java.util.List;

public class BoekRepository {
    private final IBoekContext context;

    public BoekRepository(IBoekContext context){
        this.context = context;
    }

    public boolean leenUit(BoekExemplaar boek, Gebruiker gebruiker){
        return context.beheerBoek(boek, gebruiker, "Leenuit");
    }

    public boolean retourneer(BoekExemplaar boek, Gebruiker gebruiker) {
        return context.beheerBoek(boek, gebruiker, "Retourneer");
    }

    public boolean setBeschrijving(BoekExemplaar boekExemplaar) {
        return context.setBeschrijving(boekExemplaar);
    }

    public boolean addAuteur(Auteur auteur) {
        return context.addAuteur(auteur);
    }

    public boolean addUitgever(Uitgever uitgever) {
        return context.addUitgever(uitgever);
    }

    public int addBoekExemplaar(Boek boek, int volgnr) {
        return context.addBoekExemplaar(boek, volgnr);
    }

    public Boek addBoek(Boek boek) {
        return context.addBoek(boek);
    }

    public List<Auteur> getAuteurs() {
        return context.getAuteurs();
    }

    public List<Uitgever> getUitgevers() {
        return context.getUitgevers();
    }

    public List<BoekExemplaar> getBoekExemplaren() {
        return context.getBoekExemplaren();
    }

    public List<Boek> getBoeken() {
        return context.getBoeken();
    }

}

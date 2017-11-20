package sample.Repositories;
import sample.Contexts.IBoekContext;
import sample.Models.Boek;
import sample.Models.Gebruiker;

public class BoekRepository {
    private final IBoekContext context;

    public BoekRepository(IBoekContext context){
        this.context = context;
    }

    public Boek addBoek(Boek boek) throws Exception {
        return context.addBoek(boek);
    }

    public boolean leenUit(Boek boek, Gebruiker gebruiker) throws Exception {
        return context.leenUit(boek, gebruiker);
    }

    public boolean retourneer(Boek boek, Gebruiker gebruiker) throws Exception {
        return context.retourneer(boek, gebruiker);
    }
}

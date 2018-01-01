package sample.Repositories;
import sample.Contexts.IBoekContext;
import sample.Models.Boek;
import sample.Models.BoekExemplaar;
import sample.Models.Gebruiker;
import java.util.ArrayList;

public class BoekRepository {
    private final IBoekContext context;

    public BoekRepository(IBoekContext context){
        this.context = context;
    }

    public Boek addBoek(Boek boek) throws Exception {
        return context.addBoek(boek);
    }

    public boolean leenUit(BoekExemplaar boek, Gebruiker gebruiker) throws Exception {
        return context.leenUit(boek, gebruiker);
    }

    public boolean retourneer(BoekExemplaar boek, Gebruiker gebruiker) throws Exception {
        return context.retourneer(boek, gebruiker);
    }

    public ArrayList<Boek> getBoeken() throws Exception {
        return context.getBoeken();
    }
}

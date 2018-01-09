package sample.Repositories;
import sample.Contexts.IBoekContext;
import sample.Models.*;
import java.util.ArrayList;

public class BoekRepository {
    private final IBoekContext context;

    public BoekRepository(IBoekContext context){
        this.context = context;
    }

    public boolean leenUit(BoekExemplaar boek, Gebruiker gebruiker) throws Exception {
        return context.leenUit(boek, gebruiker);
    }

    public boolean retourneer(BoekExemplaar boek, Gebruiker gebruiker) throws Exception {
        return context.retourneer(boek, gebruiker);
    }

    public boolean setBeschrijving(BoekExemplaar boekExemplaar) throws Exception {
        return context.setBeschrijving(boekExemplaar);
    }

    public boolean addAuteur(Auteur auteur) throws Exception {
        return context.addAuteur(auteur);
    }

    public boolean addUitgever(Uitgever uitgever) throws Exception {
        return context.addUitgever(uitgever);
    }

    public int addBoekExemplaar(Boek boek, int volgnr) throws Exception {
        return context.addBoekExemplaar(boek, volgnr);
    }

    public Boek addBoek(Boek boek) throws Exception {
        return context.addBoek(boek);
    }

    public ArrayList<Auteur> getAuteurs() throws Exception {
        return context.getAuteurs();
    }

    public ArrayList<Uitgever> getUitgevers() throws Exception {
        return context.getUitgevers();
    }

    public ArrayList<BoekExemplaar> getBoekExemplaren() throws Exception {
        return context.getBoekExemplaren();
    }

    public ArrayList<Boek> getBoeken() throws Exception {
        return context.getBoeken();
    }

}

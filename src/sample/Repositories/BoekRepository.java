package sample.Repositories;
import sample.Contexts.IBoekContext;
import sample.Models.*;
import java.sql.SQLException;
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

    public ArrayList<Auteur> getAuteurs() throws SQLException, ClassNotFoundException {
        return context.getAuteurs();
    }

    public ArrayList<Uitgever> getUitgevers() throws SQLException, ClassNotFoundException {
        return context.getUitgevers();
    }

    public boolean setBeschrijving(BoekExemplaar boekExemplaar) throws SQLException, ClassNotFoundException {
        return context.setBeschrijving(boekExemplaar);
    }

    public ArrayList<BoekExemplaar> getBoekExemplaren() throws SQLException, ClassNotFoundException {
        return context.getBoekExemplaren();
    }

    public boolean addAuteur(Auteur auteur) throws SQLException, ClassNotFoundException {
        return context.addAuteur(auteur);
    }

    public boolean addUitgever(Uitgever uitgever) throws SQLException, ClassNotFoundException {
        return context.addUitgever(uitgever);
    }
}

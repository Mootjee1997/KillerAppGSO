package sample.Server;
import sample.Models.*;
import java.rmi.Remote;;
import java.util.ArrayList;

public interface IServer extends Remote {
    Gebruiker login(String gebruikersnaam, String wachtwoord) throws Exception;
    int registreer(Gebruiker gebruiker) throws Exception;
    boolean addBoek(Boek boek) throws Exception;
    boolean leenUit(int volgnummer, Boek boek, Gebruiker gebruiker) throws Exception;
    boolean retourneer(BoekExemplaar boek, Gebruiker gebruiker) throws Exception;
    boolean wijzigGegevens(Gebruiker gebruiker) throws Exception;
    boolean setBeschrijving(BoekExemplaar boekExemplaar) throws Exception;
    ArrayList<Auteur> getAuteurs() throws Exception;
    ArrayList<Uitgever> getUitgevers() throws Exception;
    ArrayList<Boek> getBoeken() throws Exception;
    ArrayList<Gebruiker> getGebruikers() throws Exception;
    ArrayList<BoekExemplaar> getBoekExemplaren() throws Exception;
    boolean addAuteur(Auteur auteur) throws Exception;
    boolean addUitgever(Uitgever uitgever) throws Exception;
}

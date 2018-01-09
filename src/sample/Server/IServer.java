package sample.Server;
import sample.Models.*;
import java.rmi.Remote;;
import java.util.ArrayList;

public interface IServer extends Remote {
    Gebruiker login(String gebruikersnaam, String wachtwoord) throws Exception;
    int registreer(Gebruiker gebruiker) throws Exception;
    boolean leenUit(int volgnummer, Gebruiker gebruiker) throws Exception;
    boolean retourneer(int volgnummer, Gebruiker gebruiker) throws Exception;
    boolean wijzigGegevens(Gebruiker gebruiker) throws Exception;
    boolean setBeschrijving(int volgnummer, String beschrijving) throws Exception;
    ArrayList<String> getBoeken() throws Exception;
    ArrayList<String> getAuteurs() throws Exception;
    ArrayList<String> getUitgevers() throws Exception;
    ArrayList<String> getGebruikers() throws Exception;
    ArrayList<String> getBoekExemplaren() throws Exception;
    ArrayList<String> getGeleendeBoeken(Gebruiker gebruiker) throws Exception;
    ArrayList<String> getBeschikbareExemplaren(String titel) throws Exception;
    boolean addAuteur(Auteur auteur) throws Exception;
    boolean addUitgever(Uitgever uitgever) throws Exception;
    void addBoek(Boek boek, String aantalExemplaren) throws Exception;
    Boek zoekBoek(String titel) throws Exception;
    Auteur zoekAuteur(String naam) throws Exception;
    Uitgever zoekUitgever(String naam) throws Exception;
    Gebruiker zoekGebruiker(String gebruikernaam) throws Exception;
    BoekExemplaar zoekBoekExemplaar(int volgnummer) throws Exception;
}

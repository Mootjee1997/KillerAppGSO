package sample.server;
import fontys.IRemotePropertyListener;
import sample.models.*;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IServer extends Remote {
    Gebruiker login(String gebruikersnaam, String wachtwoord) throws RemoteException;
    int registreer(Gebruiker gebruiker) throws RemoteException;
    boolean leenUit(int volgnummer, Gebruiker gebruiker) throws RemoteException;
    boolean retourneer(int volgnummer, Gebruiker gebruiker) throws RemoteException;
    boolean wijzigGegevens(Gebruiker gebruiker) throws RemoteException;
    boolean setBeschrijving(int volgnummer, String beschrijving) throws RemoteException;
    List<String> getBoeken() throws RemoteException;
    List<String> getAuteurs() throws RemoteException;
    List<String> getUitgevers() throws RemoteException;
    List<String> getGebruikers() throws RemoteException;
    List<String> getBoekExemplaren() throws RemoteException;
    List<String> getGeleendeBoeken(Gebruiker gebruiker) throws RemoteException;
    List<String> getBeschikbareExemplaren(String titel) throws RemoteException;
    boolean addAuteur(Auteur auteur) throws RemoteException;
    boolean addUitgever(Uitgever uitgever) throws RemoteException;
    void addBoek(Boek boek, String aantalExemplaren) throws RemoteException;
    Boek zoekBoek(String titel) throws RemoteException;
    Auteur zoekAuteur(String naam) throws RemoteException;
    Uitgever zoekUitgever(String naam) throws RemoteException;
    Gebruiker zoekGebruiker(String gebruikernaam) throws RemoteException;
    BoekExemplaar zoekBoekExemplaar(int volgnummer) throws RemoteException;
    void subscribePublisher(IRemotePropertyListener listener, String property) throws RemoteException;
}

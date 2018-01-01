package sample.Server;
import sample.Models.Boek;
import sample.Models.BoekExemplaar;
import sample.Models.Gebruiker;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface IServer extends Remote {
    Gebruiker login(String gebruikersnaam, String wachtwoord) throws Exception;
    int registreer(Gebruiker gebruiker) throws Exception;
    boolean addBoek(Boek boek) throws Exception;
    boolean leenUit(Boek boek, Gebruiker gebruiker) throws Exception;
    boolean retourneer(BoekExemplaar boek, Gebruiker gebruiker) throws Exception;
    boolean wijzigGegevens(Gebruiker gebruiker) throws Exception;
    ArrayList<Boek> getBoeken() throws Exception;
    ArrayList<Gebruiker> getGebruikers() throws Exception;
}

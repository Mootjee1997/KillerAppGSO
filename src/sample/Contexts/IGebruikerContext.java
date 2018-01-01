package sample.Contexts;
import sample.Database.Database;
import sample.Models.BoekExemplaar;
import sample.Models.Gebruiker;
import sample.Models.Gegevens;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class IGebruikerContext {
    private Database db = new Database();
    private ResultSet rs = null;
    private String query;

    public Gebruiker login(String gebruikernaam, String wachtwoord) throws Exception {
        query = "SELECT id, gebruikersnaam, wachtwoord, status, naam, email, Woonplaats, TelefoonNr FROM gebruiker WHERE gebruikersnaam = ? AND wachtwoord = ?";
        PreparedStatement ps = db.getConnection().prepareStatement(query);
        ps.setString(1, gebruikernaam);
        ps.setString(2, wachtwoord);
        rs = db.loadObject(ps);

        if (rs.next()) {
            return new Gebruiker(rs.getInt("ID"), rs.getString("Gebruikersnaam"), rs.getString("Wachtwoord"), rs.getString("Status"), new Gegevens(rs.getString("Naam"), rs.getString("Email"), rs.getString("Woonplaats"), rs.getString("TelefoonNr")));
        }
        return null;
    }

    public int registreer(Gebruiker gebruiker) throws Exception {
        query = "INSERT INTO gebruiker (status, gebruikersnaam, wachtwoord, naam, woonplaats, email, telefoonNr) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = db.getConnection().prepareStatement(query);
        ps.setString(1, gebruiker.getStatus());
        ps.setString(2, gebruiker.getGebruikersnaam());
        ps.setString(3, gebruiker.getWachtwoord());
        ps.setString(4, gebruiker.getGegevens().getNaam());
        ps.setString(5, gebruiker.getGegevens().getWoonplaats());
        ps.setString(6, gebruiker.getGegevens().getEmail());
        ps.setString(7, gebruiker.getGegevens().getTelefoonNr());
        db.update(ps);

        String query1 = "SELECT id FROM Gebruiker WHERE Gebruikersnaam = ? AND Wachtwoord = ?";
        PreparedStatement ps1 = db.getConnection().prepareStatement(query1);
        ps1.setString(1, gebruiker.getGebruikersnaam());
        ps1.setString(2, gebruiker.getWachtwoord());
        return db.select(ps1);
    }

    public boolean wijzigGegevens(Gebruiker gebruiker) throws Exception {
        query = "UPDATE gebruiker SET Wachtwoord = ?, Naam = ?, Woonplaats = ?, Email = ?, TelefoonNr = ? WHERE ID = ?";
        PreparedStatement ps = db.getConnection().prepareStatement(query);
        ps.setString(1, gebruiker.getWachtwoord());
        ps.setString(2, gebruiker.getGegevens().getNaam());
        ps.setString(3, gebruiker.getGegevens().getWoonplaats());
        ps.setString(4, gebruiker.getGegevens().getEmail());
        ps.setString(5, gebruiker.getGegevens().getTelefoonNr());
        ps.setInt(6, gebruiker.getId());
        return db.update(ps);
    }

    public ArrayList<Gebruiker> getGebruikers() throws Exception {
        ArrayList<Gebruiker> gebruikers = new ArrayList<>();
        query = "SELECT * FROM gebruiker";
        PreparedStatement ps = db.getConnection().prepareStatement(query);
        rs = db.loadObject(ps);

        while (rs.next()) {
            Gebruiker gebruiker = new Gebruiker(rs.getInt("ID"), rs.getString("Gebruikersnaam"), rs.getString("Wachtwoord"), rs.getString("Status"), new Gegevens(rs.getString("Naam"), rs.getString("Email"), rs.getString("Woonplaats"), rs.getString("TelefoonNr")));

            ResultSet rs2;
            query = "SELECT * FROM BoekExemplaar WHERE ID IN (SELECT BoekID FROM `Gebruiker-BoekExemplaar` WHERE GebruikerID = ?)";
            PreparedStatement ps2 = db.getConnection().prepareStatement(query);
            ps2.setInt(1, gebruiker.getId());
            rs2 = db.loadObject(ps2);

            while (rs2.next()) {
                BoekExemplaar boekExemplaar = new BoekExemplaar(rs2.getInt("ID"), rs2.getString("Beschrijving"), rs2.getBoolean("Beschikbaar"));
                gebruiker.addGeleendeBoek(boekExemplaar);
            }
            gebruikers.add(gebruiker);
        }
        return gebruikers;
    }
}

package sample.Contexts;
import sample.Database.Database;
import sample.Models.Gebruiker;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class IGebruikerContext {
    Database db = new Database();
    ResultSet rs = null;
    String query;

    public Gebruiker login(String gebruikernaam, String wachtwoord) throws Exception {
        query = "SELECT id, gebruikersnaam, wachtwoord, status, naam, email, Woonplaats, TelefoonNr FROM gebruiker WHERE gebruikersnaam = ? AND wachtwoord = ?";
        PreparedStatement ps = db.getConnection().prepareStatement(query);
        ps.setString(1, gebruikernaam);
        ps.setString(2, wachtwoord);
        rs = db.loadObject(ps);

        if (rs.next()) {
                return new Gebruiker
                        (
                                rs.getInt("ID"),
                                rs.getString("Gebruikersnaam"),
                                rs.getString("Wachtwoord"),
                                rs.getString("Status"),
                                rs.getString("Naam"),
                                rs.getString("Email"),
                                rs.getString("Woonplaats"),
                                rs.getString("TelefoonNr")
                        );
        }
        return null;
    }

    public int registreer(Gebruiker gebruiker) throws Exception {
        query = "INSERT INTO gebruiker (status, gebruikersnaam, wachtwoord, naam, woonplaats, email, telefoonNr) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = db.getConnection().prepareStatement(query);
        ps.setString(1, gebruiker.getStatus());
        ps.setString(2, gebruiker.getGebruikersnaam());
        ps.setString(3, gebruiker.getWachtwoord());
        ps.setString(4, gebruiker.getNaam());
        ps.setString(5, gebruiker.getWoonplaats());
        ps.setString(6, gebruiker.getEmail());
        ps.setString(7, gebruiker.getTelefoonNr());
        String query1 = "SELECT id FROM Gebruiker WHERE Gebruikersnaam = ? AND Wachtwoord = ?";
        PreparedStatement ps1 = db.getConnection().prepareStatement(query1);
        ps1.setString(1, gebruiker.getGebruikersnaam());
        ps1.setString(2, gebruiker.getWachtwoord());
        return db.registreer(ps, ps1);
    }

    public boolean wijzigGegevens(Gebruiker gebruiker) throws Exception {
        query = "UPDATE gebruiker SET Wachtwoord = ?, Naam = ?, Woonplaats = ?, Email = ?, TelefoonNr = ? WHERE ID = ?";
        PreparedStatement ps = db.getConnection().prepareStatement(query);
        ps.setString(1, gebruiker.getWachtwoord());
        ps.setString(2, gebruiker.getNaam());
        ps.setString(3, gebruiker.getWoonplaats());
        ps.setString(4, gebruiker.getEmail());
        ps.setString(5, gebruiker.getTelefoonNr());
        ps.setInt(6, gebruiker.getId());
        return db.update(ps);
    }
}

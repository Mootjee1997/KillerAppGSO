package sample.Contexts;
import sample.Database.Database;
import sample.Enums.Status;
import sample.Models.BoekExemplaar;
import sample.Models.Gebruiker;
import sample.Models.Gegevens;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class IGebruikerContext {
    private Database db = new Database();
    private ResultSet rs = null;
    private String query;

    public Gebruiker login(String gebruikernaam, String wachtwoord) throws Exception {
        try {
            query = "SELECT id, gebruikersnaam, wachtwoord, status, naam, email, Woonplaats, TelefoonNr FROM gebruiker WHERE gebruikersnaam = ? AND wachtwoord = ?";
            PreparedStatement ps = db.getConnection().prepareStatement(query);
            ps.setString(1, gebruikernaam);
            ps.setString(2, wachtwoord);
            rs = db.loadObject(ps);

            Gebruiker gebruiker = null;
            if (rs.next()) {
                Status status = Status.KLANT;
                if (rs.getString("Status").equals("Medewerker")) status = Status.MEDEWERKER;
                gebruiker = new Gebruiker(rs.getInt("ID"), rs.getString("Gebruikersnaam"), rs.getString("Wachtwoord"), status, new Gegevens(rs.getString("Naam"), rs.getString("Email"), rs.getString("Woonplaats"), rs.getString("TelefoonNr")));

                ResultSet rs1;
                query = "SELECT * FROM BoekExemplaar WHERE ID IN (SELECT BoekID FROM `Gebruiker-BoekExemplaar` WHERE GebruikerID = ?)";
                PreparedStatement ps1 = db.getConnection().prepareStatement(query);
                ps1.setInt(1, gebruiker.getId());
                rs1 = db.loadObject(ps1);

                while (rs1.next()) {
                    BoekExemplaar boekExemplaar = new BoekExemplaar(rs1.getInt("ID"), rs1.getString("Beschrijving"), rs1.getBoolean("Beschikbaar"), rs1.getInt("Volgnummer"));
                    gebruiker.addGeleendeBoek(boekExemplaar);
                }
            }
            return gebruiker;
        }
        catch (Exception ex) {
            System.out.println(ex);
        }
        return null;
    }

    public int registreer(Gebruiker gebruiker) throws Exception {
        try {
            query = "INSERT INTO gebruiker (status, gebruikersnaam, wachtwoord, naam, woonplaats, email, telefoonNr) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = db.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, gebruiker.getStatus().toString());
            ps.setString(2, gebruiker.getGebruikersnaam());
            ps.setString(3, gebruiker.getWachtwoord());
            ps.setString(4, gebruiker.getGegevens().getNaam());
            ps.setString(5, gebruiker.getGegevens().getWoonplaats());
            ps.setString(6, gebruiker.getGegevens().getEmail());
            ps.setString(7, gebruiker.getGegevens().getTelefoonNr());
            ps.execute();

            rs = ps.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);
        }
        catch (Exception ex) {
            System.out.println(ex);
        }
        return -1;
    }

    public boolean wijzigGegevens(Gebruiker gebruiker) throws Exception {
        try {
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
        catch (Exception ex) {
            System.out.println(ex);
        }
        return false;
    }

    public ArrayList<Gebruiker> getGebruikers() throws Exception {
        try {
            ArrayList<Gebruiker> gebruikers = new ArrayList<>();
            query = "SELECT * FROM gebruiker";
            PreparedStatement ps = db.getConnection().prepareStatement(query);
            rs = db.loadObject(ps);

            while (rs.next()) {
                Status status = Status.KLANT;
                if (rs.getString("Status").equals("Medewerker")) status = Status.MEDEWERKER;
                Gebruiker gebruiker = new Gebruiker(rs.getInt("ID"), rs.getString("Gebruikersnaam"), rs.getString("Wachtwoord"), status, new Gegevens(rs.getString("Naam"), rs.getString("Email"), rs.getString("Woonplaats"), rs.getString("TelefoonNr")));

                ResultSet rs2;
                query = "SELECT * FROM BoekExemplaar WHERE ID IN (SELECT BoekID FROM `Gebruiker-BoekExemplaar` WHERE GebruikerID = ?)";
                PreparedStatement ps2 = db.getConnection().prepareStatement(query);
                ps2.setInt(1, gebruiker.getId());
                rs2 = db.loadObject(ps2);

                while (rs2.next()) {
                    BoekExemplaar boekExemplaar = new BoekExemplaar(rs2.getInt("ID"), rs2.getString("Beschrijving"), rs2.getBoolean("Beschikbaar"), rs2.getInt("Volgnummer"));
                    gebruiker.addGeleendeBoek(boekExemplaar);
                }
                gebruikers.add(gebruiker);
            }
            return gebruikers;
        }
        catch (Exception ex) {
            System.out.println(ex);
        }
        return null;
    }
}

package sample.Contexts;
import sample.Database.Database;
import sample.Models.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class IBoekContext {
    private Database db = new Database();
    private ResultSet rs = null;
    private String query;

    public Boek addBoek(Boek boek) throws Exception {
        query = "INSERT INTO Boek (UitgeverID, Titel, Descriptie) VALUES (?, ?, ?)";
        PreparedStatement ps = db.getConnection().prepareStatement(query);
        db.update(ps);

        query = "SELECT ID FROM Boek WHERE Titel = ? AND Descriptie = ?";
        PreparedStatement ps1 = db.getConnection().prepareStatement(query);
        ps1.setString(1, boek.getTitel());
        ps1.setString(2, boek.getDescriptie());
        int id = db.select(ps1);
        boek.setId(id);
        return boek;
    }

    public boolean leenUit(BoekExemplaar boek, Gebruiker gebruiker) throws Exception {
        query = "INSERT INTO `Gebruiker-Boekexemplaar` (GebruikerID, BoekID) VALUES (?, ?)";
        PreparedStatement ps = db.getConnection().prepareStatement(query);
        ps.setInt(1, gebruiker.getId());
        ps.setInt(2, boek.getId());
        return db.update(ps);
    }

    public boolean retourneer(BoekExemplaar boek, Gebruiker gebruiker) throws Exception {
        query = "DELETE FROM `Gebruiker-Boekexemplaar` WHERE GebruikerID = ? && BoekID = ?";
        PreparedStatement ps = db.getConnection().prepareStatement(query);
        ps.setInt(1, gebruiker.getId());
        ps.setInt(2, boek.getId());
        return db.update(ps);
    }

    public ArrayList<Boek> getBoeken() throws Exception {
        query = "SELECT * FROM Boek";
        PreparedStatement ps = db.getConnection().prepareStatement(query);
        rs = db.loadObject(ps);

        ArrayList<Boek> boeken = new ArrayList<>();
        while (rs.next()) {
            Boek boek = new Boek(rs.getInt("ID"), rs.getString("Titel"), rs.getString("Descriptie"));

            ResultSet rs1;
            query = "SELECT * FROM Uitgever WHERE ID = ?";
            PreparedStatement ps2 = db.getConnection().prepareStatement(query);
            ps2.setInt(1, rs.getInt("UitgeverID"));
            rs1 = db.loadObject(ps2);

            while (rs1.next()) {
                Uitgever uitgever = new Uitgever(new Gegevens(rs1.getString("Naam"), rs1.getString("Email"), rs1.getString("Woonplaats"), rs1.getString("TelefoonNr")));
                boek.setUitgever(uitgever);
            }

            ResultSet rs2;
            query = "SELECT * FROM Auteur WHERE ID in (SELECT ID FROM `Auteur-Boek` WHERE BoekID = ?)";
            PreparedStatement ps3 = db.getConnection().prepareStatement(query);
            ps3.setInt(1, boek.getId());
            rs2 = db.loadObject(ps3);

            while (rs2.next()) {
                Auteur auteur = new Auteur(new Gegevens(rs2.getString("Naam"), rs2.getString("Email"), rs2.getString("Woonplaats"), rs2.getString("TelefoonNr")));
                boek.setAuteurs(auteur);
            }

            ResultSet rs3;
            query = "SELECT * FROM BoekExemplaar WHERE BoekID = ?";
            PreparedStatement ps4 = db.getConnection().prepareStatement(query);
            ps4.setInt(1, boek.getId());
            rs3 = db.loadObject(ps4);

            while (rs3.next()) {
                BoekExemplaar boekExemplaar = new BoekExemplaar(rs3.getInt("ID"), boek, rs3.getString("Beschrijving"), rs3.getBoolean("Beschikbaar"));
                boek.setBoekExemplaren(boekExemplaar);
            }
            boeken.add(boek);
        }
        return boeken;
    }
}

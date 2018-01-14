package sample.contexts;
import sample.database.Database;
import sample.models.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IBoekContext {
    private static final String TELEFOONNR = "TelefoonNr";
    private static final String WOONPLAATS = "Woonplaats";
    private static final String EMAIL = "Email";
    private Logger logger = Logger.getLogger("Logger");
    private Database db = new Database();
    private ResultSet rs = null;
    private String query;

    public boolean leenUit(BoekExemplaar boek, Gebruiker gebruiker) {
        try {
            query = "INSERT INTO `Gebruiker-Boekexemplaar` (GebruikerID, BoekID) VALUES (?, ?)";
            PreparedStatement ps = db.getConnection().prepareStatement(query);
            ps.setInt(1, gebruiker.getId());
            ps.setInt(2, boek.getId());
            db.update(ps);

            query = "UPDATE BoekExemplaar SET Beschikbaar = FALSE WHERE ID = ?";
            PreparedStatement ps1 = db.getConnection().prepareStatement(query);
            ps1.setInt(1, boek.getId());
            return db.update(ps1);
        }
        catch (Exception ex) {
            logger.log( Level.WARNING, ex.toString(), ex);
        }
        return false;
    }

    public boolean retourneer(BoekExemplaar boek, Gebruiker gebruiker) {
        try {
            query = "DELETE FROM `Gebruiker-Boekexemplaar` WHERE GebruikerID = ? AND BoekID = ?";
            PreparedStatement ps = db.getConnection().prepareStatement(query);
            ps.setInt(1, gebruiker.getId());
            ps.setInt(2, boek.getId());
            db.update(ps);

            query = "UPDATE BoekExemplaar SET Beschikbaar = TRUE WHERE ID = ?";
            PreparedStatement ps1 = db.getConnection().prepareStatement(query);
            ps1.setInt(1, boek.getId());
            return db.update(ps1);
        }
        catch (Exception ex) {
            logger.log( Level.WARNING, ex.toString(), ex);
        }
        return false;
    }

    public boolean setBeschrijving(BoekExemplaar boekExemplaar) {
        try {
            query = "UPDATE BoekExemplaar SET Beschrijving = ? WHERE ID = ?";
            PreparedStatement ps = db.getConnection().prepareStatement(query);
            ps.setString(1, boekExemplaar.getBeschrijving());
            ps.setInt(2, boekExemplaar.getId());
            return db.update(ps);
        }
        catch (Exception ex) {
            logger.log( Level.WARNING, ex.toString(), ex);
        }
        return false;
    }

    public boolean addAuteur(Auteur auteur) {
        try {
            query = "INSERT INTO Auteur (Naam) VALUES (?)";
            PreparedStatement ps = db.getConnection().prepareStatement(query);
            ps.setString(1, auteur.getGegevens().getNaam());
            return db.update(ps);
        }
        catch (Exception ex) {
            logger.log( Level.WARNING, ex.toString(), ex);
        }
        return false;
    }

    public boolean addUitgever(Uitgever uitgever) {
        try {
            query = "INSERT INTO Uitgever (Naam) VALUES (?)";
            PreparedStatement ps = db.getConnection().prepareStatement(query);
            ps.setString(1, uitgever.getGegevens().getNaam());
            return db.update(ps);
        }
        catch (Exception ex) {
            logger.log( Level.WARNING, ex.toString(), ex);
        }
        return false;
    }

    public int addBoekExemplaar(Boek boek, int volgnr) {
        try {
            query = "INSERT INTO BoekExemplaar (BoekID, Volgnummer) VALUES (?, ?)";
            PreparedStatement ps = db.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, boek.getId());
            ps.setInt(2, volgnr);
            ps.execute();
            rs = ps.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);
        }
        catch (Exception ex) {
            logger.log( Level.WARNING, ex.toString(), ex);
        }
        return -1;
    }

    public Boek addBoek(Boek boek) {
        try {
            query = "INSERT INTO Boek (UitgeverID, Titel, Descriptie) VALUES ((SELECT ID FROM Uitgever WHERE Naam = ?), ?, ?)";
            PreparedStatement ps = db.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, boek.getUitgever().getGegevens().getNaam());
            ps.setString(2, boek.getTitel());
            ps.setString(3, boek.getDescriptie());
            ps.execute();
            rs = ps.getGeneratedKeys();
            if (rs.next()) boek.setId(rs.getInt(1));

            for (Auteur auteur : boek.getAuteurs()) {
                query = "INSERT INTO `Auteur-Boek` (AuteurID, BoekID) VALUES ((SELECT ID FROM Auteur WHERE Naam = ?), ?)";
                PreparedStatement ps1 = db.getConnection().prepareStatement(query);
                ps1.setString(1, auteur.getGegevens().getNaam());
                ps1.setInt(2, boek.getId());
                db.update(ps1);
            }
            return boek;
        }
        catch (Exception ex) {
            logger.log( Level.WARNING, ex.toString(), ex);
        }
        return null;
    }

    public List<Auteur> getAuteurs() {
        try {
            query = "SELECT * FROM Auteur";
            PreparedStatement ps = db.getConnection().prepareStatement(query);
            rs = db.loadObject(ps);

            ArrayList<Auteur> auteurs = new ArrayList<>();
            while (rs.next()) {
                Auteur auteur = new Auteur(new Gegevens(rs.getString("Naam"), rs.getString(EMAIL), rs.getString(WOONPLAATS), rs.getString(TELEFOONNR)));
                auteurs.add(auteur);
            }
            return auteurs;
        }
        catch (Exception ex) {
            logger.log( Level.WARNING, ex.toString(), ex);
        }
        return new ArrayList<>();
    }

    public List<Uitgever> getUitgevers() {
        try {
            query = "SELECT * FROM Uitgever";
            PreparedStatement ps = db.getConnection().prepareStatement(query);
            rs = db.loadObject(ps);

            ArrayList<Uitgever> uitgevers = new ArrayList<>();
            while (rs.next()) {
                Uitgever uitgever = new Uitgever(new Gegevens(rs.getString("Naam"), rs.getString(EMAIL), rs.getString(WOONPLAATS), rs.getString(TELEFOONNR)));
                uitgevers.add(uitgever);
            }
            return uitgevers;
        }
        catch (Exception ex) {
            logger.log( Level.WARNING, ex.toString(), ex);
        }
        return new ArrayList<>();
    }

    public List<BoekExemplaar> getBoekExemplaren() {
        try {
            query = "SELECT * FROM BoekExemplaar";
            PreparedStatement ps = db.getConnection().prepareStatement(query);
            rs = db.loadObject(ps);

            ArrayList<BoekExemplaar> boekExemplaren = new ArrayList<>();
            while (rs.next()) {
                BoekExemplaar boekExemplaar = new BoekExemplaar(rs.getInt("ID"), rs.getString("Beschrijving"), rs.getBoolean("Beschikbaar"), rs.getInt("Volgnummer"));

                ResultSet rs1;
                query = "SELECT * FROM Boek WHERE ID = (SELECT BoekID FROM BoekExemplaar WHERE ID = ?)";
                PreparedStatement ps1 = db.getConnection().prepareStatement(query);
                ps1.setInt(1, boekExemplaar.getId());
                rs1 = db.loadObject(ps1);

                while (rs1.next()) {
                    Boek boek = new Boek(rs1.getInt("ID"), rs1.getString("Titel"), rs1.getString("Descriptie"));
                    boekExemplaar.setBoek(boek);
                }
                boekExemplaren.add(boekExemplaar);
            }
            return boekExemplaren;
        }
        catch (Exception ex) {
            logger.log( Level.WARNING, ex.toString(), ex);
        }
        return new ArrayList<>();
    }

    public List<Boek> getBoeken() {
        try {
            query = "SELECT * FROM Boek";
            PreparedStatement ps = db.getConnection().prepareStatement(query);
            rs = db.loadObject(ps);

            List<Boek> boeken = new ArrayList<>();
            while (rs.next()) {
                Boek boek = new Boek(rs.getInt("ID"), rs.getString("Titel"), rs.getString("Descriptie"));

                ResultSet rs1;
                query = "SELECT * FROM Uitgever WHERE ID = ?";
                PreparedStatement ps2 = db.getConnection().prepareStatement(query);
                ps2.setInt(1, rs.getInt("UitgeverID"));
                rs1 = db.loadObject(ps2);

                while (rs1.next()) {
                    Uitgever uitgever = new Uitgever(new Gegevens(rs1.getString("Naam"), rs1.getString(EMAIL), rs1.getString(WOONPLAATS), rs1.getString(TELEFOONNR)));
                    boek.setUitgever(uitgever);
                }

                ResultSet rs2;
                query = "SELECT * FROM Auteur WHERE ID in (SELECT AuteurID FROM `Auteur-Boek` WHERE BoekID = ?)";
                PreparedStatement ps3 = db.getConnection().prepareStatement(query);
                ps3.setInt(1, boek.getId());
                rs2 = db.loadObject(ps3);

                while (rs2.next()) {
                    Auteur auteur = new Auteur(new Gegevens(rs2.getString("Naam"), rs2.getString(EMAIL), rs2.getString(WOONPLAATS), rs2.getString(TELEFOONNR)));
                    boek.setAuteurs(auteur);
                }

                ResultSet rs3;
                query = "SELECT * FROM BoekExemplaar WHERE BoekID = ?";
                PreparedStatement ps4 = db.getConnection().prepareStatement(query);
                ps4.setInt(1, boek.getId());
                rs3 = db.loadObject(ps4);

                while (rs3.next()) {
                    BoekExemplaar boekExemplaar = new BoekExemplaar(rs3.getInt("ID"), boek, rs3.getString("Beschrijving"), rs3.getBoolean("Beschikbaar"), rs3.getInt("Volgnummer"));
                    boek.setBoekExemplaren(boekExemplaar);
                }
                boeken.add(boek);
            }
            return boeken;
        }
        catch (Exception ex) {
            logger.log( Level.WARNING, ex.toString(), ex);
        }
        return new ArrayList<>();
    }
}

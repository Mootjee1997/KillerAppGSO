package Unittests;
import org.junit.Test;
import sample.Database.Database;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.junit.Assert.*;

public class DatabaseTest {
    Database db = new Database();
    ResultSet rs;
    String query;

    @Test
    public void getConnection() throws Exception {
        assertNotNull(db.getConnection());
    }

    @Test
    public void loadObject() throws Exception {
        query = "SELECT ID FROM gebruiker WHERE gebruikersnaam = ? AND wachtwoord = ?";
        PreparedStatement ps = db.getConnection().prepareStatement(query);
        ps.setString(1, "Klant");
        ps.setString(2, "klant");
        rs = db.loadObject(ps);

        if (rs.next()) assertEquals(14, rs.getInt("ID"));
    }
}
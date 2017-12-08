package sample.Database;
import sample.Models.Gebruiker;
import java.sql.*;

public class Database {
    private String query;
    private PreparedStatement ps;
    private Connection conn;
    private ResultSet rs;

    public Connection getConnection() throws SQLException, ClassNotFoundException {
        if (conn == null) {
            Class.forName("com.mysql.jdbc.Driver");
            this.conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/KillerApp", "root", "raadjeniet");
            return conn;
        }
        return conn;
    }

    public ResultSet loadObject(PreparedStatement ps) throws SQLException {
        try {
            getConnection();
            this.ps = ps;
            return rs = ps.executeQuery();
        }
        catch (Exception ex){
            System.out.println("Error: " + ex);
        }
        return null;
    }

    public int registreer(PreparedStatement ps, PreparedStatement ps1) throws SQLException {
        try {
            getConnection();
            ps.executeUpdate();
            rs = ps1.executeQuery();
            if (rs.next()) {
                return rs.getInt("ID");
            }
        }
        catch (Exception ex) {
            System.out.println("Error: " + ex);
        }
        return -1;
    }

    public boolean update(PreparedStatement ps) throws SQLException {
        try {
            getConnection();
            ps.executeUpdate();
            return true;
        }
        catch (Exception ex) {
            System.out.println("Error: " + ex);
        }
        return false;
    }
}

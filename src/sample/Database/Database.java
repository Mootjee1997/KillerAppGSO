package sample.Database;
import java.sql.*;

public class Database {
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
            return rs = ps.executeQuery();
        }
        catch (Exception ex){
            System.out.println("Error: " + ex);
        }
        return null;
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

package sample.database;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {
    private Logger logger = Logger.getLogger("Logger");
    private Connection conn;

    public Connection getConnection() throws SQLException, ClassNotFoundException {
        if (conn == null) {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/KillerApp", "root", "raadjeniet");
            return conn;
        }
        return conn;
    }

    public ResultSet loadObject(PreparedStatement ps) throws SQLException {
        try {
            getConnection();
            return ps.executeQuery();
        }
        catch (Exception ex){
            logger.log( Level.WARNING, ex.toString(), ex);
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
            logger.log( Level.WARNING, ex.toString(), ex);
        }
        return false;
    }
}

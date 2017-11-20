package sample.Database;
import java.sql.*;

public class Database {
    private Connection connection;

    public Connection getConnection() throws SQLException {
        if (connection == null) {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/KillerApp", "root", "raadjeniet");
            return connection;
        }
        return null;
    }

    public ResultSet getData(String query) throws SQLException {
        try {
            getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement(query);
            return preparedStatement.executeQuery();
        }
        catch (Exception ex){

        }
        return null;
    }

    public boolean setData(String query, String[] values) throws SQLException {
        try {
            getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeQuery();
        }
        catch (Exception ex) {

        }
        return false;
    }
}

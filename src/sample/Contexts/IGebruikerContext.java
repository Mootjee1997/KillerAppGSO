package sample.Contexts;
import sample.Database.Database;
import sample.Models.Gebruiker;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IGebruikerContext {
    public Gebruiker login(String gebruikernaam, String wachtwoord) throws Exception {
        String query = "SELECT id, username, name, email FROM Account WHERE username = ?";
//        ResultSet resultSet = Database.getData(query);
//
//        if (resultSet.next()) {
//            return new Gebruiker
//                    (
//                            resultSet.getInt("ID"),
//                            resultSet.getString("Gebruikernaam"),
//                            resultSet.getString("Wachtwoord"),
//                            resultSet.getString("Naam"),
//                            resultSet.getString("Email"),
//                            resultSet.getString("Woonplaats"),
//                            resultSet.getString("TelefoonNr")
//                    );
//        }
        return null;
    }

    public Gebruiker registreer(Gebruiker gebruiker) throws Exception {
        return null;
    }
}

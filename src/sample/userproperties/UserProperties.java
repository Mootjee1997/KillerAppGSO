package sample.userproperties;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserProperties {
    private Logger logger = Logger.getLogger("Logger");
    private static final String GEBRUIKERSNAAM = "Gebruikersnaam";
    private String url = "C:\\Users\\Mo\\Desktop\\UserPreferences";

    public void saveProperties(String gebruikersnaam, boolean isSelected) {
        try {
            if (!isSelected) gebruikersnaam = "";
            Properties props = new Properties();
            props.setProperty(GEBRUIKERSNAAM, gebruikersnaam);
            File file = new File(url);
            OutputStream out = new FileOutputStream(file);
            props.store(out, "UserPreferences");
            out. close();
        }
        catch (Exception ex) {
            logger.log( Level.WARNING, ex.toString(), ex);
        }
    }

    public List<String> loadProperties() {
        try {
            ArrayList<String> values = new ArrayList<>();
            Properties props = new Properties();
            File file = new File(url);
            if (file.exists()) {
                InputStream in = new FileInputStream(file);
                props.load(in);
                if (!props.getProperty(GEBRUIKERSNAAM).equals("")) {
                    values.add(props.getProperty(GEBRUIKERSNAAM));
                    values.add("True");
                }
                else {
                    values.add("");
                    values.add("False");
                }
                in.close();
                return values;
            }
        } catch (Exception ex) {
            logger.log( Level.WARNING, ex.toString(), ex);
        }
        return new ArrayList<>();
    }
}

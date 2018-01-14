package UserProperties;
import java.io.*;
import java.util.ArrayList;
import java.util.Properties;

public class UserProperties {

    public void saveProperties(String gebruikersnaam, boolean isSelected) {
        try {
            if (!isSelected) gebruikersnaam = "";
            Properties props = new Properties();
            props.setProperty("Gebruikersnaam", gebruikersnaam);
            File file = new File("C:\\Users\\Mo\\Desktop\\UserPreferences");
            OutputStream out = new FileOutputStream(file);
            props.store(out, "UserPreferences");
            out. close();
        }
        catch (Exception e ) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> loadProperties() {
        try {
            ArrayList<String> values = new ArrayList<>();
            Properties props = new Properties();
            File file = new File("C:\\Users\\Mo\\Desktop\\UserPreferences");
            if (file.exists()) {
                InputStream in = new FileInputStream(file);
                props.load(in);
                if (!props.getProperty("Gebruikersnaam").equals("")) {
                    values.add(props.getProperty("Gebruikersnaam"));
                    values.add("True");
                }
                else {
                    values.add("");
                    values.add("False");
                }
                in.close();
                return values;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

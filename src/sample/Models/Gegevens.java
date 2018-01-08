package sample.Models;
import java.io.Serializable;

public class Gegevens  implements Serializable {
    private String naam, email, woonplaats, telefoonNr;

    public String getNaam() {
        return naam;
    }
    public String getEmail() {
        return email;
    }
    public String getWoonplaats() { return woonplaats; }
    public String getTelefoonNr() {
        return telefoonNr;
    }

    public Gegevens(String naam, String email, String woonplaats, String telefoonNr){
        this.naam = naam;
        this.email = email;
        this.woonplaats = woonplaats;
        this.telefoonNr = telefoonNr;
    }

    public Gegevens(String naam){
        this.naam = naam;
    }
}

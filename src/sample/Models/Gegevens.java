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

    public void setNaam(String naam) {this.naam = naam; }
    public void setEmail(String email) {this.email = email; }
    public void setWoonplaats(String woonplaats) {this.woonplaats = woonplaats; }
    public void setTelefoonNr(String telefoonNr) {this.telefoonNr = telefoonNr; }

    public Gegevens(String naam, String email, String woonplaats, String telefoonNr){
        this.naam = naam;
        this.email = email;
        this.woonplaats = woonplaats;
        this.telefoonNr = telefoonNr;
    }
}

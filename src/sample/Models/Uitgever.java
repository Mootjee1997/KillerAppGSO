package sample.Models;
import java.io.Serializable;

public class Uitgever implements Serializable {
    private Gegevens gegevens;

    public Gegevens getGegevens() {
        return gegevens;
    }

    public Uitgever(Gegevens gegevens){
        this.gegevens = gegevens;
    }
}

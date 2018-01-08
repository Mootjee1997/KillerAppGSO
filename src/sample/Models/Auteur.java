package sample.Models;
import java.io.Serializable;

public class Auteur implements Serializable {
    private Gegevens gegevens;
    public Gegevens getGegevens() {
        return gegevens;
    }

    public Auteur(Gegevens gegevens){
        this.gegevens = gegevens;
    }

    @Override
    public String toString() {
        return gegevens.getNaam();
    }
}

package sample.models;
import java.io.Serializable;

public class BoekExemplaar implements Serializable {
    private int id;
    private int volgnummer;
    private Boek boek;
    private String beschrijving;
    private boolean beschikbaar;

    public int getId (){return this.id; }
    public int getVolgnummer() {
        return this.volgnummer;
    }
    public Boek getBoek() {return this.boek; }
    public String getBeschrijving() {
        return beschrijving;
    }
    public boolean getBeschikbaar() {return this.beschikbaar; }
    public void setBoek(Boek boek) {
        this.boek = boek;
    }
    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }
    public void setBeschikbaar(boolean beschikbaar) {
        this.beschikbaar = beschikbaar;
    }

    public BoekExemplaar(int id, Boek boek, String beschrijving, boolean beschikbaar, int volgnummer){
        this.volgnummer = volgnummer;
        this.id = id;
        this.boek = boek;
        this.beschrijving = beschrijving;
        this.beschikbaar = beschikbaar;
    }

    public BoekExemplaar(int id, String beschrijving, boolean beschikbaar, int volgnummer){
        this.volgnummer = volgnummer;
        this.id = id;
        this.beschrijving = beschrijving;
        this.beschikbaar = beschikbaar;
    }

    @Override
    public String toString() {
        return String.valueOf(this.volgnummer);
    }
}

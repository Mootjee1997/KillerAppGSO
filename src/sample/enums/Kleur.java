package sample.enums;

public enum Kleur {
    RED("RED"), GREEN("GREEN");

    private String kleurwaarde;

    Kleur(String value) {
        this.kleurwaarde = value;
    }

    public String getKleur() {
        return kleurwaarde;
    }

    @Override
    public String toString() {
        return this.getKleur();
    }
}

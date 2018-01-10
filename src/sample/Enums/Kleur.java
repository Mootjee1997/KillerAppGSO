package sample.Enums;

public enum Kleur {
    RED("RED"), GREEN("GREEN");

    private String kleur;

    Kleur(String kleur) {
        this.kleur = kleur;
    }

    public String getKleur() {
        return kleur;
    }

    @Override
    public String toString() {
        return this.getKleur();
    }
}

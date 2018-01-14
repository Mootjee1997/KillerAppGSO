package sample.enums;

public enum Status {
    MEDEWERKER("Medewerker"), KLANT("Klant");

    private String statuswaarde;

    Status(String value) {
        this.statuswaarde = value;
    }

    public String getStatus() {
        return statuswaarde;
    }

    @Override
    public String toString() {
        return this.getStatus();
    }
}
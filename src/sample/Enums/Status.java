package sample.Enums;

public enum Status {
    MEDEWERKER("Medewerker"), KLANT("Klant");

    private String status;

    Status(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return this.getStatus();
    }
}
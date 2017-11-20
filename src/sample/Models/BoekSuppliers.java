package sample.Models;

public abstract class BoekSuppliers {
    private int id;
    private String naam;
    private String email;
    private String telefoonNr;

    //Getters voor attributen
    public int getId () {return  this.id; }
    public String getNaam() {
        return naam;
    }
    public String getEmail() {
        return email;
    }
    public String getTelefoonNr() {
        return telefoonNr;
    }

    //Constructor voor het ophalen van een Auteur uit de database
    public BoekSuppliers (int id, String naam, String email, String telefoonNr) {
        this.id = id;
        this.naam = naam;
        this.email = email;
        this.telefoonNr = telefoonNr;
    }
}

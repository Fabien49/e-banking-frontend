package com.fabienit.biblioapi.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Livre {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "livre_id")
    int id;
    String titre;
    String auteur;
    String description;
    int isbn;
    String categorie;
    int quantite;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "bibliotheque_id", nullable = false)
    private Bibliotheque bibliotheque;

    @OneToMany(mappedBy = "livre", fetch = FetchType.EAGER, cascade =  CascadeType.ALL)
    private Set<Reservation> reservation;

    public Livre() {
    }

    public Livre(int id, String titre, String auteur, String description, int isbn, String categorie, int quantite, Bibliotheque bibliotheque) {
        this.id = id;
        this.titre = titre;
        this.auteur = auteur;
        this.description = description;
        this.isbn = isbn;
        this.categorie = categorie;
        this.quantite = quantite;
        this.bibliotheque = bibliotheque;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIsbn() {
        return isbn;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public Bibliotheque getBibliotheque() {
        return bibliotheque;
    }

    public void setBibliotheque(Bibliotheque bibliotheque) {
        this.bibliotheque = bibliotheque;
    }

    @Override
    public String toString() {
        return "Livre{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", auteur='" + auteur + '\'' +
                ", description='" + description + '\'' +
                ", isbn=" + isbn +
                ", categorie='" + categorie + '\'' +
                ", quantite=" + quantite +
                ", bibliotheque=" + bibliotheque +
                '}';
    }
}






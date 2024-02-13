package com.fabienit.biblioapi.model;

import javax.persistence.*;
import java.util.Set;


@Entity
public class Bibliotheque {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "bibliotheque_id")
    int id;
    String nom;
    String voie;
    String codePostal;
    String commune;

    @OneToMany(mappedBy = "bibliotheque", fetch = FetchType.EAGER, cascade =  CascadeType.ALL)
    private Set<Livre> livre;

/*    @OneToMany(mappedBy = "bibliotheque", fetch = FetchType.EAGER, cascade =  CascadeType.ALL)
    private Set<Usager> usager;*/



    public Bibliotheque() {
    }

    public Bibliotheque(int id, String nom, String voie, String codePostal, String commune) {
        this.id = id;
        this.nom = nom;
        this.voie = voie;
        this.codePostal = codePostal;
        this.commune = commune;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getVoie() {
        return voie;
    }

    public void setVoie(String voie) {
        this.voie = voie;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getCommune() {
        return commune;
    }

    public void setCommune(String commune) {
        this.commune = commune;
    }

    @Override
    public String toString() {
        return "Bibliotheque{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", voie='" + voie + '\'' +
                ", codePostal='" + codePostal + '\'' +
                ", commune='" + commune + '\'' +
                '}';
    }
}

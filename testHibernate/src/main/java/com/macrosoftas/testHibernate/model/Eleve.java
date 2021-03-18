package com.macrosoftas.testHibernate.model;


import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="eleve")
public class Eleve {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "eleve_id")
    private Long id;
    private String nom;
    private String prenom;
    private String mail;
    private String mdp;
    private String sexe;
    private String voie;
    private String codePostal;
    private String commune;
    private String niveau;


    public Eleve() {
    }

    public Eleve(Long id, String nom, String prenom, String mail, String mdp, String sexe, String voie, String codePostal, String commune, String niveau) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.mdp = mdp;
        this.sexe = sexe;
        this.voie = voie;
        this.codePostal = codePostal;
        this.commune = commune;
        this.niveau = niveau;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
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

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    @Override
    public String toString() {
        return "Eleve{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", mail='" + mail + '\'' +
                ", mdp='" + mdp + '\'' +
                ", sexe='" + sexe + '\'' +
                ", voie='" + voie + '\'' +
                ", codePostal='" + codePostal + '\'' +
                ", commune='" + commune + '\'' +
                ", niveau='" + niveau + '\'' +
                '}';
    }

            @ManyToOne
            @JoinColumn(name="salle_id")
            private Salle salle;

            @ManyToMany
            @JoinTable(
                    name = "cours_like",
                    joinColumns = @JoinColumn(name = "eleve_id"),
                    inverseJoinColumns = @JoinColumn(name = "cours_id"))
                    Set<Cours> likedCours;
}
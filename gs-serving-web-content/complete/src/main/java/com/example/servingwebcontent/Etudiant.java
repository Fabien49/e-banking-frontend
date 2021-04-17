package com.example.servingwebcontent;


import java.util.ArrayList;

public class Etudiant {

    private String nom;
    private String prenom;
    private int age;
    private String sexe;
    private String photoUrl;

    public Etudiant() {
    }

    public Etudiant(String nom, String prenom, int age, String sexe, String photoUrl) {
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.sexe = sexe;
        this.photoUrl = photoUrl;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    @Override
    public String toString() {
        return "Etudiant{" +
                "nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", age=" + age +
                ", sexe='" + sexe + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                '}';
    }
}

package com.fabienIT.friendsEscalade.model;/*
package com.fabienIT.escaladefriendsp6ocr.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="Topo")
public class Topo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String proprietaire;
    private String region;
    private int nbSites;
    private int nbVoies;
    private String cotationMin;
    private String cotationMax;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "site_id", nullable = false)
    private Site site;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;


    public Topo() {
    }

    public Topo(Long id, String nom, String proprietaire, String region, int nbSites, int nbVoies, String cotationMin, String cotationMax, Site site, User user, Reservation reservation) {
        this.id = id;
        this.nom = nom;
        this.proprietaire = proprietaire;
        this.region = region;
        this.nbSites = nbSites;
        this.nbVoies = nbVoies;
        this.cotationMin = cotationMin;
        this.cotationMax = cotationMax;
        this.site = site;
        this.user = user;
        this.reservation = reservation;
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

    public String getProprietaire() {
        return proprietaire;
    }

    public void setProprietaire(String proprietaire) {
        this.proprietaire = proprietaire;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public int getNbSites() {
        return nbSites;
    }

    public void setNbSites(int nbSites) {
        this.nbSites = nbSites;
    }

    public int getNbVoies() {
        return nbVoies;
    }

    public void setNbVoies(int nbVoies) {
        this.nbVoies = nbVoies;
    }

    public String getCotationMin() {
        return cotationMin;
    }

    public void setCotationMin(String cotationMin) {
        this.cotationMin = cotationMin;
    }

    public String getCotationMax() {
        return cotationMax;
    }

    public void setCotationMax(String cotationMax) {
        this.cotationMax = cotationMax;
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Topo topo = (Topo) o;
        return id.equals(topo.id);
    }

    @Override
    public String toString() {
        return "Topo{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", proprietaire='" + proprietaire + '\'' +
                ", region='" + region + '\'' +
                ", nbSites=" + nbSites +
                ", nbVoies=" + nbVoies +
                ", cotationMin='" + cotationMin + '\'' +
                ", cotationMax='" + cotationMax + '\'' +
                '}';
    }
}*/

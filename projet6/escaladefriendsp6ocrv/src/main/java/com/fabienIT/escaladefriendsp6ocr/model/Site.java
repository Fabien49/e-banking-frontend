package com.fabienIT.escaladefriendsp6ocr.model;


import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="site")
public class Site implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String region;
    private int nbVoies;
    private String cotationMin;
    private String cotationMax;
    private String description;
    private String site_image;
    private boolean certifie;

    @OneToMany(mappedBy = "site", fetch = FetchType.EAGER, cascade =  CascadeType.ALL)
    private Set<Topo> topo;

    @OneToMany(mappedBy = "site", fetch = FetchType.EAGER, cascade =  CascadeType.ALL)
    private Set<Commentaire> commentaire;


    public Site() {
    }

    public Site(Long id, String nom, String region, int nbVoies, String cotationMin, String cotationMax, String description, String site_image, boolean certifie) {
        this.id = id;
        this.nom = nom;
        this.region = region;
        this.nbVoies = nbVoies;
        this.cotationMin = cotationMin;
        this.cotationMax = cotationMax;
        this.description = description;
        this.site_image = site_image;
        this.certifie = certifie;
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

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSite_image() {
        return site_image;
    }

    public void setSite_image(String site_image) {
        this.site_image = site_image;
    }

    public Set<Topo> getTopo() {
        return topo;
    }

    public void setTopo(Set<Topo> topo) {
        this.topo = topo;
    }

    public Set<Commentaire> getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(Set<Commentaire> commentaire) {
        this.commentaire = commentaire;
    }

    public boolean isCertifie() {
        return certifie;
    }

    public void setCertifie(boolean certifie) {
        this.certifie = certifie;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Site site = (Site) o;
        return id.equals(site.id);
    }


    @Override
    public String toString() {
        return "Site{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", region='" + region + '\'' +
                ", nbVoies=" + nbVoies +
                ", cotationMin='" + cotationMin + '\'' +
                ", cotationMax='" + cotationMax + '\'' +
                ", description='" + description + '\'' +
                ", site_image='" + site_image + '\'' +
                ", certifie=" + certifie +
                ", topo=" + topo +
                ", commentaire=" + commentaire +
                '}';
    }
}

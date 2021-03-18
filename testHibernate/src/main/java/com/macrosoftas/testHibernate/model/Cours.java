package com.macrosoftas.testHibernate.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "cours")
public class Cours implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cours_id")
    private Long id;

    @Column(name = "intitule")
    private String intitule;

    @Column(name = "niveau")
    private String niveau;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    @Override
    public String toString() {
        return "Cours{" +
                "id=" + id +
                ", intitule='" + intitule + '\'' +
                ", niveau='" + niveau + '\'' +
                '}';
    }

    /*@ManyToMany(mappedBy = "likedCours")
    Set<Eleve> likes;*/
}

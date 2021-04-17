package com.fabienIT.escaladefriendsp6ocr.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name="Topo")
public class Topo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "topo_id", unique = true, nullable = false)
    private Long id;
    private String nom;
    private String region;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date date;
    private boolean disponible;
    private String description;

   /* @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "site_id", nullable = false)
    private Site site;*/

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

   /* @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation*/;

    @OneToMany(mappedBy = "topo", fetch = FetchType.LAZY)
    private Set<Reservation> reservation;


    public Topo() {
    }

    public Topo(Long id, String nom, String region, Date date, boolean disponible, String description, User user) {
        this.id = id;
        this.nom = nom;
        this.region = region;
        this.date = date;
        this.disponible = disponible;
        this.description = description;
        this.user = user;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Reservation> getReservation() {
        return reservation;
    }

    public void setReservation(Set<Reservation> reservation) {
        this.reservation = reservation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Topo topo = (Topo) o;
        return id.equals(topo.id);
    }

/*    @Override
    public String toString() {
        return "Topo{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", region='" + region + '\'' +
                ", date=" + date +
                ", disponible=" + disponible +
                ", description='" + description + '\'' +
                ", user=" + user +
                ", reservation=" + reservation +
                '}';
    }*/
}

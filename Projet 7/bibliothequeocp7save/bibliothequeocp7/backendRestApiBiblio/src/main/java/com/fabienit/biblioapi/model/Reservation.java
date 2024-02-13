package com.fabienit.biblioapi.model;


import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "reservation_id")
    private int id;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateReservation;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateRetour;
    private boolean prolongation;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "usager_id", nullable = false)
    private Usager usager;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "livre_id", nullable = false)
    private Livre livre;

    public Reservation() {
    }

    public Reservation(int id, LocalDateTime dateReservation, LocalDateTime dateRetour, boolean prolongation, Usager usager, Livre livre) {
        this.id = id;
        this.dateReservation = dateReservation;
        this.dateRetour = dateRetour;
        this.prolongation = prolongation;
        this.usager = usager;
        this.livre = livre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(LocalDateTime dateReservation) {
        this.dateReservation = dateReservation;
    }

    public LocalDateTime getDateRetour() {
        return dateRetour;
    }

    public void setDateRetour(LocalDateTime dateRetour) {
        this.dateRetour = dateRetour;
    }

    public boolean isProlongation() {
        return prolongation;
    }

    public void setProlongation(boolean prolongation) {
        this.prolongation = prolongation;
    }

    public Usager getUsager() {
        return usager;
    }

    public void setUsager(Usager usager) {
        this.usager = usager;
    }

    public Livre getLivre() {
        return livre;
    }

    public void setLivre(Livre livre) {
        this.livre = livre;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", dateReservation=" + dateReservation +
                ", dateRetour=" + dateRetour +
                ", prolongation=" + prolongation +
                ", usager=" + usager +
                ", livre=" + livre +
                '}';
    }
}

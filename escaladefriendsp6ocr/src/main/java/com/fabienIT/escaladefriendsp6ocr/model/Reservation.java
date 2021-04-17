package com.fabienIT.escaladefriendsp6ocr.model;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="Reservation")
public class Reservation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean demandeReservation = false;
    private Boolean validerReservation = false;
    private Boolean reserve = false;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "topo_id")
    private Topo topo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id_demande", nullable = true)
    private User user;

    public Reservation() {
    }

    public Reservation(Long id, Boolean demandeReservation, Boolean validerReservation, Boolean reserve, Topo topo, User user) {
        this.id = id;
        this.demandeReservation = demandeReservation;
        this.validerReservation = validerReservation;
        this.reserve = reserve;
        this.topo = topo;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getDemandeReservation() {
        return demandeReservation;
    }

    public void setDemandeReservation(Boolean demandeReservation) {
        this.demandeReservation = demandeReservation;
    }

    public Boolean getValiderReservation() {
        return validerReservation;
    }

    public void setValiderReservation(Boolean validerReservation) {
        this.validerReservation = validerReservation;
    }

    public Boolean getReserve() {
        return reserve;
    }

    public void setReserve(Boolean reserve) {
        this.reserve = reserve;
    }

    public Topo getTopo() {
        return topo;
    }

    public void setTopo(Topo topo) {
        this.topo = topo;
    }

    public User getUseur() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation topo = (Reservation) o;
        return id.equals(topo.id);
    }

/*    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", demandeReservation=" + demandeReservation +
                ", validerReservation=" + validerReservation +
                ", reserve=" + reserve +
                ", topo=" + topo +
                ", user=" + user +
                '}';
    }*/
}
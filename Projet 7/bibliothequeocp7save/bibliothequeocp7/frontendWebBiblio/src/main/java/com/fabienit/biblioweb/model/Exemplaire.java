/*
package com.fabienit.biblioapi.model;

import javax.persistence.*;


@Entity
public class Exemplaire {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "exemplaire_id")
    int id;
    int quantite;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "bibliotheque_id", nullable = false)
    private Bibliotheque bibliotheque;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "livre_id", nullable = false)
    private Livre livre;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "reservation_id", nullable = false)
    private Reservation reservation;

}
*/

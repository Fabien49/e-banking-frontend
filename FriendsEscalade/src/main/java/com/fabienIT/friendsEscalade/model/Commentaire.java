package com.fabienIT.friendsEscalade.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="commentaire")
public class Commentaire {

    @Id
    @Column(name = "commentaire_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
   /* @Temporal(TemporalType.TIMESTAMP)
    private Date dateTime;*/
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime comDate;

    /*private Date date;*/
    private String com;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "site_id", nullable = false)
    private Site site;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Commentaire() {
    }

    public Commentaire(Long id, LocalDateTime comDate, String com, Site site, User user) {
        this.id = id;
        this.comDate = comDate;
        this.com = com;
        this.site = site;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId() {
        this.id = id;
    }

    public LocalDateTime getComDate() {
        return comDate;
    }

    public void setComDate(LocalDateTime comDate) {
        this.comDate = comDate;
    }

    public String getCom() {
        return com;
    }

    public void setCom(String com) {
        this.com = com;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Commentaire commentaire = (Commentaire) o;
        return id.equals(commentaire.id);
    }

    @Override
    public String toString() {
        return "Commentaire{" +
                "id=" + id +
                ", comDate=" + comDate +
                ", com='" + com + '\'' +
                '}';
    }
}

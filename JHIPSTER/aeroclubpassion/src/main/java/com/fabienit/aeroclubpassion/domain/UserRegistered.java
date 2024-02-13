package com.fabienit.aeroclubpassion.domain;

import java.io.Serializable;
import java.time.Duration;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A UserRegistered.
 */
@Entity
@Table(name = "user_registereds")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserRegistered implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Size(max = 80)
    @Column(name = "nom", length = 80)
    private String nom;

    @Size(max = 80)
    @Column(name = "prenom", length = 80)
    private String prenom;

    @Size(max = 10)
    @Column(name = "telephone", length = 10)
    private String telephone;

    @Size(max = 80)
    @Column(name = "mail", length = 80)
    private String mail;

    @Size(max = 150)
    @Column(name = "adresse", length = 150)
    private String adresse;

    @Size(max = 5)
    @Column(name = "code_postal", length = 5)
    private String codePostal;

    @Size(max = 80)
    @Column(name = "commune", length = 80)
    private String commune;

    @Column(name = "heure_vol")
    private Duration heureVol;

    @ManyToOne
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public UserRegistered id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public UserRegistered nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public UserRegistered prenom(String prenom) {
        this.setPrenom(prenom);
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public UserRegistered telephone(String telephone) {
        this.setTelephone(telephone);
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMail() {
        return this.mail;
    }

    public UserRegistered mail(String mail) {
        this.setMail(mail);
        return this;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getAdresse() {
        return this.adresse;
    }

    public UserRegistered adresse(String adresse) {
        this.setAdresse(adresse);
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getCodePostal() {
        return this.codePostal;
    }

    public UserRegistered codePostal(String codePostal) {
        this.setCodePostal(codePostal);
        return this;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getCommune() {
        return this.commune;
    }

    public UserRegistered commune(String commune) {
        this.setCommune(commune);
        return this;
    }

    public void setCommune(String commune) {
        this.commune = commune;
    }

    public Duration getHeureVol() {
        return this.heureVol;
    }

    public UserRegistered heureVol(Duration heureVol) {
        this.setHeureVol(heureVol);
        return this;
    }

    public void setHeureVol(Duration heureVol) {
        this.heureVol = heureVol;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserRegistered user(User user) {
        this.setUser(user);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserRegistered)) {
            return false;
        }
        return id != null && id.equals(((UserRegistered) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserRegistered{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", mail='" + getMail() + "'" +
            ", adresse='" + getAdresse() + "'" +
            ", codePostal='" + getCodePostal() + "'" +
            ", commune='" + getCommune() + "'" +
            ", heureVol='" + getHeureVol() + "'" +
            "}";
    }
}

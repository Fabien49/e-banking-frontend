package com.fabienit.aeroclubpassion.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Aeroclub.
 */
@Entity
@Table(name = "aeroclubs")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Aeroclub implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 80)
    @Column(name = "oaci", length = 80, nullable = false)
    private String oaci;

    @NotNull
    @Size(max = 80)
    @Column(name = "nom", length = 80, nullable = false)
    private String nom;

    @NotNull
    @Size(max = 80)
    @Column(name = "type", length = 80, nullable = false)
    private String type;

    @NotNull
    @Size(max = 80)
    @Column(name = "telephone", length = 80, nullable = false)
    private String telephone;

    @NotNull
    @Size(max = 80)
    @Column(name = "mail", length = 80, nullable = false, unique = true)
    private String mail;

    @NotNull
    @Size(max = 150)
    @Column(name = "adresse", length = 150, nullable = false)
    private String adresse;

    @NotNull
    @Size(max = 80)
    @Column(name = "code_postal", length = 80, nullable = false)
    private String codePostal;

    @NotNull
    @Size(max = 80)
    @Column(name = "commune", length = 80, nullable = false)
    private String commune;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Aeroclub id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOaci() {
        return this.oaci;
    }

    public Aeroclub oaci(String oaci) {
        this.setOaci(oaci);
        return this;
    }

    public void setOaci(String oaci) {
        this.oaci = oaci;
    }

    public String getNom() {
        return this.nom;
    }

    public Aeroclub nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getType() {
        return this.type;
    }

    public Aeroclub type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public Aeroclub telephone(String telephone) {
        this.setTelephone(telephone);
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMail() {
        return this.mail;
    }

    public Aeroclub mail(String mail) {
        this.setMail(mail);
        return this;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getAdresse() {
        return this.adresse;
    }

    public Aeroclub adresse(String adresse) {
        this.setAdresse(adresse);
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getCodePostal() {
        return this.codePostal;
    }

    public Aeroclub codePostal(String codePostal) {
        this.setCodePostal(codePostal);
        return this;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getCommune() {
        return this.commune;
    }

    public Aeroclub commune(String commune) {
        this.setCommune(commune);
        return this;
    }

    public void setCommune(String commune) {
        this.commune = commune;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Aeroclub)) {
            return false;
        }
        return id != null && id.equals(((Aeroclub) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Aeroclub{" +
            "id=" + getId() +
            ", oaci='" + getOaci() + "'" +
            ", nom='" + getNom() + "'" +
            ", type='" + getType() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", mail='" + getMail() + "'" +
            ", adresse='" + getAdresse() + "'" +
            ", codePostal='" + getCodePostal() + "'" +
            ", commune='" + getCommune() + "'" +
            "}";
    }
}

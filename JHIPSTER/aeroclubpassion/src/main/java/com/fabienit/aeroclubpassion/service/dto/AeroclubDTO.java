package com.fabienit.aeroclubpassion.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.fabienit.aeroclubpassion.domain.Aeroclub} entity.
 */
public class AeroclubDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 80)
    private String oaci;

    @NotNull
    @Size(max = 80)
    private String nom;

    @NotNull
    @Size(max = 80)
    private String type;

    @NotNull
    @Size(max = 80)
    private String telephone;

    @NotNull
    @Size(max = 80)
    private String mail;

    @NotNull
    @Size(max = 150)
    private String adresse;

    @NotNull
    @Size(max = 80)
    private String codePostal;

    @NotNull
    @Size(max = 80)
    private String commune;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOaci() {
        return oaci;
    }

    public void setOaci(String oaci) {
        this.oaci = oaci;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getCommune() {
        return commune;
    }

    public void setCommune(String commune) {
        this.commune = commune;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AeroclubDTO)) {
            return false;
        }

        AeroclubDTO aeroclubDTO = (AeroclubDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, aeroclubDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AeroclubDTO{" +
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

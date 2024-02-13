package com.fabienit.aeroclubpassion.service.dto;

import java.io.Serializable;
import java.time.Duration;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.fabienit.aeroclubpassion.domain.UserRegistered} entity.
 */
public class UserRegisteredDTO implements Serializable {

    private Long id;

    @Size(max = 80)
    private String nom;

    @Size(max = 80)
    private String prenom;

    @Size(max = 10)
    private String telephone;

    @Size(max = 80)
    private String mail;

    @Size(max = 150)
    private String adresse;

    @Size(max = 5)
    private String codePostal;

    @Size(max = 80)
    private String commune;

    private Duration heureVol;

    private UserDTO user;

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

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
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

    public Duration getHeureVol() {
        return heureVol;
    }

    public void setHeureVol(Duration heureVol) {
        this.heureVol = heureVol;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserRegisteredDTO)) {
            return false;
        }

        UserRegisteredDTO userRegisteredDTO = (UserRegisteredDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, userRegisteredDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserRegisteredDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", mail='" + getMail() + "'" +
            ", adresse='" + getAdresse() + "'" +
            ", codePostal='" + getCodePostal() + "'" +
            ", commune='" + getCommune() + "'" +
            ", heureVol='" + getHeureVol() + "'" +
            ", user=" + getUser() +
            "}";
    }
}

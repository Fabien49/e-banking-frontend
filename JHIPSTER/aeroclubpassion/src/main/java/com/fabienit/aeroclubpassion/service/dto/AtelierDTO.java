package com.fabienit.aeroclubpassion.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.fabienit.aeroclubpassion.domain.Atelier} entity.
 */
public class AtelierDTO implements Serializable {

    private Long id;

    private Integer compteurChgtMoteur;

    private Integer compteurCarrosserie;

    private Integer compteurHelisse;

    private ZonedDateTime date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCompteurChgtMoteur() {
        return compteurChgtMoteur;
    }

    public void setCompteurChgtMoteur(Integer compteurChgtMoteur) {
        this.compteurChgtMoteur = compteurChgtMoteur;
    }

    public Integer getCompteurCarrosserie() {
        return compteurCarrosserie;
    }

    public void setCompteurCarrosserie(Integer compteurCarrosserie) {
        this.compteurCarrosserie = compteurCarrosserie;
    }

    public Integer getCompteurHelisse() {
        return compteurHelisse;
    }

    public void setCompteurHelisse(Integer compteurHelisse) {
        this.compteurHelisse = compteurHelisse;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AtelierDTO)) {
            return false;
        }

        AtelierDTO atelierDTO = (AtelierDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, atelierDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AtelierDTO{" +
            "id=" + getId() +
            ", compteurChgtMoteur=" + getCompteurChgtMoteur() +
            ", compteurCarrosserie=" + getCompteurCarrosserie() +
            ", compteurHelisse=" + getCompteurHelisse() +
            ", date='" + getDate() + "'" +
            "}";
    }
}

package com.fabienit.aeroclubpassion.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.fabienit.aeroclubpassion.domain.Revision} entity.
 */
public class RevisionDTO implements Serializable {

    private Long id;

    private Boolean niveaux;

    private Boolean pression;

    private Boolean carroserie;

    private ZonedDateTime date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getNiveaux() {
        return niveaux;
    }

    public void setNiveaux(Boolean niveaux) {
        this.niveaux = niveaux;
    }

    public Boolean getPression() {
        return pression;
    }

    public void setPression(Boolean pression) {
        this.pression = pression;
    }

    public Boolean getCarroserie() {
        return carroserie;
    }

    public void setCarroserie(Boolean carroserie) {
        this.carroserie = carroserie;
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
        if (!(o instanceof RevisionDTO)) {
            return false;
        }

        RevisionDTO revisionDTO = (RevisionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, revisionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RevisionDTO{" +
            "id=" + getId() +
            ", niveaux='" + getNiveaux() + "'" +
            ", pression='" + getPression() + "'" +
            ", carroserie='" + getCarroserie() + "'" +
            ", date='" + getDate() + "'" +
            "}";
    }
}

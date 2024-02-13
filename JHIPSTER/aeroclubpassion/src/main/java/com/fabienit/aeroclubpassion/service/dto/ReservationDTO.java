package com.fabienit.aeroclubpassion.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.fabienit.aeroclubpassion.domain.Reservation} entity.
 */
public class ReservationDTO implements Serializable {

    private Long id;

    private ZonedDateTime dateEmprunt;

    private ZonedDateTime dateRetour;

    private AvionDTO avions;

    private UserRegisteredDTO userRegistereds;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDateEmprunt() {
        return dateEmprunt;
    }

    public void setDateEmprunt(ZonedDateTime dateEmprunt) {
        this.dateEmprunt = dateEmprunt;
    }

    public ZonedDateTime getDateRetour() {
        return dateRetour;
    }

    public void setDateRetour(ZonedDateTime dateRetour) {
        this.dateRetour = dateRetour;
    }

    public AvionDTO getAvions() {
        return avions;
    }

    public void setAvions(AvionDTO avions) {
        this.avions = avions;
    }

    public UserRegisteredDTO getUserRegistereds() {
        return userRegistereds;
    }

    public void setUserRegistereds(UserRegisteredDTO userRegistereds) {
        this.userRegistereds = userRegistereds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReservationDTO)) {
            return false;
        }

        ReservationDTO reservationDTO = (ReservationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, reservationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReservationDTO{" +
            "id=" + getId() +
            ", dateEmprunt='" + getDateEmprunt() + "'" +
            ", dateRetour='" + getDateRetour() + "'" +
            ", avions=" + getAvions() +
            ", userRegistereds=" + getUserRegistereds() +
            "}";
    }
}

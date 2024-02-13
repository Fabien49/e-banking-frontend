package com.fabienit.aeroclubpassion.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.fabienit.aeroclubpassion.domain.Tarif} entity.
 */
public class TarifDTO implements Serializable {

    private Long id;

    private Double taxeAtterrissage;

    private Double taxeParking;

    private Double carburant;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTaxeAtterrissage() {
        return taxeAtterrissage;
    }

    public void setTaxeAtterrissage(Double taxeAtterrissage) {
        this.taxeAtterrissage = taxeAtterrissage;
    }

    public Double getTaxeParking() {
        return taxeParking;
    }

    public void setTaxeParking(Double taxeParking) {
        this.taxeParking = taxeParking;
    }

    public Double getCarburant() {
        return carburant;
    }

    public void setCarburant(Double carburant) {
        this.carburant = carburant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TarifDTO)) {
            return false;
        }

        TarifDTO tarifDTO = (TarifDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, tarifDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TarifDTO{" +
            "id=" + getId() +
            ", taxeAtterrissage=" + getTaxeAtterrissage() +
            ", taxeParking=" + getTaxeParking() +
            ", carburant=" + getCarburant() +
            "}";
    }
}

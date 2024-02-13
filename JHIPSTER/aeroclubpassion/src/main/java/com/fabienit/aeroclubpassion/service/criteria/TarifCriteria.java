package com.fabienit.aeroclubpassion.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.fabienit.aeroclubpassion.domain.Tarif} entity. This class is used
 * in {@link com.fabienit.aeroclubpassion.web.rest.TarifResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /tarifs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TarifCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private DoubleFilter taxeAtterrissage;

    private DoubleFilter taxeParking;

    private DoubleFilter carburant;

    private Boolean distinct;

    public TarifCriteria() {}

    public TarifCriteria(TarifCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.taxeAtterrissage = other.taxeAtterrissage == null ? null : other.taxeAtterrissage.copy();
        this.taxeParking = other.taxeParking == null ? null : other.taxeParking.copy();
        this.carburant = other.carburant == null ? null : other.carburant.copy();
        this.distinct = other.distinct;
    }

    @Override
    public TarifCriteria copy() {
        return new TarifCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public DoubleFilter getTaxeAtterrissage() {
        return taxeAtterrissage;
    }

    public DoubleFilter taxeAtterrissage() {
        if (taxeAtterrissage == null) {
            taxeAtterrissage = new DoubleFilter();
        }
        return taxeAtterrissage;
    }

    public void setTaxeAtterrissage(DoubleFilter taxeAtterrissage) {
        this.taxeAtterrissage = taxeAtterrissage;
    }

    public DoubleFilter getTaxeParking() {
        return taxeParking;
    }

    public DoubleFilter taxeParking() {
        if (taxeParking == null) {
            taxeParking = new DoubleFilter();
        }
        return taxeParking;
    }

    public void setTaxeParking(DoubleFilter taxeParking) {
        this.taxeParking = taxeParking;
    }

    public DoubleFilter getCarburant() {
        return carburant;
    }

    public DoubleFilter carburant() {
        if (carburant == null) {
            carburant = new DoubleFilter();
        }
        return carburant;
    }

    public void setCarburant(DoubleFilter carburant) {
        this.carburant = carburant;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TarifCriteria that = (TarifCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(taxeAtterrissage, that.taxeAtterrissage) &&
            Objects.equals(taxeParking, that.taxeParking) &&
            Objects.equals(carburant, that.carburant) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, taxeAtterrissage, taxeParking, carburant, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TarifCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (taxeAtterrissage != null ? "taxeAtterrissage=" + taxeAtterrissage + ", " : "") +
            (taxeParking != null ? "taxeParking=" + taxeParking + ", " : "") +
            (carburant != null ? "carburant=" + carburant + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}

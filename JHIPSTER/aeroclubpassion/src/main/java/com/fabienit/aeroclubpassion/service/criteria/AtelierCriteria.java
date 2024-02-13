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
import tech.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link com.fabienit.aeroclubpassion.domain.Atelier} entity. This class is used
 * in {@link com.fabienit.aeroclubpassion.web.rest.AtelierResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /ateliers?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AtelierCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter compteurChgtMoteur;

    private IntegerFilter compteurCarrosserie;

    private IntegerFilter compteurHelisse;

    private ZonedDateTimeFilter date;

    private LongFilter avionsId;

    private Boolean distinct;

    public AtelierCriteria() {}

    public AtelierCriteria(AtelierCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.compteurChgtMoteur = other.compteurChgtMoteur == null ? null : other.compteurChgtMoteur.copy();
        this.compteurCarrosserie = other.compteurCarrosserie == null ? null : other.compteurCarrosserie.copy();
        this.compteurHelisse = other.compteurHelisse == null ? null : other.compteurHelisse.copy();
        this.date = other.date == null ? null : other.date.copy();
        this.avionsId = other.avionsId == null ? null : other.avionsId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public AtelierCriteria copy() {
        return new AtelierCriteria(this);
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

    public IntegerFilter getCompteurChgtMoteur() {
        return compteurChgtMoteur;
    }

    public IntegerFilter compteurChgtMoteur() {
        if (compteurChgtMoteur == null) {
            compteurChgtMoteur = new IntegerFilter();
        }
        return compteurChgtMoteur;
    }

    public void setCompteurChgtMoteur(IntegerFilter compteurChgtMoteur) {
        this.compteurChgtMoteur = compteurChgtMoteur;
    }

    public IntegerFilter getCompteurCarrosserie() {
        return compteurCarrosserie;
    }

    public IntegerFilter compteurCarrosserie() {
        if (compteurCarrosserie == null) {
            compteurCarrosserie = new IntegerFilter();
        }
        return compteurCarrosserie;
    }

    public void setCompteurCarrosserie(IntegerFilter compteurCarrosserie) {
        this.compteurCarrosserie = compteurCarrosserie;
    }

    public IntegerFilter getCompteurHelisse() {
        return compteurHelisse;
    }

    public IntegerFilter compteurHelisse() {
        if (compteurHelisse == null) {
            compteurHelisse = new IntegerFilter();
        }
        return compteurHelisse;
    }

    public void setCompteurHelisse(IntegerFilter compteurHelisse) {
        this.compteurHelisse = compteurHelisse;
    }

    public ZonedDateTimeFilter getDate() {
        return date;
    }

    public ZonedDateTimeFilter date() {
        if (date == null) {
            date = new ZonedDateTimeFilter();
        }
        return date;
    }

    public void setDate(ZonedDateTimeFilter date) {
        this.date = date;
    }

    public LongFilter getAvionsId() {
        return avionsId;
    }

    public LongFilter avionsId() {
        if (avionsId == null) {
            avionsId = new LongFilter();
        }
        return avionsId;
    }

    public void setAvionsId(LongFilter avionsId) {
        this.avionsId = avionsId;
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
        final AtelierCriteria that = (AtelierCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(compteurChgtMoteur, that.compteurChgtMoteur) &&
            Objects.equals(compteurCarrosserie, that.compteurCarrosserie) &&
            Objects.equals(compteurHelisse, that.compteurHelisse) &&
            Objects.equals(date, that.date) &&
            Objects.equals(avionsId, that.avionsId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, compteurChgtMoteur, compteurCarrosserie, compteurHelisse, date, avionsId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AtelierCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (compteurChgtMoteur != null ? "compteurChgtMoteur=" + compteurChgtMoteur + ", " : "") +
            (compteurCarrosserie != null ? "compteurCarrosserie=" + compteurCarrosserie + ", " : "") +
            (compteurHelisse != null ? "compteurHelisse=" + compteurHelisse + ", " : "") +
            (date != null ? "date=" + date + ", " : "") +
            (avionsId != null ? "avionsId=" + avionsId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}

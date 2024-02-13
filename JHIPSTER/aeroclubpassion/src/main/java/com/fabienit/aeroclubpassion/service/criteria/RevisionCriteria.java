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
 * Criteria class for the {@link com.fabienit.aeroclubpassion.domain.Revision} entity. This class is used
 * in {@link com.fabienit.aeroclubpassion.web.rest.RevisionResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /revisions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class RevisionCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BooleanFilter niveaux;

    private BooleanFilter pression;

    private BooleanFilter carroserie;

    private ZonedDateTimeFilter date;

    private LongFilter avionsId;

    private Boolean distinct;

    public RevisionCriteria() {}

    public RevisionCriteria(RevisionCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.niveaux = other.niveaux == null ? null : other.niveaux.copy();
        this.pression = other.pression == null ? null : other.pression.copy();
        this.carroserie = other.carroserie == null ? null : other.carroserie.copy();
        this.date = other.date == null ? null : other.date.copy();
        this.avionsId = other.avionsId == null ? null : other.avionsId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public RevisionCriteria copy() {
        return new RevisionCriteria(this);
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

    public BooleanFilter getNiveaux() {
        return niveaux;
    }

    public BooleanFilter niveaux() {
        if (niveaux == null) {
            niveaux = new BooleanFilter();
        }
        return niveaux;
    }

    public void setNiveaux(BooleanFilter niveaux) {
        this.niveaux = niveaux;
    }

    public BooleanFilter getPression() {
        return pression;
    }

    public BooleanFilter pression() {
        if (pression == null) {
            pression = new BooleanFilter();
        }
        return pression;
    }

    public void setPression(BooleanFilter pression) {
        this.pression = pression;
    }

    public BooleanFilter getCarroserie() {
        return carroserie;
    }

    public BooleanFilter carroserie() {
        if (carroserie == null) {
            carroserie = new BooleanFilter();
        }
        return carroserie;
    }

    public void setCarroserie(BooleanFilter carroserie) {
        this.carroserie = carroserie;
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
        final RevisionCriteria that = (RevisionCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(niveaux, that.niveaux) &&
            Objects.equals(pression, that.pression) &&
            Objects.equals(carroserie, that.carroserie) &&
            Objects.equals(date, that.date) &&
            Objects.equals(avionsId, that.avionsId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, niveaux, pression, carroserie, date, avionsId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RevisionCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (niveaux != null ? "niveaux=" + niveaux + ", " : "") +
            (pression != null ? "pression=" + pression + ", " : "") +
            (carroserie != null ? "carroserie=" + carroserie + ", " : "") +
            (date != null ? "date=" + date + ", " : "") +
            (avionsId != null ? "avionsId=" + avionsId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}

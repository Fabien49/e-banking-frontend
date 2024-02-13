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
 * Criteria class for the {@link com.fabienit.aeroclubpassion.domain.Reservation} entity. This class is used
 * in {@link com.fabienit.aeroclubpassion.web.rest.ReservationResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /reservations?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ReservationCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private ZonedDateTimeFilter dateEmprunt;

    private ZonedDateTimeFilter dateRetour;

    private LongFilter avionsId;

    private LongFilter userRegisteredsId;

    private Boolean distinct;

    public ReservationCriteria() {}

    public ReservationCriteria(ReservationCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.dateEmprunt = other.dateEmprunt == null ? null : other.dateEmprunt.copy();
        this.dateRetour = other.dateRetour == null ? null : other.dateRetour.copy();
        this.avionsId = other.avionsId == null ? null : other.avionsId.copy();
        this.userRegisteredsId = other.userRegisteredsId == null ? null : other.userRegisteredsId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ReservationCriteria copy() {
        return new ReservationCriteria(this);
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

    public ZonedDateTimeFilter getDateEmprunt() {
        return dateEmprunt;
    }

    public ZonedDateTimeFilter dateEmprunt() {
        if (dateEmprunt == null) {
            dateEmprunt = new ZonedDateTimeFilter();
        }
        return dateEmprunt;
    }

    public void setDateEmprunt(ZonedDateTimeFilter dateEmprunt) {
        this.dateEmprunt = dateEmprunt;
    }

    public ZonedDateTimeFilter getDateRetour() {
        return dateRetour;
    }

    public ZonedDateTimeFilter dateRetour() {
        if (dateRetour == null) {
            dateRetour = new ZonedDateTimeFilter();
        }
        return dateRetour;
    }

    public void setDateRetour(ZonedDateTimeFilter dateRetour) {
        this.dateRetour = dateRetour;
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

    public LongFilter getUserRegisteredsId() {
        return userRegisteredsId;
    }

    public LongFilter userRegisteredsId() {
        if (userRegisteredsId == null) {
            userRegisteredsId = new LongFilter();
        }
        return userRegisteredsId;
    }

    public void setUserRegisteredsId(LongFilter userRegisteredsId) {
        this.userRegisteredsId = userRegisteredsId;
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
        final ReservationCriteria that = (ReservationCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(dateEmprunt, that.dateEmprunt) &&
            Objects.equals(dateRetour, that.dateRetour) &&
            Objects.equals(avionsId, that.avionsId) &&
            Objects.equals(userRegisteredsId, that.userRegisteredsId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dateEmprunt, dateRetour, avionsId, userRegisteredsId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReservationCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (dateEmprunt != null ? "dateEmprunt=" + dateEmprunt + ", " : "") +
            (dateRetour != null ? "dateRetour=" + dateRetour + ", " : "") +
            (avionsId != null ? "avionsId=" + avionsId + ", " : "") +
            (userRegisteredsId != null ? "userRegisteredsId=" + userRegisteredsId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}

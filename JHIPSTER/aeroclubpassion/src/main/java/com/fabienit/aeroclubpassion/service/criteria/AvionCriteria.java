package com.fabienit.aeroclubpassion.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.DurationFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.fabienit.aeroclubpassion.domain.Avion} entity. This class is used
 * in {@link com.fabienit.aeroclubpassion.web.rest.AvionResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /avions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AvionCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter marque;

    private StringFilter type;

    private StringFilter moteur;

    private IntegerFilter puissance;

    private IntegerFilter place;

    private DurationFilter autonomie;

    private StringFilter usage;

    private DurationFilter heures;

    private LongFilter atelierId;

    private LongFilter revisionId;

    private Boolean distinct;

    public AvionCriteria() {}

    public AvionCriteria(AvionCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.marque = other.marque == null ? null : other.marque.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.moteur = other.moteur == null ? null : other.moteur.copy();
        this.puissance = other.puissance == null ? null : other.puissance.copy();
        this.place = other.place == null ? null : other.place.copy();
        this.autonomie = other.autonomie == null ? null : other.autonomie.copy();
        this.usage = other.usage == null ? null : other.usage.copy();
        this.heures = other.heures == null ? null : other.heures.copy();
        this.atelierId = other.atelierId == null ? null : other.atelierId.copy();
        this.revisionId = other.revisionId == null ? null : other.revisionId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public AvionCriteria copy() {
        return new AvionCriteria(this);
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

    public StringFilter getMarque() {
        return marque;
    }

    public StringFilter marque() {
        if (marque == null) {
            marque = new StringFilter();
        }
        return marque;
    }

    public void setMarque(StringFilter marque) {
        this.marque = marque;
    }

    public StringFilter getType() {
        return type;
    }

    public StringFilter type() {
        if (type == null) {
            type = new StringFilter();
        }
        return type;
    }

    public void setType(StringFilter type) {
        this.type = type;
    }

    public StringFilter getMoteur() {
        return moteur;
    }

    public StringFilter moteur() {
        if (moteur == null) {
            moteur = new StringFilter();
        }
        return moteur;
    }

    public void setMoteur(StringFilter moteur) {
        this.moteur = moteur;
    }

    public IntegerFilter getPuissance() {
        return puissance;
    }

    public IntegerFilter puissance() {
        if (puissance == null) {
            puissance = new IntegerFilter();
        }
        return puissance;
    }

    public void setPuissance(IntegerFilter puissance) {
        this.puissance = puissance;
    }

    public IntegerFilter getPlace() {
        return place;
    }

    public IntegerFilter place() {
        if (place == null) {
            place = new IntegerFilter();
        }
        return place;
    }

    public void setPlace(IntegerFilter place) {
        this.place = place;
    }

    public DurationFilter getAutonomie() {
        return autonomie;
    }

    public DurationFilter autonomie() {
        if (autonomie == null) {
            autonomie = new DurationFilter();
        }
        return autonomie;
    }

    public void setAutonomie(DurationFilter autonomie) {
        this.autonomie = autonomie;
    }

    public StringFilter getUsage() {
        return usage;
    }

    public StringFilter usage() {
        if (usage == null) {
            usage = new StringFilter();
        }
        return usage;
    }

    public void setUsage(StringFilter usage) {
        this.usage = usage;
    }

    public DurationFilter getHeures() {
        return heures;
    }

    public DurationFilter heures() {
        if (heures == null) {
            heures = new DurationFilter();
        }
        return heures;
    }

    public void setHeures(DurationFilter heures) {
        this.heures = heures;
    }

    public LongFilter getAtelierId() {
        return atelierId;
    }

    public LongFilter atelierId() {
        if (atelierId == null) {
            atelierId = new LongFilter();
        }
        return atelierId;
    }

    public void setAtelierId(LongFilter atelierId) {
        this.atelierId = atelierId;
    }

    public LongFilter getRevisionId() {
        return revisionId;
    }

    public LongFilter revisionId() {
        if (revisionId == null) {
            revisionId = new LongFilter();
        }
        return revisionId;
    }

    public void setRevisionId(LongFilter revisionId) {
        this.revisionId = revisionId;
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
        final AvionCriteria that = (AvionCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(marque, that.marque) &&
            Objects.equals(type, that.type) &&
            Objects.equals(moteur, that.moteur) &&
            Objects.equals(puissance, that.puissance) &&
            Objects.equals(place, that.place) &&
            Objects.equals(autonomie, that.autonomie) &&
            Objects.equals(usage, that.usage) &&
            Objects.equals(heures, that.heures) &&
            Objects.equals(atelierId, that.atelierId) &&
            Objects.equals(revisionId, that.revisionId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, marque, type, moteur, puissance, place, autonomie, usage, heures, atelierId, revisionId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AvionCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (marque != null ? "marque=" + marque + ", " : "") +
            (type != null ? "type=" + type + ", " : "") +
            (moteur != null ? "moteur=" + moteur + ", " : "") +
            (puissance != null ? "puissance=" + puissance + ", " : "") +
            (place != null ? "place=" + place + ", " : "") +
            (autonomie != null ? "autonomie=" + autonomie + ", " : "") +
            (usage != null ? "usage=" + usage + ", " : "") +
            (heures != null ? "heures=" + heures + ", " : "") +
            (atelierId != null ? "atelierId=" + atelierId + ", " : "") +
            (revisionId != null ? "revisionId=" + revisionId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}

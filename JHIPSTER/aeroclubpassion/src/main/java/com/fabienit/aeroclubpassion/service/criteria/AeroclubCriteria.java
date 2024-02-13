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
 * Criteria class for the {@link com.fabienit.aeroclubpassion.domain.Aeroclub} entity. This class is used
 * in {@link com.fabienit.aeroclubpassion.web.rest.AeroclubResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /aeroclubs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AeroclubCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter oaci;

    private StringFilter nom;

    private StringFilter type;

    private StringFilter telephone;

    private StringFilter mail;

    private StringFilter adresse;

    private StringFilter codePostal;

    private StringFilter commune;

    private Boolean distinct;

    public AeroclubCriteria() {}

    public AeroclubCriteria(AeroclubCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.oaci = other.oaci == null ? null : other.oaci.copy();
        this.nom = other.nom == null ? null : other.nom.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.telephone = other.telephone == null ? null : other.telephone.copy();
        this.mail = other.mail == null ? null : other.mail.copy();
        this.adresse = other.adresse == null ? null : other.adresse.copy();
        this.codePostal = other.codePostal == null ? null : other.codePostal.copy();
        this.commune = other.commune == null ? null : other.commune.copy();
        this.distinct = other.distinct;
    }

    @Override
    public AeroclubCriteria copy() {
        return new AeroclubCriteria(this);
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

    public StringFilter getOaci() {
        return oaci;
    }

    public StringFilter oaci() {
        if (oaci == null) {
            oaci = new StringFilter();
        }
        return oaci;
    }

    public void setOaci(StringFilter oaci) {
        this.oaci = oaci;
    }

    public StringFilter getNom() {
        return nom;
    }

    public StringFilter nom() {
        if (nom == null) {
            nom = new StringFilter();
        }
        return nom;
    }

    public void setNom(StringFilter nom) {
        this.nom = nom;
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

    public StringFilter getTelephone() {
        return telephone;
    }

    public StringFilter telephone() {
        if (telephone == null) {
            telephone = new StringFilter();
        }
        return telephone;
    }

    public void setTelephone(StringFilter telephone) {
        this.telephone = telephone;
    }

    public StringFilter getMail() {
        return mail;
    }

    public StringFilter mail() {
        if (mail == null) {
            mail = new StringFilter();
        }
        return mail;
    }

    public void setMail(StringFilter mail) {
        this.mail = mail;
    }

    public StringFilter getAdresse() {
        return adresse;
    }

    public StringFilter adresse() {
        if (adresse == null) {
            adresse = new StringFilter();
        }
        return adresse;
    }

    public void setAdresse(StringFilter adresse) {
        this.adresse = adresse;
    }

    public StringFilter getCodePostal() {
        return codePostal;
    }

    public StringFilter codePostal() {
        if (codePostal == null) {
            codePostal = new StringFilter();
        }
        return codePostal;
    }

    public void setCodePostal(StringFilter codePostal) {
        this.codePostal = codePostal;
    }

    public StringFilter getCommune() {
        return commune;
    }

    public StringFilter commune() {
        if (commune == null) {
            commune = new StringFilter();
        }
        return commune;
    }

    public void setCommune(StringFilter commune) {
        this.commune = commune;
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
        final AeroclubCriteria that = (AeroclubCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(oaci, that.oaci) &&
            Objects.equals(nom, that.nom) &&
            Objects.equals(type, that.type) &&
            Objects.equals(telephone, that.telephone) &&
            Objects.equals(mail, that.mail) &&
            Objects.equals(adresse, that.adresse) &&
            Objects.equals(codePostal, that.codePostal) &&
            Objects.equals(commune, that.commune) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, oaci, nom, type, telephone, mail, adresse, codePostal, commune, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AeroclubCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (oaci != null ? "oaci=" + oaci + ", " : "") +
            (nom != null ? "nom=" + nom + ", " : "") +
            (type != null ? "type=" + type + ", " : "") +
            (telephone != null ? "telephone=" + telephone + ", " : "") +
            (mail != null ? "mail=" + mail + ", " : "") +
            (adresse != null ? "adresse=" + adresse + ", " : "") +
            (codePostal != null ? "codePostal=" + codePostal + ", " : "") +
            (commune != null ? "commune=" + commune + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}

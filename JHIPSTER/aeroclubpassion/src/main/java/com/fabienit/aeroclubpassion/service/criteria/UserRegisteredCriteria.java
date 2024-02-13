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
 * Criteria class for the {@link com.fabienit.aeroclubpassion.domain.UserRegistered} entity. This class is used
 * in {@link com.fabienit.aeroclubpassion.web.rest.UserRegisteredResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /user-registereds?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class UserRegisteredCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nom;

    private StringFilter prenom;

    private StringFilter telephone;

    private StringFilter mail;

    private StringFilter adresse;

    private StringFilter codePostal;

    private StringFilter commune;

    private DurationFilter heureVol;

    private LongFilter userId;

    private Boolean distinct;

    public UserRegisteredCriteria() {}

    public UserRegisteredCriteria(UserRegisteredCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nom = other.nom == null ? null : other.nom.copy();
        this.prenom = other.prenom == null ? null : other.prenom.copy();
        this.telephone = other.telephone == null ? null : other.telephone.copy();
        this.mail = other.mail == null ? null : other.mail.copy();
        this.adresse = other.adresse == null ? null : other.adresse.copy();
        this.codePostal = other.codePostal == null ? null : other.codePostal.copy();
        this.commune = other.commune == null ? null : other.commune.copy();
        this.heureVol = other.heureVol == null ? null : other.heureVol.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public UserRegisteredCriteria copy() {
        return new UserRegisteredCriteria(this);
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

    public StringFilter getPrenom() {
        return prenom;
    }

    public StringFilter prenom() {
        if (prenom == null) {
            prenom = new StringFilter();
        }
        return prenom;
    }

    public void setPrenom(StringFilter prenom) {
        this.prenom = prenom;
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

    public DurationFilter getHeureVol() {
        return heureVol;
    }

    public DurationFilter heureVol() {
        if (heureVol == null) {
            heureVol = new DurationFilter();
        }
        return heureVol;
    }

    public void setHeureVol(DurationFilter heureVol) {
        this.heureVol = heureVol;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public LongFilter userId() {
        if (userId == null) {
            userId = new LongFilter();
        }
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
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
        final UserRegisteredCriteria that = (UserRegisteredCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(nom, that.nom) &&
            Objects.equals(prenom, that.prenom) &&
            Objects.equals(telephone, that.telephone) &&
            Objects.equals(mail, that.mail) &&
            Objects.equals(adresse, that.adresse) &&
            Objects.equals(codePostal, that.codePostal) &&
            Objects.equals(commune, that.commune) &&
            Objects.equals(heureVol, that.heureVol) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nom, prenom, telephone, mail, adresse, codePostal, commune, heureVol, userId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserRegisteredCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (nom != null ? "nom=" + nom + ", " : "") +
            (prenom != null ? "prenom=" + prenom + ", " : "") +
            (telephone != null ? "telephone=" + telephone + ", " : "") +
            (mail != null ? "mail=" + mail + ", " : "") +
            (adresse != null ? "adresse=" + adresse + ", " : "") +
            (codePostal != null ? "codePostal=" + codePostal + ", " : "") +
            (commune != null ? "commune=" + commune + ", " : "") +
            (heureVol != null ? "heureVol=" + heureVol + ", " : "") +
            (userId != null ? "userId=" + userId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}

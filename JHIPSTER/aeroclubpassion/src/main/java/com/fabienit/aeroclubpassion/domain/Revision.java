package com.fabienit.aeroclubpassion.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Revision.
 */
@Entity
@Table(name = "revisions")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Revision implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "niveaux")
    private Boolean niveaux;

    @Column(name = "pression")
    private Boolean pression;

    @Column(name = "carroserie")
    private Boolean carroserie;

    @Column(name = "date")
    private ZonedDateTime date;

    @OneToMany(mappedBy = "revision")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "atelier", "revision" }, allowSetters = true)
    private Set<Avion> avions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Revision id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getNiveaux() {
        return this.niveaux;
    }

    public Revision niveaux(Boolean niveaux) {
        this.setNiveaux(niveaux);
        return this;
    }

    public void setNiveaux(Boolean niveaux) {
        this.niveaux = niveaux;
    }

    public Boolean getPression() {
        return this.pression;
    }

    public Revision pression(Boolean pression) {
        this.setPression(pression);
        return this;
    }

    public void setPression(Boolean pression) {
        this.pression = pression;
    }

    public Boolean getCarroserie() {
        return this.carroserie;
    }

    public Revision carroserie(Boolean carroserie) {
        this.setCarroserie(carroserie);
        return this;
    }

    public void setCarroserie(Boolean carroserie) {
        this.carroserie = carroserie;
    }

    public ZonedDateTime getDate() {
        return this.date;
    }

    public Revision date(ZonedDateTime date) {
        this.setDate(date);
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public Set<Avion> getAvions() {
        return this.avions;
    }

    public void setAvions(Set<Avion> avions) {
        if (this.avions != null) {
            this.avions.forEach(i -> i.setRevision(null));
        }
        if (avions != null) {
            avions.forEach(i -> i.setRevision(this));
        }
        this.avions = avions;
    }

    public Revision avions(Set<Avion> avions) {
        this.setAvions(avions);
        return this;
    }

    public Revision addAvions(Avion avion) {
        this.avions.add(avion);
        avion.setRevision(this);
        return this;
    }

    public Revision removeAvions(Avion avion) {
        this.avions.remove(avion);
        avion.setRevision(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Revision)) {
            return false;
        }
        return id != null && id.equals(((Revision) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Revision{" +
            "id=" + getId() +
            ", niveaux='" + getNiveaux() + "'" +
            ", pression='" + getPression() + "'" +
            ", carroserie='" + getCarroserie() + "'" +
            ", date='" + getDate() + "'" +
            "}";
    }
}

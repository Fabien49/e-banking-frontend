package com.fabienit.rechercher.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * The User entity.
 */
@ApiModel(description = "The User entity.")
@Entity
@Table(name = "pilote")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Pilote implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * The firstname attribute.
     */
    @ApiModelProperty(value = "The firstname attribute.")
    @Column(name = "name")
    private String name;

    @Column(name = "email_id")
    private String emailId;

    @Column(name = "qualification")
    private String qualification;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Pilote id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Pilote name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailId() {
        return this.emailId;
    }

    public Pilote emailId(String emailId) {
        this.setEmailId(emailId);
        return this;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getQualification() {
        return this.qualification;
    }

    public Pilote qualification(String qualification) {
        this.setQualification(qualification);
        return this;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pilote)) {
            return false;
        }
        return id != null && id.equals(((Pilote) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Pilote{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", emailId='" + getEmailId() + "'" +
            ", qualification='" + getQualification() + "'" +
            "}";
    }
}

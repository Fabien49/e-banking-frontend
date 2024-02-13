package com.fabienit.aeroclubpassion.service.dto;

import java.io.Serializable;
import java.time.Duration;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.fabienit.aeroclubpassion.domain.Avion} entity.
 */
public class AvionDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 80)
    private String marque;

    private String type;

    private String moteur;

    private Integer puissance;

    @NotNull
    private Integer place;

    private Duration autonomie;

    private String usage;

    @NotNull
    private Duration heures;

    @Lob
    private byte[] image;

    private String imageContentType;
    private AtelierDTO atelier;

    private RevisionDTO revision;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMoteur() {
        return moteur;
    }

    public void setMoteur(String moteur) {
        this.moteur = moteur;
    }

    public Integer getPuissance() {
        return puissance;
    }

    public void setPuissance(Integer puissance) {
        this.puissance = puissance;
    }

    public Integer getPlace() {
        return place;
    }

    public void setPlace(Integer place) {
        this.place = place;
    }

    public Duration getAutonomie() {
        return autonomie;
    }

    public void setAutonomie(Duration autonomie) {
        this.autonomie = autonomie;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public Duration getHeures() {
        return heures;
    }

    public void setHeures(Duration heures) {
        this.heures = heures;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public AtelierDTO getAtelier() {
        return atelier;
    }

    public void setAtelier(AtelierDTO atelier) {
        this.atelier = atelier;
    }

    public RevisionDTO getRevision() {
        return revision;
    }

    public void setRevision(RevisionDTO revision) {
        this.revision = revision;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AvionDTO)) {
            return false;
        }

        AvionDTO avionDTO = (AvionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, avionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AvionDTO{" +
            "id=" + getId() +
            ", marque='" + getMarque() + "'" +
            ", type='" + getType() + "'" +
            ", moteur='" + getMoteur() + "'" +
            ", puissance=" + getPuissance() +
            ", place=" + getPlace() +
            ", autonomie='" + getAutonomie() + "'" +
            ", usage='" + getUsage() + "'" +
            ", heures='" + getHeures() + "'" +
            ", image='" + getImage() + "'" +
            ", atelier=" + getAtelier() +
            ", revision=" + getRevision() +
            "}";
    }
}

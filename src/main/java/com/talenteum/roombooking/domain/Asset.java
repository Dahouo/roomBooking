package com.talenteum.roombooking.domain;



import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Asset.
 */
@Entity
@Table(name = "asset")
public class Asset implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "teleconference")
    private Boolean teleconference;

    @Column(name = "projector")
    private Boolean projector;

    @Column(name = "whiteboard")
    private Boolean whiteboard;

    @Column(name = "a_c")
    private Boolean aC;

    @Column(name = "internet")
    private Boolean internet;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isTeleconference() {
        return teleconference;
    }

    public Asset teleconference(Boolean teleconference) {
        this.teleconference = teleconference;
        return this;
    }

    public void setTeleconference(Boolean teleconference) {
        this.teleconference = teleconference;
    }

    public Boolean isProjector() {
        return projector;
    }

    public Asset projector(Boolean projector) {
        this.projector = projector;
        return this;
    }

    public void setProjector(Boolean projector) {
        this.projector = projector;
    }

    public Boolean isWhiteboard() {
        return whiteboard;
    }

    public Asset whiteboard(Boolean whiteboard) {
        this.whiteboard = whiteboard;
        return this;
    }

    public void setWhiteboard(Boolean whiteboard) {
        this.whiteboard = whiteboard;
    }

    public Boolean isaC() {
        return aC;
    }

    public Asset aC(Boolean aC) {
        this.aC = aC;
        return this;
    }

    public void setaC(Boolean aC) {
        this.aC = aC;
    }

    public Boolean isInternet() {
        return internet;
    }

    public Asset internet(Boolean internet) {
        this.internet = internet;
        return this;
    }

    public void setInternet(Boolean internet) {
        this.internet = internet;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Asset asset = (Asset) o;
        if (asset.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), asset.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Asset{" +
            "id=" + getId() +
            ", teleconference='" + isTeleconference() + "'" +
            ", projector='" + isProjector() + "'" +
            ", whiteboard='" + isWhiteboard() + "'" +
            ", aC='" + isaC() + "'" +
            ", internet='" + isInternet() + "'" +
            "}";
    }
}

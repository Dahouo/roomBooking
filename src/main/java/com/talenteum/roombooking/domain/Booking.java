package com.talenteum.roombooking.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Booking.
 */
@Entity
@Table(name = "booking")
public class Booking implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "jhi_start", nullable = false)
    private ZonedDateTime start;

    @NotNull
    @Column(name = "jhi_end", nullable = false)
    private ZonedDateTime end;

    @NotNull
    @Column(name = "purpose", nullable = false)
    private String purpose;

    @ManyToOne
    @JsonIgnoreProperties("bookings")
    private Room room;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getStart() {
        return start;
    }

    public Booking start(ZonedDateTime start) {
        this.start = start;
        return this;
    }

    public void setStart(ZonedDateTime start) {
        this.start = start;
    }

    public ZonedDateTime getEnd() {
        return end;
    }

    public Booking end(ZonedDateTime end) {
        this.end = end;
        return this;
    }

    public void setEnd(ZonedDateTime end) {
        this.end = end;
    }

    public String getPurpose() {
        return purpose;
    }

    public Booking purpose(String purpose) {
        this.purpose = purpose;
        return this;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public Room getRoom() {
        return room;
    }

    public Booking room(Room room) {
        this.room = room;
        return this;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public User getUser() {
        return user;
    }

    public Booking user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
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
        Booking booking = (Booking) o;
        if (booking.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), booking.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Booking{" +
            "id=" + getId() +
            ", start='" + getStart() + "'" +
            ", end='" + getEnd() + "'" +
            ", purpose='" + getPurpose() + "'" +
            "}";
    }
}

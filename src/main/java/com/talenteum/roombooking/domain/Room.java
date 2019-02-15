package com.talenteum.roombooking.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.talenteum.roombooking.domain.enumeration.Status;

/**
 * A Room.
 */
@Entity
@Table(name = "room")
public class Room implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "image")
    private String image;

    @NotNull
    @Column(name = "floor", nullable = false)
    private Float floor;

    @Column(name = "capacity")
    private Float capacity;

    @NotNull
    @Column(name = "location", nullable = false)
    private String location;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @OneToOne
    @JoinColumn(unique = true)
    private Asset assets;

    @OneToMany(mappedBy = "room")
    private Set<Booking> bookings = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Room name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public Room image(String image) {
        this.image = image;
        return this;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Float getFloor() {
        return floor;
    }

    public Room floor(Float floor) {
        this.floor = floor;
        return this;
    }

    public void setFloor(Float floor) {
        this.floor = floor;
    }

    public Float getCapacity() {
        return capacity;
    }

    public Room capacity(Float capacity) {
        this.capacity = capacity;
        return this;
    }

    public void setCapacity(Float capacity) {
        this.capacity = capacity;
    }

    public String getLocation() {
        return location;
    }

    public Room location(String location) {
        this.location = location;
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Status getStatus() {
        return status;
    }

    public Room status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Asset getAssets() {
        return assets;
    }

    public Room assets(Asset asset) {
        this.assets = asset;
        return this;
    }

    public void setAssets(Asset asset) {
        this.assets = asset;
    }

    public Set<Booking> getBookings() {
        return bookings;
    }

    public Room bookings(Set<Booking> bookings) {
        this.bookings = bookings;
        return this;
    }

    public Room addBookings(Booking booking) {
        this.bookings.add(booking);
        booking.setRoom(this);
        return this;
    }

    public Room removeBookings(Booking booking) {
        this.bookings.remove(booking);
        booking.setRoom(null);
        return this;
    }

    public void setBookings(Set<Booking> bookings) {
        this.bookings = bookings;
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
        Room room = (Room) o;
        if (room.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), room.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Room{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", image='" + getImage() + "'" +
            ", floor=" + getFloor() +
            ", capacity=" + getCapacity() +
            ", location='" + getLocation() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}

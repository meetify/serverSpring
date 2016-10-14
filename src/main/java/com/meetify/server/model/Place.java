package com.meetify.server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Place class
 * Created by dmitry on 10/14/16.
 */

@SuppressWarnings("unused")
@Entity
public class Place extends JsonToString {

    private static Long currentId;
    @EmbeddedId
    private Id id;
    private String name;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "owner", nullable = false)
    private User owner;

    @ElementCollection
    private Set<Id> allowed;

    private Place() {

    }

    public Place(String name) {
        this.name = name;
        this.id = new Id(nextId());
        this.allowed = new HashSet<>();
    }

    public Place(String name, User user) {
        this(name);
        this.name = name;
        this.owner = user;
    }

    public static void setCurrentId(Long value) {
        if (currentId == null) {
            currentId = value;
        }
    }

    private static Long nextId() {
        return currentId++;
    }

    public User getOwner() {
        return owner;
    }

    public Set<Id> getAllowed() {
        return allowed;
    }

    public Id getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Place place = (Place) o;

        return id != null ? id.equals(place.id) : place.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}

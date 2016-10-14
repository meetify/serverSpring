package com.meetify.server.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

/**
 * Place class
 * Created by dmitry on 10/14/16.
 */

@SuppressWarnings("unused")
@Entity
public class Place {

    private static Long currentId;
    @EmbeddedId
    private Id id;
    private String name;
    private Place() {
    }

    public Place(String name) {
        this.name = name;
        this.id = new Id(nextId());
    }

    public static void setCurrentId(Long value) {
        if (currentId == null || currentId < value) {
            currentId = value;
        }
    }

    private static Long nextId() {
        return currentId++;
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

    @Override
    public String toString() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return "";
        }
    }
}

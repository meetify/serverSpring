package com.meetify.server.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by kr3v on 02.10.2016.
 * Users entity. Based on VK ID.
 */

@SuppressWarnings("unused")

@Entity
@Table(name = "users")
public class User implements Serializable {

    @EmbeddedId
    private Id id;

    @ElementCollection
    private Set<Id> friends;

    @OneToMany
    @ElementCollection
    private Set<Place> created;

    @ElementCollection
    private Set<Id> allowed;

    public User(Id id, Set<Id> friends) {
        this.id = id;
        this.friends = friends;
        this.allowed = new HashSet<>();
        this.created = new HashSet<>();
    }

    private User() {
    }

    public User(Id id) {
        this.id = id;
    }

    public Set<Id> getAllowed() {
        return allowed;
    }

    public Set<Place> getCreated() {
        return created;
    }

    public Set<Id> getFriends() {
        return friends;
    }

    public void setFriends(Set<Id> friends) {
        this.friends = friends;
    }
    
    public Id getId() {
        return id;
    }
    
    public void setId(Id id) {
        this.id = id;
    }

    @Override
    public String toString() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return "";
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return id != null ? id.equals(user.id) : user.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}

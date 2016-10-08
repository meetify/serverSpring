package com.meetify.server.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by kr3v on 02.10.2016.
 * Users entity. Based on VK ID.
 */

@SuppressWarnings("unused")

@Entity
@Table(name = "users")
@IdClass(UserID.class)
public class User implements Serializable {

    @Id
    @Column(name = "vkid")
    private Long vkID;

    @ElementCollection
    @Column(name = "friends")
    private Set<Long> friends;

    @OneToOne
    private Key key;

    private User() {

    }

    public User(Long vkID, Set<Long> friends) {
        this.vkID = vkID;
        this.friends = friends;
    }

    public Set<Long> getFriends() {
        return friends;
    }

    public User setFriends(Set<Long> friends) {
        this.friends = friends;
        return this;
    }

    public Boolean isFriend(User user) {
        return this.friends.contains(user.getVkID());
    }

    public Boolean isFriend(Long value) {
        return this.friends.contains(value);
    }

    public Long getVkID() {
        return vkID;
    }

    @Override
    public String toString() {
        return "User{" +
                "vkID=" + vkID +
                ", friends=" + friends +
                ", key=" + key +
                '}';
    }
}

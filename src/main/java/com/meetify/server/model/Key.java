package com.meetify.server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.meetify.server.model.ids.KeyID;

import javax.persistence.*;
import java.io.Serializable;

import static java.lang.System.currentTimeMillis;

/**
 * com.meetify.server.model
 * Created by kr3v on 08.10.2016.
 */

@Entity
@Table(name = "keys")
@IdClass(KeyID.class)
public class Key implements Serializable {

    @Id
    private final Long vkID;

    @JsonIgnore
    @OneToOne
    private final User user;

    @Column(name = "time")
    private final Long time;

    @Column(name = "devinfo")
    private final String devInfo;

    public Key() {
        this.user = null;
        this.time = null;
        this.devInfo = null;
        this.vkID = null;
    }

    public Key(User user, String devInfo) {
        this.user = user;
        this.devInfo = devInfo;
        this.time = currentTimeMillis();
        this.vkID = user.getVkID();
    }

    public User getUser() {
        return user;
    }

    public boolean usable() {
        return (currentTimeMillis() - time) >= (3600 * 1000);
    }

    public Long getVkID() {
        return vkID;
    }

    public Long getTime() {
        return time;
    }

    public String getDevInfo() {
        return devInfo;
    }
}

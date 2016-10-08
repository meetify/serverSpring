package com.meetify.server.model;

import java.io.Serializable;

/**
 * com.meetify.server.model
 * Created by kr3v on 08.10.2016.
 */
@SuppressWarnings("unused")
class KeyID implements Serializable {
    private User user;

    public KeyID(User user) {
        this.user = user;
    }

    public KeyID() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KeyID keyID = (KeyID) o;

        return user != null ? user.equals(keyID.user) : keyID.user == null;

    }

    @Override
    public int hashCode() {
        return user != null ? user.hashCode() : 0;
    }
}


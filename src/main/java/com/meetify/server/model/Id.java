package com.meetify.server.model;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created by dmitry on 10/14/16.
 */

@Embeddable
public class Id extends JsonParser implements Serializable {

    private Long id;

    public Id() {
    }

    public Id(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Id id1 = (Id) o;

        return id != null ? id.equals(id1.id) : id1.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}

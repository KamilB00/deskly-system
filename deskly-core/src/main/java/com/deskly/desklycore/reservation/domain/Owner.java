package com.deskly.desklycore.reservation.domain;

import java.util.Objects;

import static com.deskly.desklycore.reservation.domain.OwnerType.BUSINESS;
import static com.deskly.desklycore.reservation.domain.OwnerType.PERSON;


public record Owner(OwnerId id, OwnerType type) {

    public static Owner business(OwnerId id) {
        return new Owner(id, BUSINESS);
    }

    public static Owner person(OwnerId id) {
        return new Owner(id, PERSON);
    }

    public static Owner none() {
        return new Owner(null, null);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Owner owner = (Owner) o;
        return Objects.equals(id, owner.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}

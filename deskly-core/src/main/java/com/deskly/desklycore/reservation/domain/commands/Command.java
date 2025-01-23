package com.deskly.desklycore.reservation.domain.commands;

import com.deskly.desklycore.reservation.domain.ReservationProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import static java.util.Objects.requireNonNull;

@Getter
@EqualsAndHashCode
public abstract class Command<VALUE> {

    private final VALUE value;

    private final ReservationProperty property;

    public Command(VALUE value, ReservationProperty property) {
        this.value = requireNonNull(value);
        this.property = requireNonNull(property);
    }
}

package com.deskly.desklycore.reservation.domain.changes;

import com.deskly.desklycore.reservation.domain.ReservationProperty;
import com.deskly.desklycore.reservation.domain.ReservationVersion;
import jakarta.annotation.Nullable;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

@Getter
public abstract class ReservationChange<TYPE> {

    private final TYPE oldValue;

    private final TYPE newValue;

    private final ReservationProperty property;

    private final LocalDateTime occurredAt;

    private final ReservationVersion version;

    public ReservationChange(@Nullable TYPE oldValue, TYPE newValue, ReservationProperty property, ReservationVersion version) {
        this.oldValue = oldValue;
        this.newValue = requireNonNull(newValue);
        this.property = requireNonNull(property);
        this.occurredAt = LocalDateTime.now(ZoneId.of("UTC"));
        this.version = requireNonNull(version);
    }

    public ReservationChange(TYPE oldValue, TYPE newValue, ReservationProperty property, LocalDateTime occurredAt, ReservationVersion version) {
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.property = property;
        this.occurredAt = occurredAt;
        this.version = version;
    }

    public Optional<TYPE> getOldValue() {
        return Optional.ofNullable(oldValue);
    }

    public final boolean isChanged() {
        return oldValue == null || !oldValue.equals(newValue);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ReservationChange<?> that)) return false;
        return Objects.equals(oldValue, that.oldValue) && Objects.equals(newValue, that.newValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(oldValue, newValue);
    }
}

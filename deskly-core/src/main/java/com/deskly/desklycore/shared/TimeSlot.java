package com.deskly.desklycore.shared;

import java.time.*;
import java.util.ArrayList;
import java.util.List;

public record TimeSlot(Instant from, Instant to) {

    public static TimeSlot empty() {
        return new TimeSlot(Instant.EPOCH, Instant.EPOCH);
    }

    public static TimeSlot createDailyTimeSlotAtUTC(int year, int month, int day) {
        return createTimeSlotAtUTCOfDuration(year, month, day, Duration.ofDays(1));
    }

    public static TimeSlot createTimeSlotAtUTCOfDuration(int year, int month, int day, Duration duration) {
        LocalDate thisDay = LocalDate.of(year, month, day);
        Instant from = thisDay.atStartOfDay(ZoneId.of("UTC")).toInstant();
        return new TimeSlot(from, from.plus(duration));
    }

    public static TimeSlot createTimeSlotAtUTCOfDuration(int year, int month, int day, int hour, int minute, Duration duration) {
        LocalDateTime thisDay = LocalDateTime.of(year, month, day, hour, minute);
        Instant from = thisDay.atZone(ZoneId.of("UTC")).toInstant();
        return new TimeSlot(from, from.plus(duration));
    }

    public static TimeSlot createMonthlyTimeSlotAtUTC(int year, int month) {
        LocalDate startOfMonth = LocalDate.of(year, month, 1);
        LocalDate endOfMonth = startOfMonth.plusMonths(1);
        Instant from = startOfMonth.atStartOfDay(ZoneId.of("UTC")).toInstant();
        Instant to = endOfMonth.atTime(0, 0, 0).atZone(ZoneId.of("UTC")).toInstant();
        return new TimeSlot(from, to);
    }

    boolean overlapsWith(TimeSlot other) {
        return !this.from().isAfter(other.to()) && !this.to().isBefore(other.from());
    }

    public boolean within(TimeSlot other) {
        return !this.from.isBefore(other.from) && !this.to.isAfter(other.to);
    }

    public List<TimeSlot> leftoverAfterRemovingCommonWith(TimeSlot other) {
        List<TimeSlot> result = new ArrayList<>();
        if (other.equals(this)) {
            return List.of();
        }
        if (!other.overlapsWith(this)) {
            return List.of(this, other);
        }
        if (this.equals(other)) {
            return result;
        }
        if (this.from.isBefore(other.from)) {
            result.add(new TimeSlot(this.from, other.from));
        }
        if (other.from.isBefore(this.from)) {
            result.add(new TimeSlot(other.from, this.from));
        }
        if (this.to.isAfter(other.to)) {
            result.add(new TimeSlot(other.to, this.to));
        }
        if (other.to.isAfter(this.to)) {
            result.add(new TimeSlot(this.to, other.to));
        }
        return result;
    }

    public TimeSlot commonPartWith(TimeSlot other) {
        if (!this.overlapsWith(other)) {
            return TimeSlot.empty();
        }
        Instant commonStart = this.from.isAfter(other.from) ? this.from : other.from;
        Instant commonEnd = this.to.isBefore(other.to) ? this.to : other.to;
        return new TimeSlot(commonStart, commonEnd);
    }

    boolean isEmpty() {
        return this.from.equals(this.to);
    }

    public Duration duration() {
        return Duration.between(this.from, this.to);
    }

    public TimeSlot stretch(Duration duration) {
        return new TimeSlot(this.from.minus(duration), this.to.plus(duration));
    }
}

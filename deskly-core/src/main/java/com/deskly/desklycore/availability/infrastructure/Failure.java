package com.deskly.desklycore.availability.infrastructure;

public record Failure(String message) {

    public static Failure withReason(String message) {
        return new Failure(message);
    }

}

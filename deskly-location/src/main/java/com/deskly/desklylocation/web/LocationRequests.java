package com.deskly.desklylocation.web;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

public class LocationRequests {

    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateLocation {
        public String name;

        public String email;

        public String phoneNumber;

        public String city;

        public String postalCode;

        public String street;

        public String buildingName;

        public String flatNumber;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateLocation {
        public String name;

        public String email;

        public String phoneNumber;

        public String city;

        public String postalCode;

        public String street;

        public String buildingName;

        public String flatNumber;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    public static class AssignResource {

        public Set<UUID> resourceIds;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    public static class UnassignResource {

        public Set<UUID> resourceIds;
    }

}

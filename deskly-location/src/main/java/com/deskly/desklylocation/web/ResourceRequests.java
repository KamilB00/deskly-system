package com.deskly.desklylocation.web;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Set;

public class ResourceRequests {

    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateResource {
        public String name;

        public String type;

        public Set<AttributeDTO> attributes;

    }

    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateResource {
        public String name;

        public String type;

        public Set<AttributeDTO> attributes;

    }
}

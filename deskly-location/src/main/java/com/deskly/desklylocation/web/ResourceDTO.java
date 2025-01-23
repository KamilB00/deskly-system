package com.deskly.desklylocation.web;

import java.util.Set;

public class ResourceDTO {

    public String id;

    public String name;

    public String type;

    public Set<String> photoUrls;

    public Set<AttributeDTO> attributes;

    public ResourceDTO(String id, String name, String type, Set<String> photoUrls, Set<AttributeDTO> attributes) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.photoUrls = photoUrls;
        this.attributes = attributes;
    }
}

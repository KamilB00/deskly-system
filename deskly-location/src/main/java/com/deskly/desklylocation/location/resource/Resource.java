package com.deskly.desklylocation.location.resource;

import com.deskly.desklylocation.shared.language.Photo;
import com.deskly.desklylocation.web.AttributeDTO;
import com.deskly.desklylocation.web.ResourceDTO;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import org.hibernate.annotations.Type;

import java.util.Set;
import java.util.stream.Collectors;

@Entity(name = "resources")
public class Resource {

    @EmbeddedId
    private ResourceId id = ResourceId.newOne();

    @Version
    private int version;

    @Column
    private String name;

    @Enumerated(EnumType.STRING)
    private ResourceType type;

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    Set<Photo> photos;

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private Set<Attribute> attributes;

    public Resource(ResourceId id, String name, ResourceType type, Set<Attribute> attributes) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.photos = Set.of();
        this.attributes = attributes != null ? attributes : Set.of();
    }

    public Resource() {

    }

    public ResourceId id() {
        return id;
    }

    public ResourceDTO toDTO() {
        Set<String> photoUrls = photos.stream().map(Photo::url).collect(Collectors.toSet());
        Set<AttributeDTO> attributesDto = attributes.stream()
                .map(attribute -> new AttributeDTO(attribute.name(), attribute.value()))
                .collect(Collectors.toSet());
        return new ResourceDTO(id.id().toString(), name, type.name(), photoUrls, attributesDto);
    }

    void setName(String name) {
        this.name = name;
    }

    void setType(ResourceType type) {
        this.type = type;
    }

    void addPhotos(Set<Photo> photos) {
        this.photos.addAll(photos);
    }

    void addAttribute(Set<Attribute> attributes) {
        this.attributes.addAll(attributes);
    }
}

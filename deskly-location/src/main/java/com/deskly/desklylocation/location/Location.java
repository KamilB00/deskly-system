package com.deskly.desklylocation.location;


import com.deskly.desklylocation.shared.language.Address;
import com.deskly.desklylocation.shared.language.Photo;
import com.deskly.desklylocation.web.LocationDTO;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import org.hibernate.annotations.Type;

import java.util.Set;

@Entity(name = "locations")
public class Location {

    @EmbeddedId
    private LocationId id = LocationId.newOne();

    @Version
    private int version;

    @Column
    private String name;

    @Column
    private String email;

    @Column
    private String phoneNumber;

    @Embedded
    private Address address;

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private Set<Photo> photos;

    Location(LocationId id, String name, String email, String phoneNumber, Address address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.photos = Set.of();
    }

    public Location() {
    }

    public LocationId id() {
        return id;
    }

    public LocationDTO toDTO() {
        return new LocationDTO(id, name, email, phoneNumber, address, photos);
    }

    void setName(String name) {
        this.name = name;
    }

    void setEmail(String email) {
        this.email = email;
    }

    void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    void setAddress(Address address) {
        this.address = address;
    }

    void addPhotos(Set<Photo> photos) {
        this.photos.addAll(photos);
    }
}

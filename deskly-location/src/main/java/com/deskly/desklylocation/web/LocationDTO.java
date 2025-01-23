package com.deskly.desklylocation.web;

import com.deskly.desklylocation.shared.language.Photo;
import com.deskly.desklylocation.shared.language.Address;
import com.deskly.desklylocation.location.LocationId;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class LocationDTO {

    public String id;

    public String name;

    public String email;

    public String phoneNumber;

    public String city;

    public String postalCode;

    public String street;

    public String buildingName;

    public String flatNumber;

    public List<String> photosUrls;

    public LocationDTO(LocationId id, String name, String email, String phoneNumber, Address address, Set<Photo> photos) {
        this.id = id.id().toString();
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.city = address.city();
        this.postalCode = address.postalCode();
        this.street = address.street();
        this.buildingName = address.buildingName();
        this.flatNumber = address.flatNumber();
        this.photosUrls = photos.stream().map(Photo::url).collect(Collectors.toList());
    }
}

package com.deskly.desklylocation.location;

import com.deskly.desklylocation.shared.language.Address;
import com.deskly.desklylocation.shared.language.Photo;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


public class LocationFacade {

    private final Logger log = LoggerFactory.getLogger(LocationFacade.class);

    private final LocationRepository locationRepository;

    private final FileRepository fileRepository;

    public LocationFacade(LocationRepository locationRepository, FileRepository fileRepository) {
        this.locationRepository = locationRepository;
        this.fileRepository = fileRepository;
    }

    public LocationId createLocation(String name, String email, String phoneNumber, Address address) {
        LocationId id = LocationId.newOne();
        Location location = new Location(id, name, email, phoneNumber, address);
        return locationRepository.save(location).id();
    }

    public Location uploadPhotos(LocationId id, List<MultipartFile> files) {
        Set<Photo> photos = new HashSet<>();
        for (MultipartFile file : files) {
            try {
                photos.add(new Photo(fileRepository.upload(file).url()));
            } catch (Exception e) {
                log.error("Could not upload location photo: {}", e.getMessage());
                throw new RuntimeException(e);
            }
        }
        Location location = getLocationOrElseThrow(id);
        location.addPhotos(photos);
        return locationRepository.save(location);
    }

    @Transactional
    public LocationId updateLocation(LocationId id, String name, String email, String phoneNumber, Address address) {
        Location location = getLocationOrElseThrow(id);
        location.setName(name);
        location.setEmail(email);
        location.setPhoneNumber(phoneNumber);
        location.setAddress(address);
        return locationRepository.save(location).id();
    }

    public Optional<Location> findById(LocationId id) {
        return locationRepository.findById(id);
    }

    public List<Location> findAll() {
        return locationRepository.findAll();
    }

    private Location getLocationOrElseThrow(LocationId id) {
        return locationRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Location does not exist"));
    }

}

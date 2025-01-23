package com.deskly.desklylocation.web;

import com.deskly.desklylocation.location.Location;
import com.deskly.desklylocation.location.LocationFacade;
import com.deskly.desklylocation.location.LocationId;
import com.deskly.desklylocation.location.assignment.ResourceLocationAssignmentFacade;
import com.deskly.desklylocation.location.resource.Resource;
import com.deskly.desklylocation.location.resource.ResourceFacade;
import com.deskly.desklylocation.location.resource.ResourceId;
import com.deskly.desklylocation.shared.language.Address;
import jakarta.persistence.EntityNotFoundException;
import lombok.val;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

@RestController
@RequestMapping("/api/v1/deskly-location")
public class LocationController {

    private final LocationFacade locationFacade;

    private final ResourceLocationAssignmentFacade assignmentFacade;

    private final ResourceFacade resourceFacade;

    public LocationController(LocationFacade locationFacade, ResourceLocationAssignmentFacade assignmentFacade, ResourceFacade resourceFacade) {
        this.locationFacade = requireNonNull(locationFacade);
        this.assignmentFacade = requireNonNull(assignmentFacade);
        this.resourceFacade = requireNonNull(resourceFacade);
    }

    @PostMapping("/location")
    public LocationId create(@RequestBody LocationRequests.CreateLocation request) {
        Address address = new Address(request.city, request.postalCode, request.street, request.buildingName, request.flatNumber);
        return locationFacade.createLocation(request.name, request.email, request.phoneNumber, address);
    }

    @GetMapping("/locations")
    public List<LocationDTO> allLocations() {
        return locationFacade.findAll().stream()
                .map(Location::toDTO)
                .toList();
    }

    @PostMapping("/location/{id}/upload")
    public LocationDTO upload(@PathVariable UUID id, @RequestParam("files") List<MultipartFile> files) {
        return locationFacade.uploadPhotos(new LocationId(id), files).toDTO();
    }

    @PutMapping("/location/{id}")
    public LocationId update(@PathVariable UUID id, @RequestBody LocationRequests.UpdateLocation request) {
        Address address = new Address(request.city, request.postalCode, request.street, request.buildingName, request.flatNumber);
        return locationFacade.updateLocation(new LocationId(id), request.name, request.email, request.phoneNumber, address);
    }

    @GetMapping("/location/{id}")
    public LocationDTO location(@PathVariable UUID id) {
        return locationFacade.findById(new LocationId(id)).map(Location::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Location with id " + id + " was not found"));
    }

    @GetMapping("/location/{id}/resource")
    public Set<ResourceDTO> locationResources(@PathVariable UUID id) {
        val resources = assignmentFacade.resourceAssignedToLocation(new LocationId(id));
        return resourceFacade.findResourcesById(resources).stream().map(Resource::toDTO).collect(Collectors.toSet());
    }

    @PostMapping("/location/{id}/resource/assign")
    public void assign(@PathVariable UUID id, @RequestBody LocationRequests.AssignResource request) {
        val resourceIds = request.resourceIds.stream()
                .map(ResourceId::new)
                .collect(Collectors.toSet());
        assignmentFacade.assign(resourceIds, new LocationId(id));
    }

    @PostMapping("/location/{id}/resource/unassign")
    public void unassign(@PathVariable UUID id, @RequestBody LocationRequests.UnassignResource request) {
        val resourceIds = request.resourceIds.stream()
                .map(ResourceId::new)
                .collect(Collectors.toSet());
        assignmentFacade.unassign(resourceIds, new LocationId(id));
    }
}

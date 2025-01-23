package com.deskly.desklylocation.web;

import com.deskly.desklylocation.location.resource.*;
import com.deskly.desklylocation.shared.language.Photo;
import lombok.val;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/deskly-location/resource")
public class ResourceController {

    private final ResourceFacade resourceFacade;

    public ResourceController(ResourceFacade resourceFacade) {
        this.resourceFacade = resourceFacade;
    }

    @PostMapping
    public ResourceId create(@RequestBody ResourceRequests.CreateResource request) {
        val attributes = request.attributes.stream()
                .map(attribute -> new Attribute(attribute.name, attribute.value))
                .collect(Collectors.toSet());
        return resourceFacade.create(request.name, ResourceType.valueOf(request.type), attributes);
    }

    @PutMapping("/{id}")
    public ResourceId update(@PathVariable UUID id, @RequestBody ResourceRequests.UpdateResource request) {
        val attributes = request.attributes.stream()
                .map(attribute -> new Attribute(attribute.name, attribute.value))
                .collect(Collectors.toSet());
        return resourceFacade.update(new ResourceId(id), request.name, ResourceType.valueOf(request.type), attributes);
    }

    @PostMapping("/{id}/upload")
    public ResourceDTO upload(@PathVariable UUID id, @RequestParam("files") List<MultipartFile> files) {
        return resourceFacade.upload(new ResourceId(id), files).toDTO();
    }

    @GetMapping("/{id}")
    public ResourceDTO details(@PathVariable UUID id) {
        return resourceFacade.getByIdOrElseThrow(new ResourceId(id)).toDTO();
    }

    @GetMapping
    public List<ResourceDTO> allResources() {
        return resourceFacade.findAll().stream().map(Resource::toDTO).collect(Collectors.toList());
    }


}

package com.deskly.desklylocation.location.resource;

import com.deskly.desklylocation.location.FileRepository;
import com.deskly.desklylocation.shared.language.Photo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Objects.requireNonNull;

public class ResourceFacade {

    private final Logger log = LoggerFactory.getLogger(ResourceFacade.class);

    private final ResourceRepository resourceRepository;

    private final FileRepository fileRepository;

    public ResourceFacade(ResourceRepository resourceRepository, FileRepository fileRepository) {
        this.resourceRepository = requireNonNull(resourceRepository);
        this.fileRepository = requireNonNull(fileRepository);
    }

    public ResourceId create(String name, ResourceType type, Set<Attribute> attributes) {
        ResourceId id = ResourceId.newOne();
        return resourceRepository.save(new Resource(id, name, type, attributes)).id();
    }

    public ResourceId update(ResourceId id, String name, ResourceType type, Set<Attribute> attributes) {
        Resource resource = getByIdOrElseThrow(id);
        resource.setName(name);
        resource.setType(type);
        resource.addAttribute(attributes);
        return resourceRepository.save(resource).id();
    }

    public Resource upload(ResourceId id, List<MultipartFile> files) {
        Set<Photo> photos = new HashSet<>();
        for (MultipartFile file : files) {
            try {
                photos.add(new Photo(fileRepository.upload(file).url()));
            } catch (Exception e) {
                log.error("Could not upload resource photo: {}", e.getMessage());
                throw new RuntimeException(e);
            }
        }
        Resource resource = getByIdOrElseThrow(id);
        resource.addPhotos(photos);
        return resourceRepository.save(resource);
    }

    public Resource getByIdOrElseThrow(ResourceId resourceId) {
        return resourceRepository.findById(resourceId)
                .orElseThrow(() -> new IllegalArgumentException("Could not find resource with id:" + resourceId.id().toString()));
    }

    public List<Resource> findResourcesById(Set<ResourceId> resources) {
        return resourceRepository.findAllById(resources);
    }

    public List<Resource> findAll() {
        return resourceRepository.findAll();
    }

}

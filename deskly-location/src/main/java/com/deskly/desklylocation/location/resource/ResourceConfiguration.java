package com.deskly.desklylocation.location.resource;

import com.deskly.desklylocation.location.FileRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ResourceConfiguration {

    @Bean
    ResourceFacade resourceFacade(ResourceRepository repository, FileRepository fileRepository) {
        return new ResourceFacade(repository, fileRepository);
    }

}

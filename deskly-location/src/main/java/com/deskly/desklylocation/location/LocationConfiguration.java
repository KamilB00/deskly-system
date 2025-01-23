package com.deskly.desklylocation.location;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LocationConfiguration {

    @Bean
    LocationFacade locationFacade(LocationRepository locationRepository, FileRepository fileRepository) {
        return new LocationFacade(locationRepository, fileRepository);
    }

}

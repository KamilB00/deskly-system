package com.deskly.desklylocation.shared.storage;

import com.deskly.desklylocation.location.LocationFacade;
import com.deskly.desklylocation.location.LocationId;
import com.deskly.desklylocation.location.assignment.ResourceLocationAssignmentFacade;
import com.deskly.desklylocation.location.resource.Attribute;
import com.deskly.desklylocation.location.resource.ResourceFacade;
import com.deskly.desklylocation.location.resource.ResourceId;
import com.deskly.desklylocation.location.resource.ResourceType;
import com.deskly.desklylocation.shared.language.Address;
import com.deskly.desklylocation.shared.language.Photo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Configuration
public class InitialDataConfiguration {

    private final Logger log = LoggerFactory.getLogger(InitialDataConfiguration.class);

    @Bean
    CommandLineRunner init(ResourceFacade resourceFacade, LocationFacade locationFacade, ResourceLocationAssignmentFacade locationAssignmentFacade) {
        return args -> {
            ResourceId resourceA = resourceFacade.create("Biurko podnoszone",
                    ResourceType.DESK,
                    Set.of(
                            new Attribute("serial number", "ABC123"),
                            new Attribute("width", "180"),
                            new Attribute("depth", "80"),
                            new Attribute("producer", "Fly Desk"))
            );

            ResourceId resourceB = resourceFacade.create("Biurko podnoszone",
                    ResourceType.DESK,
                    Set.of(
                            new Attribute("serial number", "XYZ123"),
                            new Attribute("width", "180"),
                            new Attribute("depth", "80"),
                            new Attribute("producer", "Fly Desk"))
            );

            ResourceId resourceC = resourceFacade.create("Biurko podnoszone",
                    ResourceType.DESK,
                    Set.of(
                            new Attribute("serial number", "XYZ123"),
                            new Attribute("width", "180"),
                            new Attribute("depth", "80"),
                            new Attribute("producer", "Fly Desk"))
            );

            ResourceId resourceD = resourceFacade.create("Biurko podnoszone",
                    ResourceType.DESK,
                    Set.of(
                            new Attribute("serial number", "XYZ123"),
                            new Attribute("width", "180"),
                            new Attribute("depth", "80"),
                            new Attribute("producer", "Fly Desk"))
            );

            LocationId locationA = locationFacade.createLocation(
                    "WeWork Warsaw",
                    "wework@warsaw.com",
                    "+48504000123",
                    new Address("Warsaw", "00-001", "Rondo Daszyńskiego", "12", "")
            );

            LocationId locationB = locationFacade.createLocation(
                    "Share Space Wrocław",
                    "sharespace@wroclaw.com",
                    "+48505321123",
                    new Address("Wrocław", "50-001", "Sucha", "1A", "3")
            );

            locationAssignmentFacade.assign(Set.of(resourceA, resourceB), locationA);
            locationAssignmentFacade.assign(Set.of(resourceC, resourceD), locationB);

            log.info("Resource A: " + resourceA.id().toString());
            log.info("Resource B: " + resourceB.id().toString());
            log.info("Resource C: " + resourceC.id().toString());
            log.info("Resource D: " + resourceD.id().toString());
            log.info("Location A: " + locationA.id().toString());
            log.info("Location B: " + locationB.id().toString());
        };
    }


}

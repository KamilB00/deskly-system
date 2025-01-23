package com.deskly.desklylocation.location.assignment;

import com.deskly.desklylocation.shared.publisher.EventsPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AssignmentConfiguration {

    @Bean
    ResourceLocationAssignmentFacade resourceLocationAssignmentFacade(AssignmentRepository repository,
                                                                      EventsPublisher publisher) {
        return new ResourceLocationAssignmentFacade(repository, publisher);
    }

}

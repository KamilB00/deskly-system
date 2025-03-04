package com.deskly.desklycore.availability.domain;


import com.deskly.desklycore.availability.infrastructure.AvailabilityEventsHandler;
import com.deskly.desklycore.shared.EventsPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.Clock;

@Configuration
public class AvailabilityConfiguration {

    @Bean
    AvailabilityFacade availabilityFacade(JdbcTemplate jdbcTemplate, EventsPublisher eventsPublisher, Clock clock) {
        return new AvailabilityFacade(new ResourceAvailabilityRepository(jdbcTemplate), new ResourceAvailabilityReadModel(jdbcTemplate), eventsPublisher, clock);
    }

    @Bean
    AvailabilityEventsHandler eventsHandler(AvailabilityFacade availabilityFacade) {
        return new AvailabilityEventsHandler(availabilityFacade);
    }
}

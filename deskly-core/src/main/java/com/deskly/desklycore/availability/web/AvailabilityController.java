package com.deskly.desklycore.availability.web;

import com.deskly.desklycore.availability.domain.AvailabilityFacade;
import com.deskly.desklycore.shared.ResourceId;
import com.deskly.desklycore.shared.TimeSlot;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/deskly-core/availability")
public class AvailabilityController {

    private final AvailabilityFacade availabilityFacade;

    public AvailabilityController(AvailabilityFacade availabilityFacade) {
        this.availabilityFacade = availabilityFacade;
    }

    @GetMapping("/resource/{resourceId}")
    List<ResourceAvailabilityDTO> resourceAvailability(@PathVariable UUID resourceId, @RequestParam String from, @RequestParam String to) {
        LocalDate dateFrom = LocalDate.parse(from);
        LocalDate dateTo = LocalDate.parse(to);

        Duration duration = Duration.ofDays(dateTo.toEpochDay() - dateFrom.toEpochDay() + 1);

        return availabilityFacade.findGrouped(
                        ResourceId.of(resourceId.toString()),
                        TimeSlot.createTimeSlotAtUTCOfDuration(dateFrom.getYear(), dateFrom.getMonthValue(), dateFrom.getDayOfMonth(), duration))
                .toDto();
    }


}

package com.deskly.desklycore.availability.domain;

import com.deskly.desklycore.shared.LocationId;
import com.deskly.desklycore.shared.TimeSlot;

import java.util.List;

public interface LocationAvailabilityRepository {

    List<TimeSlot> loadLocationAvailability(LocationId locationId);
}

package com.deskly.desklycore.availability.web;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ResourceAvailabilityDTO {

    public boolean isAvailable;

    public String dateFrom;

    public String dateTo;

    public String resourceId;

}

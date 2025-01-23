package com.deskly.desklylocation.shared.publisher;

import com.deskly.desklylocation.location.assignment.ResourceAssignedToLocation;
import com.deskly.desklylocation.location.assignment.ResourceUnassignedFromLocation;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;

public class SQSMessageSerializer {


    public static ObjectMapper getInstance() {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(ResourceAssignedToLocation.class, new ResourceAssignedToLocationSerializer());
        module.addSerializer(ResourceUnassignedFromLocation.class, new ResourceUnassignedFromLocationSerializer());
        mapper.registerModule(module);
        return mapper;
    }

    private static class ResourceAssignedToLocationSerializer extends JsonSerializer<ResourceAssignedToLocation> {
        @Override
        public void serialize(ResourceAssignedToLocation value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeStringField("eventType", "ResourceAssignedToLocation");
            jsonGenerator.writeStringField("eventId", value.eventId().toString());
            jsonGenerator.writeStringField("resourceId", value.resourceId().id().toString());
            jsonGenerator.writeStringField("locationId", value.locationId().id().toString());
            jsonGenerator.writeStringField("occurredAt", value.occurredAt().toString());
            jsonGenerator.writeEndObject();
        }
    }

    private static class ResourceUnassignedFromLocationSerializer extends JsonSerializer<ResourceUnassignedFromLocation> {

        @Override
        public void serialize(ResourceUnassignedFromLocation value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeStringField("eventType", "ResourceUnassignedFromLocation");
            jsonGenerator.writeStringField("eventId", value.eventId().toString());
            jsonGenerator.writeStringField("resourceId", value.resourceId().id().toString());
            jsonGenerator.writeStringField("locationId", value.locationId().id().toString());
            jsonGenerator.writeStringField("occurredAt", value.occurredAt().toString());
            jsonGenerator.writeEndObject();
        }
    }
}


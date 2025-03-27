package com.deskly.desklycore.availability.infrastructure;

import com.deskly.desklycore.shared.language.LocationId;
import com.deskly.desklycore.shared.language.ResourceId;
import com.deskly.desklycore.shared.messaging.ReceivedEvent;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;
import java.time.Instant;
import java.util.UUID;

public class SQSMessageDeserializer {

    private SQSMessageDeserializer() {
    }

    public static ObjectMapper getObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(ReceivedEvent.class, new ReceivedEventDeserializer());
        mapper.registerModule(module);
        return mapper;
    }

    public static class ReceivedEventDeserializer extends JsonDeserializer<ReceivedEvent> {
        @Override
        public ReceivedEvent deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            JsonNode rootNode = jsonParser.getCodec().readTree(jsonParser);
            String eventType = rootNode.get("eventType").asText();
            if (eventType.equals("ResourceAssignedToLocation")) {
                return new ResourceAssignedToLocationDeserializer().deserialize(jsonParser.getCodec().treeAsTokens(rootNode), deserializationContext);
            } else if (eventType.equals("ResourceUnassignedFromLocation")) {
                return new ResourceUnassignedFromLocationDeserializer().deserialize(jsonParser.getCodec().treeAsTokens(rootNode), deserializationContext);
            }
            throw new IllegalArgumentException("Could not deserialize due to unknown event type");
        }
    }


    public static class ResourceAssignedToLocationDeserializer extends JsonDeserializer<ResourceAssignedToLocation> {

        @Override
        public ResourceAssignedToLocation deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            JsonNode rootNode = jsonParser.getCodec().readTree(jsonParser);
            UUID eventId = UUID.fromString(rootNode.get("eventId").asText());
            ResourceId resourceId = ResourceId.of(rootNode.get("resourceId").asText());
            LocationId locationId = LocationId.of(rootNode.get("locationId").asText());
            Instant occurredAt = Instant.parse(rootNode.get("occurredAt").asText());
            return new ResourceAssignedToLocation(eventId, resourceId, locationId, occurredAt);
        }
    }

    public static class ResourceUnassignedFromLocationDeserializer extends JsonDeserializer<ResourceUnassignedFromLocation> {

        @Override
        public ResourceUnassignedFromLocation deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            JsonNode rootNode = jsonParser.getCodec().readTree(jsonParser);
            UUID eventId = UUID.fromString(rootNode.get("eventId").asText());
            ResourceId resourceId = ResourceId.of(rootNode.get("resourceId").asText());
            LocationId locationId = LocationId.of(rootNode.get("locationId").asText());
            Instant occurredAt = Instant.parse(rootNode.get("occurredAt").asText());
            return new ResourceUnassignedFromLocation(eventId, resourceId, locationId, occurredAt);
        }
    }

}

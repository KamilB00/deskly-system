package com.deskly.desklycore.shared.language;

import java.util.UUID;

public final class RequestId {

    private final UUID requestId;

    private RequestId(UUID requestId) {
        this.requestId = requestId;
    }

    public static RequestId newOne() {
        return new RequestId(UUID.randomUUID());
    }

    public static RequestId of(UUID id) {
        return new RequestId(id);
    }

    public UUID id() {
        return requestId;
    }
}

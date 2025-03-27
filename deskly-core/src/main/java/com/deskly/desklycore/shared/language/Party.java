package com.deskly.desklycore.shared.language;

import static com.deskly.desklycore.shared.language.PartyType.*;

public record Party(PartyId id, PartyType type) {

    public static Party organization(PartyId id) {
        return new Party(id, ORGANIZATION);
    }

    public static Party organizationMember(PartyId id) {
        return new Party(id, ORGANIZATION_MEMBER);
    }

    public static Party individual(PartyId id) {
        return new Party(id, INDIVIDUAL);
    }

    public static Party none() {
        return new Party(null, null);
    }
}

package com.deskly.desklycore.reservation.application;

import com.deskly.desklycore.reservation.domain.commands.Command;
import com.deskly.desklycore.shared.ResourceId;
import lombok.Builder;

import java.util.Set;

@Builder
public record ReservationProcessInput(ResourceId resourceId, Set<Command<?>> commands) {


}

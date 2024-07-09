package com.valdirsantos714.planner_planejador_de_viagens.payload;

import com.valdirsantos714.planner_planejador_de_viagens.model.Trip;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record TripPayloadResponse(@NotNull(message = "Não pode deixar este campo sem preencher!") String destination,
                                  @NotNull(message = "Não pode deixar este campo sem preencher!") LocalDateTime starts_at,
                                  @NotNull(message = "Não pode deixar este campo sem preencher!") LocalDateTime ends_at,
                                  boolean is_confirmed,
                                  UUID id,
                                  @NotNull(message = "Não pode deixar este campo sem preencher!") String owner_name,
                                  @NotNull(message = "Não pode deixar este campo sem preencher!") String owner_email) {

    public TripPayloadResponse(Trip trip) {
        this(trip.getDestination(), trip.getStarts_at(), trip.getEnds_at(), trip.is_confirmed(), trip.getId(), trip.getOwner_name(), trip.getOwner_email());
    }
}

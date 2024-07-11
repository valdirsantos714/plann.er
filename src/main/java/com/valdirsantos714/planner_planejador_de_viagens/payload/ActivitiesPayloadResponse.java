package com.valdirsantos714.planner_planejador_de_viagens.payload;

import com.valdirsantos714.planner_planejador_de_viagens.model.Activities;
import com.valdirsantos714.planner_planejador_de_viagens.model.Trip;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record ActivitiesPayloadResponse(UUID idActivities,
                                        @NotNull(message = "Não pode deixar o title null") String title,
                                        @NotNull(message = "Não pode deixar o title null") LocalDateTime occurs_at,
                                        Trip trip) {

    public ActivitiesPayloadResponse(Activities activities) {
        this(activities.getId(),activities.getTitle(), activities.getOccurs_at(), activities.getTrip());
    }
}

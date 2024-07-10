package com.valdirsantos714.planner_planejador_de_viagens.payload;

import com.valdirsantos714.planner_planejador_de_viagens.model.Activities;
import com.valdirsantos714.planner_planejador_de_viagens.model.Trip;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ActivitiesPayload(@NotNull(message = "Não pode deixar o title null") String title,
                                LocalDateTime occurs_at,
                                Trip trip) {

    public ActivitiesPayload(Activities activities) {
        this(activities.getTitle(), activities.getOccurs_at(), activities.getTrip());
    }
}

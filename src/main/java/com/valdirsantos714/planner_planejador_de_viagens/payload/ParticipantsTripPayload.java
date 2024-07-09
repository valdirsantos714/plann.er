package com.valdirsantos714.planner_planejador_de_viagens.payload;

import com.valdirsantos714.planner_planejador_de_viagens.model.Participants;
import com.valdirsantos714.planner_planejador_de_viagens.model.Trip;

import java.util.List;

public record ParticipantsTripPayload(Trip trip, List<Participants> participants) {
    public ParticipantsTripPayload(Trip trip) {
        this(trip, trip.getParticipantsList());
    }
}

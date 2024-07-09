package com.valdirsantos714.planner_planejador_de_viagens.payload;

import com.valdirsantos714.planner_planejador_de_viagens.model.Participants;
import com.valdirsantos714.planner_planejador_de_viagens.model.Trip;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ParticipantsPayloadResponse(@NotNull(message = "Não pode deixar este campo sem preencher!") UUID id,
                                          @NotNull(message = "Não pode deixar este campo sem preencher!") String name,
                                          @NotNull(message = "Não pode deixar este campo sem preencher!") String email,
                                          @NotNull(message = "Não pode deixar este campo sem preencher!") Boolean is_confirmed,
                                          Trip trip) {

    public ParticipantsPayloadResponse (Participants p) {
        this(p.getId(), p.getName(), p.getEmail(), p.getIs_confirmed(),p.getTrip());
    }

}

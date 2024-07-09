package com.valdirsantos714.planner_planejador_de_viagens.payload;

import com.valdirsantos714.planner_planejador_de_viagens.model.Participants;
import com.valdirsantos714.planner_planejador_de_viagens.model.Trip;
import jakarta.validation.constraints.NotNull;

public record ParticipantsPayload(@NotNull(message = "Não pode deixar este campo sem preencher!") String name,
                                  @NotNull(message = "Não pode deixar este campo sem preencher!") String email,
                                  @NotNull(message = "Não pode deixar este campo sem preencher!") Boolean is_confirmed,
                                  Trip trip) {

    public ParticipantsPayload(Participants p) {
        this(p.getName(), p.getEmail(), p.getIs_confirmed(),p.getTrip());
    }

}

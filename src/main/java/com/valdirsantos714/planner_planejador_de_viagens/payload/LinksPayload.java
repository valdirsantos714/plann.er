package com.valdirsantos714.planner_planejador_de_viagens.payload;

import com.valdirsantos714.planner_planejador_de_viagens.model.Links;
import com.valdirsantos714.planner_planejador_de_viagens.model.Trip;
import jakarta.validation.constraints.NotNull;

public record LinksPayload(@NotNull(message = "Não pode deixar o title null") String title,
                           @NotNull(message = "Não pode deixar o title null") String url,
                           Trip trip) {

    public LinksPayload(Links links) {
        this(links.getTitle(), links.getUrl(), links.getTrip());
    }
}

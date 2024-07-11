package com.valdirsantos714.planner_planejador_de_viagens.payload;

import com.valdirsantos714.planner_planejador_de_viagens.model.Links;
import com.valdirsantos714.planner_planejador_de_viagens.model.Trip;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record LinksPayloadResponse(UUID idLink,
                                   @NotNull(message = "Não pode deixar o title null") String title,
                                   @NotNull(message = "Não pode deixar o title null") String url,
                                   Trip trip) {

    public LinksPayloadResponse(Links links) {
        this(links.getId(), links.getTitle(), links.getUrl(), links.getTrip());
    }
}

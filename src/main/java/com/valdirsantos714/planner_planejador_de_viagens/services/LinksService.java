package com.valdirsantos714.planner_planejador_de_viagens.services;

import com.valdirsantos714.planner_planejador_de_viagens.model.Links;
import com.valdirsantos714.planner_planejador_de_viagens.repositories.LinksRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class LinksService {

    @Autowired
    private LinksRepository repository;

    @Autowired
    private TripService tripService;

    //Pra salvar e vincular os dois precisa só do id da trip e a classe links
    public Links save(UUID idTrip, Links link) {
        var trip = tripService.findById(idTrip);

        if (trip != null) {
            link.setTrip(trip);
            trip.getLinksList().add(link);
            //Primeiro salva o link depois a trip
            repository.save(link);
            tripService.save(trip, trip.getParticipantsList());
            return link;
        } else {
            throw new EntityNotFoundException("Erro trip não encontrada!");
        }
    }

    public List<Links> findAllByIdTrip(UUID idTrip) {
        var trip = tripService.findById(idTrip);
        var list = trip.getLinksList();

        if (list.isEmpty()) {
            throw new EntityNotFoundException("Erro! a lista de links está vazia");

        } else {
            return list;
        }
    }
}

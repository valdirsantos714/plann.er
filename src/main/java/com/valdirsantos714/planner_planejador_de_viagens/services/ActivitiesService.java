package com.valdirsantos714.planner_planejador_de_viagens.services;

import com.valdirsantos714.planner_planejador_de_viagens.model.Activities;
import com.valdirsantos714.planner_planejador_de_viagens.model.Trip;
import com.valdirsantos714.planner_planejador_de_viagens.repositories.ActivitiesRespository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ActivitiesService {

    @Autowired
    private ActivitiesRespository repository;

    @Autowired
    private TripService tripService;

    public Activities save(UUID idTrip, Activities activities) {
        var trip = tripService.findById(idTrip);
        trip.getActivitiesList().add(activities);
        activities.setTrip(trip); //Vincula a viagem com a activities
         //Quando eu registrar a atividade ele pega a hora exata em que eu fiz a activity

        if (activities.getOccurs_at().isBefore(trip.getStarts_at()) || activities.getOccurs_at().isAfter(trip.getEnds_at())) {
            throw new RuntimeException("Erro data da activity está fora do intervalo de datas da viagem!");
        } else {
            repository.save(activities);
            tripService.save(trip, trip.getParticipantsList());
            return activities;
        }
    }

    public List<Activities> findAllByIdTrip(UUID idTrip) {
        var trip = tripService.findById(idTrip);
        var list = trip.getActivitiesList();

        if (list.isEmpty()) {
            throw new EntityNotFoundException("A lista de activities está vazia");
        } else {
            return list;
        }
    }
}

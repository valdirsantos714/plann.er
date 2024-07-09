package com.valdirsantos714.planner_planejador_de_viagens.services;

import com.valdirsantos714.planner_planejador_de_viagens.model.Trip;
import com.valdirsantos714.planner_planejador_de_viagens.repositories.TripRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class TripService {

    @Autowired
    private TripRepository repository;

    public Trip save(Trip trip) {
        Trip newTrip = repository.save(trip);
        return newTrip;
    }

    public Trip findById(UUID id) {
        Trip trip = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Trip not found!"));

        return trip;
    }

    public Trip update(UUID id, Trip atualizedTrip) {
        Trip trip = findById(id);

        if (trip != null) {
            trip.setDestination(atualizedTrip.getDestination());
            trip.setStarts_at(atualizedTrip.getStarts_at());
            trip.setEnds_at(atualizedTrip.getEnds_at());
            trip.set_confirmed(atualizedTrip.is_confirmed());
            trip.setOwner_name(atualizedTrip.getOwner_name());
            trip.setOwner_email(atualizedTrip.getOwner_email());
            save(trip);
            return trip;
        } else {
            throw new EntityNotFoundException("Trip not found!");
        }
    }

    public void delete(UUID id) {
        Trip trip = findById(id);

        if (trip != null) {
            repository.delete(trip);

        } else {
            throw new EntityNotFoundException("Trip not found!");
        }
    }

    public List<Trip> findAll() {
        List<Trip> list = repository.findAll();

        if (list.isEmpty()) {
            throw new EntityNotFoundException("List of trips is empty!");

        } else {
            return list;
        }
    }
}

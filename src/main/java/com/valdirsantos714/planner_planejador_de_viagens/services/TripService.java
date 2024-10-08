package com.valdirsantos714.planner_planejador_de_viagens.services;

import com.valdirsantos714.planner_planejador_de_viagens.model.Participants;
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

    @Autowired
    private ParticipantsService participantsService;

    public Trip save(Trip trip, List<Participants> participantsList) {
        if (trip.getStarts_at().isAfter(trip.getEnds_at()) || trip.getEnds_at().isBefore(trip.getStarts_at())) {
            throw new RuntimeException("Erro data da viagem está definida incorretamente!");

        } else {
            Trip newTrip = repository.save(trip);
            participantsList.stream().forEach((p) -> p.setTrip(newTrip)); //Pega a lista e vincula essa trip a cada um desses participantes da lista
            newTrip.setParticipantsList(participantsList); //Pega a viagem e muda a lista de participantes por essa lista

            participantsService.saveAll(participantsList); //salva a lista de participantes
            repository.save(newTrip); //Salva a viagem

            return newTrip;
        }
    }

    public List<Participants> findAllParticipantsByIdTrip(UUID idTrip) {
        var trip = findById(idTrip);
        var list = trip.getParticipantsList();

        if (list.isEmpty()) {
            throw new EntityNotFoundException("Erro! A lista de participants está vazia");
        } else {
            return list;
        }
    }

    public Participants saveParticipantsInTrip(UUID idTrip, Participants participant) {
        var trip = findById(idTrip);

        if (trip != null) {
            trip.getParticipantsList().add(participant);
            participant.setTrip(trip);

            participantsService.save(participant);
            save(trip, trip.getParticipantsList());
            return participant;

        } else {
            throw new EntityNotFoundException("Erro! Trip não encontrada");
        }
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
            trip.setOwner_name(atualizedTrip.getOwner_name());
            trip.setOwner_email(atualizedTrip.getOwner_email());
            trip.setParticipantsList(atualizedTrip.getParticipantsList());
            save(trip, trip.getParticipantsList());
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

    public Trip confirmTrip(UUID id) {
        Trip trip = findById(id);
        if (!trip.is_confirmed()) {
            trip.set_confirmed(true);
            save(trip, trip.getParticipantsList());
            return trip;
        } else {
            throw new RuntimeException("Erro! a viagem já está confirmada!");
        }
    }

    public Trip cancelTrip(UUID id) {
        Trip trip = findById(id);
        if (trip.is_confirmed()) {
            trip.set_confirmed(false);
            save(trip, trip.getParticipantsList());
            return trip;
        } else {
            throw new RuntimeException("Erro! a viagem já está confirmada!");
        }
    }

}

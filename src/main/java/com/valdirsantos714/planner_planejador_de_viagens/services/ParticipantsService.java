package com.valdirsantos714.planner_planejador_de_viagens.services;

import com.valdirsantos714.planner_planejador_de_viagens.model.Participants;
import com.valdirsantos714.planner_planejador_de_viagens.model.Trip;
import com.valdirsantos714.planner_planejador_de_viagens.repositories.ParticipantsRepository;
import com.valdirsantos714.planner_planejador_de_viagens.repositories.TripRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ParticipantsService {

    @Autowired
    private ParticipantsRepository repository;


    public Participants save(Participants participant) {
        Participants newParticipant = repository.save(participant);
        return newParticipant;
    }

    public List<Participants> saveAll(List<Participants> participantsList) {
        if (participantsList.isEmpty()) {
            throw new EntityNotFoundException("Erro! lista vazia");
        } else {
            repository.saveAll(participantsList);
            return participantsList;
        }
    }

    public Participants findById(UUID id) {
        Participants participant = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Participant not found!"));

        return participant;
    }

    public Participants update(UUID id, Participants atualizedParticipant) {
        Participants participant = findById(id);

        if (participant != null) {
            participant.setName(atualizedParticipant.getName());
            participant.setEmail(atualizedParticipant.getEmail());
            participant.setTrip(atualizedParticipant.getTrip());
            save(participant);
            return participant;
        } else {
            throw new EntityNotFoundException("Participant not found!");
        }
    }

    public void delete(UUID id) {
        Participants participant = findById(id);

        if (participant != null) {
            repository.delete(participant);

        } else {
            throw new EntityNotFoundException("Participant not found!");
        }
    }

    public List<Participants> findAll() {
        List<Participants> list = repository.findAll();

        if (list.isEmpty()) {
            throw new EntityNotFoundException("List of Participants is empty!");

        } else {
            return list;
        }
    }

    public Participants confirmParticipant(UUID id) {
        Participants participant = findById(id);
        if (participant.getIs_confirmed() == false) {
            participant.setIs_confirmed(true);
            save(participant);
            return participant;
        } else {
            throw new RuntimeException("Erro! a pessoa já está confirmada!");
        }
    }

    public Participants cancelParticipant(UUID id) {
        Participants participant = findById(id);
        if (participant.getIs_confirmed() == true) {
            participant.setIs_confirmed(false);
            save(participant);
            return participant;
        } else {
            throw new RuntimeException("Erro! a pessoa já está confirmada!");
        }
    }

    /*public Trip cadastrarNaTrip(UUID idTrip, UUID idParticipant) {
        Trip atualizedTrip = tripService.findById(idTrip);
        var participant = findById(idParticipant);

        atualizedTrip.getParticipantsList().add(participant);
        participant.setTrip(atualizedTrip);

        tripService.save(atualizedTrip);
        repository.save(participant);

        return atualizedTrip;
    }*/

}

package com.valdirsantos714.planner_planejador_de_viagens.controllers;

import com.valdirsantos714.planner_planejador_de_viagens.model.Activities;
import com.valdirsantos714.planner_planejador_de_viagens.model.Trip;
import com.valdirsantos714.planner_planejador_de_viagens.payload.ActivitiesPayload;
import com.valdirsantos714.planner_planejador_de_viagens.payload.ActivitiesPayloadResponse;
import com.valdirsantos714.planner_planejador_de_viagens.payload.TripPayload;
import com.valdirsantos714.planner_planejador_de_viagens.payload.TripPayloadResponse;
import com.valdirsantos714.planner_planejador_de_viagens.services.ActivitiesService;
import com.valdirsantos714.planner_planejador_de_viagens.services.ParticipantsService;
import com.valdirsantos714.planner_planejador_de_viagens.services.TripService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/trip")
public class TripController {

    @Autowired
    private TripService service;

    @Autowired
    private ParticipantsService participantsService;

    @Autowired
    private ActivitiesService activitiesService;


    @GetMapping("/all")
    public ResponseEntity findAll() {
        List<Trip> list = service.findAll();
        return ResponseEntity.ok(list.stream().map(TripPayloadResponse::new).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable UUID id) {
        Trip trip = service.findById(id);

        if (trip != null) {
            return ResponseEntity.ok(new TripPayloadResponse(trip));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity save(@RequestBody @Valid TripPayload payload, UriComponentsBuilder uriBuilder) {
        var trip = new Trip(payload);
        var uri = uriBuilder.path("/trip/{id}").buildAndExpand(trip.getId()).toUri();

        service.save(trip, payload.participantsList());
        participantsService.saveAll(payload.participantsList());


        return ResponseEntity.created(uri).body(new TripPayloadResponse(trip));
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable UUID id, @RequestBody @Valid TripPayload payload) {
        var trip = service.findById(id);
        var atualizedTrip = service.update(id, new Trip(payload));

        return ResponseEntity.ok(new TripPayloadResponse(atualizedTrip));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable UUID id) {
        var trip = service.findById(id);
        if (trip != null) {
            service.delete(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/confirm")
    public ResponseEntity confirmTrip(@PathVariable UUID id) {
        var trip = service.findById(id);
        if (trip != null) {
            service.confirmTrip(id);
            service.save(trip, trip.getParticipantsList());
            return ResponseEntity.ok(new TripPayloadResponse(trip));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/cancel")
    public ResponseEntity cancelTrip(@PathVariable UUID id) {
        var trip = service.findById(id);
        if (trip != null) {
            service.cancelTrip(id);
            service.save(trip, trip.getParticipantsList());
            return ResponseEntity.ok(new TripPayloadResponse(trip));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/activities")
    public ResponseEntity cadastrarActivities(@PathVariable(name = "id") UUID idTrip, @RequestBody ActivitiesPayload payload, UriComponentsBuilder uriBuilder) {
        var trip = service.findById(idTrip);

        if (trip != null) {
            Activities activities = activitiesService.save(idTrip, new Activities(payload));
            var uri = uriBuilder.path("/trip/{id}/activities").buildAndExpand(activities.getId()).toUri();

            return ResponseEntity.created(uri).body(new ActivitiesPayload(activities));
        } else {
            throw new EntityNotFoundException("Trip não encontrada!");
        }

    }

    @GetMapping("/{id}/activities/all")
    public ResponseEntity verActivities(@PathVariable(name = "id") UUID idTrip){
        var trip = service.findById(idTrip);

        if (trip != null) {
            var lista = activitiesService.findAllByIdTrip(idTrip);
            return ResponseEntity.ok(lista.stream().map(ActivitiesPayloadResponse::new).toList());
        } else {
            throw new EntityNotFoundException("Erro trip não encontrada!");
        }
    }
}

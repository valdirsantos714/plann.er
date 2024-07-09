package com.valdirsantos714.planner_planejador_de_viagens.controllers;

import com.valdirsantos714.planner_planejador_de_viagens.model.Participants;
import com.valdirsantos714.planner_planejador_de_viagens.model.Trip;
import com.valdirsantos714.planner_planejador_de_viagens.payload.*;
import com.valdirsantos714.planner_planejador_de_viagens.services.ParticipantsService;
import com.valdirsantos714.planner_planejador_de_viagens.services.TripService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/participant")
public class ParticipantsController {

    @Autowired
    private ParticipantsService service;

    @Autowired
    private TripService tripService;

    @GetMapping("/all")
    public ResponseEntity findAll() {
        List<Participants> list = service.findAll();
        return ResponseEntity.ok(list.stream().map(ParticipantsPayloadResponse::new).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable UUID id) {
        Participants participant = service.findById(id);

        if (participant != null) {
            return ResponseEntity.ok(new ParticipantsPayloadResponse(participant));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity save(@RequestBody @Valid ParticipantsPayload payload, UriComponentsBuilder uriBuilder) {
        var participant = new Participants(payload);
        var uri = uriBuilder.path("/participant/{id}").buildAndExpand(participant.getId()).toUri();
        service.save(participant);
        return ResponseEntity.created(uri).body(new ParticipantsPayloadResponse(participant));
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable UUID id, @RequestBody @Valid ParticipantsPayload payload) {
        var participant = service.findById(id);
        var atualizedParticipant = service.update(id, new Participants(payload));

        return ResponseEntity.ok(new ParticipantsPayloadResponse(atualizedParticipant));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable UUID id) {
        var participant = service.findById(id);
        if (participant != null) {
            service.delete(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/confirm")
    public ResponseEntity confirmParticipant(@PathVariable UUID id) {
        var participant = service.findById(id);
        if (participant != null) {
            service.confirmParticipant(id);
            service.save(participant);
            return ResponseEntity.ok(new ParticipantsPayloadResponse(participant));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/cancel")
    public ResponseEntity cancelParticipant(@PathVariable UUID id) {
        var participant = service.findById(id);
        if (participant != null) {
            service.cancelParticipant(id);
            service.save(participant);
            return ResponseEntity.ok(new ParticipantsPayloadResponse(participant));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

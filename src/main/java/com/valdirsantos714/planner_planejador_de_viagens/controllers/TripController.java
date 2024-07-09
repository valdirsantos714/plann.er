package com.valdirsantos714.planner_planejador_de_viagens.controllers;

import com.valdirsantos714.planner_planejador_de_viagens.model.Trip;
import com.valdirsantos714.planner_planejador_de_viagens.payload.TripPayload;
import com.valdirsantos714.planner_planejador_de_viagens.payload.TripPayloadResponse;
import com.valdirsantos714.planner_planejador_de_viagens.services.TripService;
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
        service.save(trip);
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
}

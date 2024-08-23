package com.valdirsantos714.planner_planejador_de_viagens.controllers;

import com.valdirsantos714.planner_planejador_de_viagens.model.Participants;
import com.valdirsantos714.planner_planejador_de_viagens.model.Trip;
import com.valdirsantos714.planner_planejador_de_viagens.payload.ParticipantsPayload;
import com.valdirsantos714.planner_planejador_de_viagens.payload.ParticipantsPayloadResponse;
import com.valdirsantos714.planner_planejador_de_viagens.payload.TripPayload;
import com.valdirsantos714.planner_planejador_de_viagens.payload.TripPayloadResponse;
import com.valdirsantos714.planner_planejador_de_viagens.repositories.ParticipantsRepository;
import com.valdirsantos714.planner_planejador_de_viagens.repositories.TripRepository;
import com.valdirsantos714.planner_planejador_de_viagens.services.ParticipantsService;
import com.valdirsantos714.planner_planejador_de_viagens.services.TripService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class TripControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<TripPayload> objetoRequisicao;

    @Autowired
    private JacksonTester<TripPayloadResponse> objetoReceber;

    @Autowired
    private JacksonTester<ParticipantsPayload> participantsPayload;

    @Autowired
    private JacksonTester<ParticipantsPayloadResponse> participantsPayloadResponse;

    @MockBean
    private TripRepository repository;

    @MockBean
    private TripService service;

    @MockBean
    private ParticipantsRepository participantsRepository;

    @MockBean
    private ParticipantsService participantsService;


    @Test
    @DisplayName("Salva trips")
    void salvaTrips() throws Exception {
        var trip = new Trip(null, "Joao pessoa", LocalDateTime.now(), LocalDateTime.parse("2024-10-20T18:30"), true, "Joao", "joao@planner.com", null, null, null);

        var participants = new ArrayList<>(List.of(
                new Participants(null, "Pedro", "pedro@gmail.com", false, trip),
                new Participants(null, "Julia", "julia@gmail.com", true, trip),
                new Participants(null, "Valdir", "valdir@gmail.com", true, trip)
                ));

        trip.setParticipantsList(participants);
        var tripPayload = new TripPayload(trip);

        when(repository.save(any(Trip.class))).thenReturn(trip);
        when(participantsRepository.saveAll(participants)).thenReturn(participants);


        //Arrange
        var response = mvc.perform(
                post("/trip")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content( objetoRequisicao.write(
                                tripPayload
                                ).getJson())
                ).andReturn().getResponse();

        var jsonEsperado = objetoReceber.write(
                new TripPayloadResponse(trip)
        ).getJson();

        Assertions.assertEquals(jsonEsperado, response.getContentAsString());

    }

    @Test
    @DisplayName("Atualiza trips")
    void atualizaTrips() throws Exception {

        var uuid = UUID.randomUUID();
        var trip = new Trip(uuid, "Joao pessoa", LocalDateTime.now(), LocalDateTime.parse("2024-10-20T18:30"), true, "Joao", "joao@planner.com", null, null, null);

        var participants = new ArrayList<>(List.of(
                new Participants(null, "Pedro", "pedro@gmail.com", false, trip),
                new Participants(null, "Julia", "julia@gmail.com", true, trip),
                new Participants(null, "Valdir", "valdir@gmail.com", true, trip)
        ));

        trip.setParticipantsList(participants);
        var tripPayload = new TripPayload(trip);

        var tripAtualizada = new Trip(null, "Sao paulo", LocalDateTime.parse("2024-08-25T18:00"), LocalDateTime.parse("2024-10-20T18:30"), true, "Joao", "joao@planner.com", null, null, null);

        when(repository.save(trip)).thenReturn(trip);
        when(participantsRepository.saveAll(participants)).thenReturn(participants);
        when(service.update(uuid, tripAtualizada)).thenReturn(trip);

        var response = mvc.perform(
                put("/trip/" + uuid)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content( objetoRequisicao.write(
                                tripPayload
                        ).getJson())
        ).andReturn().getResponse();

        var jsonEsperado = objetoReceber.write(
                new TripPayloadResponse(trip)
        ).getJson();

        Assertions.assertEquals(jsonEsperado, response.getContentAsString());

    }

    @Test
    @DisplayName("Deleta trips, Expera 204 de resposta")
    void deletaTrips() throws Exception {

        var uuid = UUID.randomUUID();

        var trip = new Trip(uuid, "Joao pessoa", LocalDateTime.now(), LocalDateTime.parse("2024-10-20T18:30"), true, "Joao", "joao@planner.com", null, null, null);

        var participants = new ArrayList<>(List.of(
                new Participants(null, "Pedro", "pedro@gmail.com", false, trip),
                new Participants(null, "Julia", "julia@gmail.com", true, trip),
                new Participants(null, "Valdir", "valdir@gmail.com", true, trip)
        ));

        trip.setParticipantsList(participants);

        when(repository.save(trip)).thenReturn(trip);
        when(participantsRepository.saveAll(participants)).thenReturn(participants);
        when(service.findById(uuid)).thenReturn(trip);
        doNothing().when(service).delete(uuid);

        var response = mvc.perform(
                delete("/trip/" + uuid)
        ).andReturn().getResponse();


        Assertions.assertEquals(204, response.getStatus());

    }

    @Test
    @DisplayName("Salva participants em uma trip")
    void saveParticipantsTrip() throws Exception {

        //Cria trip com id
        var uuid = UUID.randomUUID();
        var trip = new Trip(uuid, "Joao pessoa", LocalDateTime.now(), LocalDateTime.parse("2024-10-20T18:30"), true, "Joao", "joao@planner.com",null, null, null);


        //Salva trip e participant sem vinculos entre si
        when(repository.save(trip)).thenReturn(trip);

        //Mocka a chamado do findById é chamada nos métodos internos
        when(service.findById(uuid)).thenReturn(trip);

        //participant sem vinculo com a trip
        var participant = new Participants(null, "Pedro", "pedro@gmail.com", false, null);

        //Mocka a chamada desse método de Salvar participants em uma trip
        when(service.saveParticipantsInTrip(uuid, participant)).thenReturn(participant);
        when(participantsRepository.save(participant)).thenReturn(participant);

        //Chama requisição
        var response = mvc.perform(
                post("/trip/" + uuid + "/invite")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(participantsPayload.write(
                                new ParticipantsPayload(participant)
                        ).getJson())
        ).andReturn().getResponse();

        //Resposta json esperada
        var jsonEsperado = participantsPayloadResponse.write(
                new ParticipantsPayloadResponse(participant)
        ).getJson();

        //Verifica se está de acordo
        Assertions.assertEquals(jsonEsperado, response.getContentAsString());

    }



}
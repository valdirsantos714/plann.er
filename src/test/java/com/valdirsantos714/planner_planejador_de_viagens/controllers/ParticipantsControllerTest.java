package com.valdirsantos714.planner_planejador_de_viagens.controllers;

import com.valdirsantos714.planner_planejador_de_viagens.model.Participants;
import com.valdirsantos714.planner_planejador_de_viagens.payload.ParticipantsPayload;
import com.valdirsantos714.planner_planejador_de_viagens.payload.ParticipantsPayloadResponse;
import com.valdirsantos714.planner_planejador_de_viagens.repositories.ParticipantsRepository;
import com.valdirsantos714.planner_planejador_de_viagens.services.ParticipantsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ParticipantsControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ParticipantsRepository repository;

    @MockBean
    private ParticipantsService service;

    @Autowired
    private JacksonTester<ParticipantsPayload> participantsPayload;

    @Autowired
    private JacksonTester<ParticipantsPayloadResponse> participantsPayloadResponse;

    @Test
    @DisplayName("Salva participant")
    void salvaParticipant() throws Exception {

        //Cria participant
        var participant = new Participants(null, "Joao", "joao@gmail.com", true, null);

        //Cria payload do participant
        var payload = new ParticipantsPayload(participant);

        //Mocka o salvamento do participant
        when(repository.save(participant)).thenReturn(participant);

        //Faz requisição
        var response = mvc.perform(
                post("/participant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(participantsPayload.write(
                                payload
                        ).getJson())
        ).andReturn().getResponse();

        //Resposta esperada
        var jsonEsperado = participantsPayloadResponse.write(
                new ParticipantsPayloadResponse(participant)
        ).getJson();

        //Verifica a resposta
        Assertions.assertEquals(jsonEsperado, response.getContentAsString());
    }

    @Test
    @DisplayName("Atualiza participant")
    void atualizaParticipant() throws Exception {

        //Cria participant
        var uuid = UUID.randomUUID();
        var participant = new Participants(uuid, "Joao", "joao@gmail.com", true, null);

        var participantAtualizado = new Participants(null, "Joao", "joao@planner.com", false, null);

        //Cria payload do participant
        var payload = new ParticipantsPayload(participant);

        //Mocka o salvamento do participant e o findById
        when(repository.save(participant)).thenReturn(participant);
        when(service.findById(uuid)).thenReturn(participant);
        when(service.update(uuid, participantAtualizado)).thenReturn(participant);

        //Faz requisição
        var response = mvc.perform(
                put("/participant/" + uuid)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(participantsPayload.write(
                                payload
                        ).getJson())
        ).andReturn().getResponse();

        //Resposta esperada
        var jsonEsperado = participantsPayloadResponse.write(
                new ParticipantsPayloadResponse(participant)
        ).getJson();

        //Verifica a resposta
        Assertions.assertEquals(jsonEsperado, response.getContentAsString());
    }

    @Test
    @DisplayName("Deleta participant, espera 204 de resposta")
    void deletaParticipant() throws Exception {

        //Cria participant
        var uuid = UUID.randomUUID();
        var participant = new Participants(uuid, "Joao", "joao@gmail.com", true, null);

         //Mocka o salvamento do participant e o findById
        when(repository.save(participant)).thenReturn(participant);
        when(service.findById(uuid)).thenReturn(participant);
        doNothing().when(service).delete(uuid);

        //Faz requisição
        var response = mvc.perform(
                delete("/participant/" + uuid)
        ).andReturn().getResponse();

        //Verifica a resposta
        Assertions.assertEquals(204, response.getStatus());
    }

}
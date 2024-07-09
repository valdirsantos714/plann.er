package com.valdirsantos714.planner_planejador_de_viagens.model;

import com.valdirsantos714.planner_planejador_de_viagens.payload.ParticipantsPayload;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "participants")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Participants implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private Boolean is_confirmed;

    @ManyToOne
    @JoinColumn(name = "id_trip")
    private Trip trip;

    public Participants(ParticipantsPayload dto) {
        this.name = dto.name();
        this.email = dto.email();
        this.is_confirmed = dto.is_confirmed();
        this.trip = dto.trip();
    }
}

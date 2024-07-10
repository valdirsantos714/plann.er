package com.valdirsantos714.planner_planejador_de_viagens.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.valdirsantos714.planner_planejador_de_viagens.payload.TripPayload;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "trip")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Trip implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String destination;

    @Column(nullable = false)
    private LocalDateTime starts_at;

    @Column(nullable = false)
    private LocalDateTime ends_at;

    private boolean is_confirmed;

    @Column(nullable = false)
    private String owner_name;

    @Column(nullable = false)
    private String owner_email;

    @JsonIgnore
    @OneToMany(mappedBy = "trip")
    private List<Participants> participantsList = new ArrayList<>();

    public Trip(TripPayload dto) {
        this.destination = dto.destination();
        this.starts_at = dto.starts_at();
        this.ends_at = dto.ends_at();
        this.owner_name = dto.owner_name();
        this.owner_email = dto.owner_email();
        this.is_confirmed = dto.is_confirmed();
        this.participantsList = dto.participantsList();

    }

}

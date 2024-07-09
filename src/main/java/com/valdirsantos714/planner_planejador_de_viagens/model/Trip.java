package com.valdirsantos714.planner_planejador_de_viagens.model;

import com.valdirsantos714.planner_planejador_de_viagens.payload.TripPayload;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
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

    public Trip(TripPayload dto) {
        this.destination = dto.destination();
        this.starts_at = dto.starts_at();
        this.ends_at = dto.ends_at();
        this.owner_name = dto.owner_name();
        this.owner_email = dto.owner_email();
        this.is_confirmed = dto.is_confirmed();

    }
}

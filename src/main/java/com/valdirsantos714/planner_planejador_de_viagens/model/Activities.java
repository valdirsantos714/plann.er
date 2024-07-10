package com.valdirsantos714.planner_planejador_de_viagens.model;

import com.valdirsantos714.planner_planejador_de_viagens.payload.ActivitiesPayload;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "activities")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Activities implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private LocalDateTime occurs_at;

    @ManyToOne
    @JoinColumn(name = "id_trip")
    private Trip trip;

    public Activities(ActivitiesPayload dto) {
        this.title = dto.title();
        this.occurs_at = dto.occurs_at();
        this.trip = dto.trip();
    }
}

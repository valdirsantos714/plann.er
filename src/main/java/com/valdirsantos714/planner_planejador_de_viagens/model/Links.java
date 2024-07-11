package com.valdirsantos714.planner_planejador_de_viagens.model;

import com.valdirsantos714.planner_planejador_de_viagens.payload.LinksPayload;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "links")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Links implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String url;

    @ManyToOne
    @JoinColumn(name = "id_trip")
    private Trip trip;

    public Links(LinksPayload dto) {
        this.title = dto.title();
        this.url = dto.url();
        this.trip = dto.trip();
    }
}

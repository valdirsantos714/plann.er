package com.valdirsantos714.planner_planejador_de_viagens.repositories;

import com.valdirsantos714.planner_planejador_de_viagens.model.Links;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LinksRepository extends JpaRepository<Links, UUID> {
}

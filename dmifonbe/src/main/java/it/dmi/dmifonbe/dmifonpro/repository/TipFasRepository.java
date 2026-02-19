package it.dmi.dmifonbe.dmifonpro.repository;

import it.dmi.dmifonbe.dmifonpro.entities.ProTipfas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipFasRepository extends JpaRepository<ProTipfas, Integer> {
    boolean existsByDesfasEqualsIgnoreCase(String desfas);
}

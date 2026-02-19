package it.dmi.dmifonbe.dmifonamm.repository;

import it.dmi.dmifonbe.dmifonamm.entities.AmmEnt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnteRepository extends JpaRepository<AmmEnt, Integer> {

}

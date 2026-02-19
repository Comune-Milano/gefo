package it.dmi.dmifonbe.dmifonamm.repository;

import it.dmi.dmifonbe.dmifonamm.entities.AmmPar;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParametriRepository extends JpaRepository<AmmPar, Integer> {
    Optional<AmmPar> getAmmParByCodiceIgnoreCase(String codice);
}

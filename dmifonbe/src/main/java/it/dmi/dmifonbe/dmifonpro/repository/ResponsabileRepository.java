package it.dmi.dmifonbe.dmifonpro.repository;

import it.dmi.dmifonbe.dmifonpro.entities.ProRes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResponsabileRepository extends JpaRepository<ProRes, Integer> {}

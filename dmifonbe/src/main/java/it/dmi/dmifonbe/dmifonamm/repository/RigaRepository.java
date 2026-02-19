package it.dmi.dmifonbe.dmifonamm.repository;

import it.dmi.dmifonbe.dmifonamm.entities.AmmRig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RigaRepository extends JpaRepository<AmmRig, Integer> {}

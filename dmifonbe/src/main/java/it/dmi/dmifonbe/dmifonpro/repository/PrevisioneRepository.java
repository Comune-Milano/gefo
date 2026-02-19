package it.dmi.dmifonbe.dmifonpro.repository;

import it.dmi.dmifonbe.dmifonpro.entities.ProPre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface PrevisioneRepository extends JpaRepository<ProPre, Integer> {
    Optional<ProPre> findProPreByIdProAndDtapre(Long idPro, Date dtapre);
}

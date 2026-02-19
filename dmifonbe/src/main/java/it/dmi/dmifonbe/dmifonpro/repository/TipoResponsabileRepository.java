package it.dmi.dmifonbe.dmifonpro.repository;

import it.dmi.dmifonbe.dmifonpro.entities.ProTipres;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TipoResponsabileRepository extends JpaRepository<ProTipres, Integer> {
    List<ProTipres> findAllByOrderByOrdtipresAsc();
}

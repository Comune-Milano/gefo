package it.dmi.dmifonbe.dmifonpro.repository;

import it.dmi.dmifonbe.dmifonpro.entities.ProTipinfagg;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoInformazioneRepository extends JpaRepository<ProTipinfagg, Integer> {
    Page<ProTipinfagg> findAllByOrderByOrdtipinfagg(Pageable page);
}

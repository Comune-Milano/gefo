package it.dmi.dmifonbe.dmifonpro.repository;

import it.dmi.dmifonbe.dmifonpro.entities.ProTipinfagg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TipoInfoAggiuntiveRepository extends JpaRepository<ProTipinfagg, Integer> {
    List<ProTipinfagg> findAllByOrderByOrdtipinfaggAsc();
}

package it.dmi.dmifonbe.dmifonpro.repository;

import it.dmi.dmifonbe.dmifonpro.entities.ProInfagg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InfoAggiuntiveRepository extends JpaRepository<ProInfagg, Integer> {
    boolean existsByProTipinfaggByIdTipinfaggId(Integer id);
}

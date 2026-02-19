package it.dmi.dmifonbe.dmifonpro.repository;

import it.dmi.dmifonbe.dmifonpro.entities.ProDettipolfas;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DettippolfasRepository extends JpaRepository<ProDettipolfas, Integer> {
    List<ProDettipolfas> findByIdLisvaltipolfas(Long idLisValTipPolFas);
}

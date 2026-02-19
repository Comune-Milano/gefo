package it.dmi.dmifonbe.dmifonpro.repository;

import it.dmi.dmifonbe.dmifonpro.entities.ProRicute;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RicUteRepository extends JpaRepository<ProRicute, Integer> {
    List<ProRicute> findByIdRic(Long idRic);
}

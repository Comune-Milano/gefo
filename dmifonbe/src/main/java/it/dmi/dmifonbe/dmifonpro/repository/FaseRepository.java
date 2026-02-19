package it.dmi.dmifonbe.dmifonpro.repository;

import it.dmi.dmifonbe.dmifonpro.entities.ProFas;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FaseRepository extends JpaRepository<ProFas, Integer> {
    Optional<ProFas> findByIdAvaAndIdTipfas(Long idAva, Long idTipfas);
    List<ProFas> findByIdAva(Long idAva);
    boolean existsByProTipfasByIdTipfasId(Integer id);
}

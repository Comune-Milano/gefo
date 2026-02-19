package it.dmi.dmifonbe.dmifonamm.repository;

import it.dmi.dmifonbe.dmifonamm.entities.AmmAll;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AllegatoRepository extends JpaRepository<AmmAll, Integer> {
    List<AmmAll> findByIdentAndTipent(Long idEnt, String tipEnt);
    Optional<AmmAll> findByIdentAndTipentAndNomfil(Long idEnt, String tipEnt, String nomFil);
}

package it.dmi.dmifonbe.dmifonamm.repository;

import it.dmi.dmifonbe.dmifonamm.entities.AmmPer;
import it.dmi.dmifonbe.dmifonamm.entities.AmmUteruo;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AmmPerRepository extends JpaRepository<AmmPer, Integer> {
    List<AmmPer> findByIdRuo(Long idRuo);
}

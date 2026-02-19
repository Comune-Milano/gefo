package it.dmi.dmifonbe.dmifonamm.repository;

import it.dmi.dmifonbe.dmifonamm.entities.AmmUteruo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AmmUteRuoRepository extends JpaRepository<AmmUteruo, Integer> {
    AmmUteruo findByIdUteAndIdRuoAndIdDir(long idUte, long idRuo, long idDir);
}

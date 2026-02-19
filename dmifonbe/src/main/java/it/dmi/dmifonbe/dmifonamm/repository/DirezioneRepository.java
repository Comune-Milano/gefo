package it.dmi.dmifonbe.dmifonamm.repository;

import it.dmi.dmifonbe.dmifonamm.entities.AmmDir;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DirezioneRepository extends JpaRepository<AmmDir, Integer> {
    Optional<AmmDir> findByCoddirOrderByCoddir(String codDir);
    List<AmmDir> findByDesdir(String desDir);

    @Query(
        value = "SELECT d FROM AmmDir d WHERE upper(d.coddir) LIKE %:autocomplete% OR upper(d.desdir) LIKE %:autocomplete% order by d.coddir"
    )
    List<AmmDir> findAutocomplete(@Param(value = "autocomplete") String autocomplete);

    List<AmmDir> findAllByOrderByCoddirAsc();
}

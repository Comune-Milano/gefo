package it.dmi.dmifonbe.dmifonamm.repository;

import it.dmi.dmifonbe.dmifonamm.entities.AmmRuo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RuoloRepository extends JpaRepository<AmmRuo, Integer> {
    Optional<AmmRuo> findByCodruo(String codRuo);
    List<AmmRuo> findByDesruo(String desRuo);

    @Query(value = "SELECT r FROM AmmRuo r WHERE upper(r.codruo) LIKE :autocomplete% OR upper(r.desruo) LIKE :autocomplete%")
    List<AmmRuo> findAutocomplete(@Param(value = "autocomplete") String autocomplete);
}

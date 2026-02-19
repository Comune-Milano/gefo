package it.dmi.dmifonbe.dmifonpro.repository;

import it.dmi.dmifonbe.dmifonpro.entities.ProEntcon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EntConRepository extends JpaRepository<ProEntcon, Integer> {
    Optional<ProEntcon> findByCodentconAndTipent(String codentcon, String tipent);

    @Query(
        value = "SELECT ent FROM ProEntcon ent WHERE ent.tipent=:tipent AND (upper(ent.desimp) LIKE %:autocomplete% OR upper(ent.codentcon) LIKE %:autocomplete%) order by ent.desimp"
    )
    List<ProEntcon> findAutocomplete(@Param(value = "tipent") String tipent,@Param(value = "autocomplete") String autocomplete);

    List<ProEntcon> findAllByTipent(String tipent);

}

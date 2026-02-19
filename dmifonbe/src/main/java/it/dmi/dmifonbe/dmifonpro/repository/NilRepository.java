package it.dmi.dmifonbe.dmifonpro.repository;

import it.dmi.dmifonbe.dmifonpro.entities.ProNil;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NilRepository extends JpaRepository<ProNil, Integer> {
    @Query(
        value = "SELECT nil FROM ProNil nil WHERE upper(nil.codnil) LIKE %:autocomplete% OR upper(nil.desnil) LIKE %:autocomplete% order by nil.codnil"
    )
    List<ProNil> findAutocomplete(@Param(value = "autocomplete") String autocomplete);

    List<ProNil> findAllByOrderByDesnilAsc();

    Page<ProNil> findAllByOrderByCodnilAsc(Pageable page);

    boolean existsByCodnilEqualsIgnoreCase(String codnil);
}

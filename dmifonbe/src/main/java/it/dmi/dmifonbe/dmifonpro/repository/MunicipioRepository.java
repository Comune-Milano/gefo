package it.dmi.dmifonbe.dmifonpro.repository;

import it.dmi.dmifonbe.dmifonpro.entities.ProMun;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MunicipioRepository extends JpaRepository<ProMun, Integer> {
    Page<ProMun> findAllByOrderByDesmunAsc(Pageable page);
    List<ProMun> findAllByOrderByDesmunAsc();

    @Query(value = "SELECT mp FROM ProMun mp WHERE upper(mp.desmun) LIKE %:autocomplete% order by mp.desmun")
    List<ProMun> findAutocomplete(@Param(value = "autocomplete") String autocomplete);

    boolean existsByDesmunEqualsIgnoreCase(String desmun);
}

package it.dmi.dmifonbe.dmifonpro.repository;

import it.dmi.dmifonbe.dmifonpro.entities.ProPro;
import it.dmi.dmifonbe.dmifonpro.entities.ProRic;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RicRepository extends JpaRepository<ProRic, Integer> {
    @Query(value = "SELECT r FROM ProRic r WHERE (upper(r.desric) LIKE %:autocomplete%)")
    List<ProRic> findAutocomplete(@Param(value = "autocomplete") String autocomplete);

    @Query(value = "SELECT r FROM ProRic r, ProPro p WHERE p.id = r.idPro AND p.idDir = :idDir AND (upper(r.desric) LIKE %:autocomplete%)")
    List<ProRic> findAutocompleteByDir(@Param(value = "autocomplete") String autocomplete, @Param(value = "idDir") Integer idDir);
}

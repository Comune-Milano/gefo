package it.dmi.dmifonbe.dmifonpro.repository;

import it.dmi.dmifonbe.dmifonpro.entities.ProDdr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DdrRepository extends JpaRepository<ProDdr, Integer> {
    @Query(value = "SELECT d FROM ProDdr d WHERE upper(d.codddr) LIKE %:autocomplete% OR upper(d.desddr) LIKE %:autocomplete%")
    List<ProDdr> findAutocomplete(@Param(value = "autocomplete") String autocomplete);

    Optional<ProDdr> findProDdrByCodddrIgnoreCase(String codDdr);
}

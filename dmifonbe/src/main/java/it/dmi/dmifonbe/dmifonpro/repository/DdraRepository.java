package it.dmi.dmifonbe.dmifonpro.repository;

import it.dmi.dmifonbe.dmifonpro.entities.ProDdra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DdraRepository extends JpaRepository<ProDdra, Integer> {
    @Query(value = "SELECT d FROM ProDdra d WHERE upper(d.codddra) LIKE %:autocomplete% OR upper(d.desddra) LIKE %:autocomplete%")
    List<ProDdra> findAutocomplete(@Param(value = "autocomplete") String autocomplete);

    Optional<ProDdra> findProDdraByCodddraIgnoreCase(String codDdra);

    List<ProDdra> findAllByOrderByCodddraAsc();

}

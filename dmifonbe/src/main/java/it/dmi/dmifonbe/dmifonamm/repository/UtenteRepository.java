package it.dmi.dmifonbe.dmifonamm.repository;

import it.dmi.dmifonbe.dmifonamm.entities.AmmUte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UtenteRepository extends JpaRepository<AmmUte, Integer> {
    Optional<AmmUte> findByUsername(String username);
    List<AmmUte> findByCognome(String cognome);

    @Query(value = "SELECT u FROM AmmUte u WHERE upper(u.username) LIKE :autocomplete% OR upper(u.cognome) LIKE :autocomplete%")
    List<AmmUte> findAutocomplete(@Param(value = "autocomplete") String autocomplete);
}

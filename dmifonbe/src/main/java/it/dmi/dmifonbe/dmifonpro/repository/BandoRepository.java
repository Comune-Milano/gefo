package it.dmi.dmifonbe.dmifonpro.repository;

import it.dmi.dmifonbe.dmifonpro.entities.ProBan;
import it.dmi.dmifonbe.dmifonpro.entities.ProMacpro;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BandoRepository extends JpaRepository<ProBan, Integer> {
    Optional<ProBan> findProBanByCodbanIgnoreCase(String codBan);
    Page<ProBan> findAllByOrderByCodbanAsc(Pageable page);

    @Query(value = "SELECT b FROM ProBan b WHERE upper(b.codban) LIKE %:autocomplete% OR upper(b.desban) LIKE %:autocomplete%")
    List<ProBan> findAutocomplete(@Param(value = "autocomplete") String autocomplete);
}

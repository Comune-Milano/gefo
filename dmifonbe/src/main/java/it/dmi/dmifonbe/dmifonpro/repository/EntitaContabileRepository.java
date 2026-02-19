package it.dmi.dmifonbe.dmifonpro.repository;

import it.dmi.dmifonbe.dmifonpro.entities.ProEntcon;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntitaContabileRepository extends JpaRepository<ProEntcon, Integer> {
    Optional<ProEntcon> findByTipentAndCodentcon(String tipent, String codEntcon);
}

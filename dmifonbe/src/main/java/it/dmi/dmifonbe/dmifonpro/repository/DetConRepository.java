package it.dmi.dmifonbe.dmifonpro.repository;

import it.dmi.dmifonbe.dmifonpro.entities.ProDetcon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetConRepository extends JpaRepository<ProDetcon, Integer> {
    List<ProDetcon> findProDetconsByIdProAndTipentIgnoreCaseOrderByCodentcon(Long idPro, String tipent);
    List<ProDetcon> findProDetconsByIdProAndTipentIgnoreCaseAndCodentcon(Long idPro, String tipent, String codentCon);

}

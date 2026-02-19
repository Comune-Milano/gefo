package it.dmi.dmifonbe.dmifonpro.repository;

import it.dmi.dmifonbe.dmifonpro.entities.ProImp;
import it.dmi.dmifonbe.dmifonpro.entities.ProTipimp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImportoRepository extends JpaRepository<ProImp, Integer> {
    boolean existsByProTipimpByIdTipimpId(Integer id);
}

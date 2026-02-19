package it.dmi.dmifonbe.dmifonpro.repository;

import it.dmi.dmifonbe.dmifonpro.entities.ProLisval;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProLisValRepository extends JpaRepository<ProLisval, Integer> {
    List<ProLisval> findByTiplis(String tiplis);
}

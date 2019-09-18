package net.atos.bpm.repository;

import net.atos.bpm.domain.Deputy;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Deputy entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeputyRepository extends JpaRepository<Deputy, Long> {

}

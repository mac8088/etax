package net.atos.etax.repository;

import net.atos.etax.domain.StdCodesProp;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the StdCodesProp entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StdCodesPropRepository extends JpaRepository<StdCodesProp, Long> {

}

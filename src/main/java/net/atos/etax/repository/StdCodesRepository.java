package net.atos.etax.repository;

import net.atos.etax.domain.StdCodes;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the StdCodes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StdCodesRepository extends JpaRepository<StdCodes, Long> {

}

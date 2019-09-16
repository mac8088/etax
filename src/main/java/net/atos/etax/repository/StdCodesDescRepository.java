package net.atos.etax.repository;

import net.atos.etax.domain.StdCodesDesc;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the StdCodesDesc entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StdCodesDescRepository extends JpaRepository<StdCodesDesc, Long>, JpaSpecificationExecutor<StdCodesDesc> {

}

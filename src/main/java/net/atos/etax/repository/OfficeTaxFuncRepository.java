package net.atos.etax.repository;

import net.atos.etax.domain.OfficeTaxFunc;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the OfficeTaxFunc entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OfficeTaxFuncRepository extends JpaRepository<OfficeTaxFunc, Long> {

}

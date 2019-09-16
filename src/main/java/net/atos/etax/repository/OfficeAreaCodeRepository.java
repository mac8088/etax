package net.atos.etax.repository;

import net.atos.etax.domain.OfficeAreaCode;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the OfficeAreaCode entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OfficeAreaCodeRepository extends JpaRepository<OfficeAreaCode, Long> {

}

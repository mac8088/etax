package net.atos.etax.repository;

import net.atos.etax.domain.OfficeAreaCode;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the OfficeAreaCode entity.
 */
@Repository
public interface OfficeAreaCodeRepository extends JpaRepository<OfficeAreaCode, Long>{
    List<OfficeAreaCode> findAllByOfficeIdEquals(Integer officeId);
}

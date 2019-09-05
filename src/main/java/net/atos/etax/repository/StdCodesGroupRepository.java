package net.atos.etax.repository;

import net.atos.etax.domain.StdCodesGroup;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the StdCodesGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StdCodesGroupRepository extends JpaRepository<StdCodesGroup, Long>, JpaSpecificationExecutor<StdCodesGroup> {

}

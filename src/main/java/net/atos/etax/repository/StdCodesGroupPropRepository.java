package net.atos.etax.repository;

import net.atos.etax.domain.StdCodesGroupProp;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the StdCodesGroupProp entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StdCodesGroupPropRepository extends JpaRepository<StdCodesGroupProp, Long> {

}

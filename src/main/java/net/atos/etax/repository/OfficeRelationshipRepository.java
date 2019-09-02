package net.atos.etax.repository;

import net.atos.etax.domain.OfficeRelationship;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the OfficeRelationship entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OfficeRelationshipRepository extends JpaRepository<OfficeRelationship, Long> {

}

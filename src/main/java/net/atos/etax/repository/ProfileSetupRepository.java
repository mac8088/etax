package net.atos.etax.repository;

import net.atos.etax.domain.ProfileSetup;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProfileSetup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProfileSetupRepository extends JpaRepository<ProfileSetup, Long> {

}

package net.atos.bpm.repository;

import net.atos.bpm.domain.Deputy;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Deputy entity.
 */
@Repository
public interface DeputyRepository extends JpaRepository<Deputy, Long>, JpaSpecificationExecutor<Deputy> {
	List<Deputy> findByOwnerIdInAndPeriodFromLessThanEqualAndPeriodToGreaterThan(List<Long> owners, ZonedDateTime start, ZonedDateTime end);
}



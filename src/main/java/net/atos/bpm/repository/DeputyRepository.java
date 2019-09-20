package net.atos.bpm.repository;

import net.atos.bpm.domain.Deputy;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Deputy entity.
 */
@Repository
public interface DeputyRepository extends JpaRepository<Deputy, Long>, JpaSpecificationExecutor<Deputy> {
	List<Deputy> findByOwnerIdInAndPeriodFromLessThanEqualAndPeriodToGreaterThan(List<Long> owners, ZonedDateTime start, ZonedDateTime end);
	
	@Modifying(clearAutomatically = true)
	@Query("update DeputyToDo dr set dr.status = 0 where dr.taskId =:taskId")
	void clearStatus(@Param("taskId") String taskId);
	
	@Modifying(clearAutomatically = true)
	@Query("update DeputyToDo dr set dr.status = 1 where dr.taskId =:taskId and dr.assignorName=:assignorName")
	void markStatus(@Param("taskId") String taskId, @Param("assignorName") String assignorName);
}



package net.atos.bpm.repository;

import net.atos.bpm.domain.DeputyToDo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DeputyToDo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeputyToDoRepository extends JpaRepository<DeputyToDo, Long> {

}

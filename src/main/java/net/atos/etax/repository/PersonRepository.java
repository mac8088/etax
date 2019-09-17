package net.atos.etax.repository;

import net.atos.etax.domain.Person;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Person entity.
 */
@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
	Person findByUserName(String username);
}

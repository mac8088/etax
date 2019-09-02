package net.atos.etax.repository;

import net.atos.etax.domain.OfficeWeekday;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the OfficeWeekday entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OfficeWeekdayRepository extends JpaRepository<OfficeWeekday, Long> {

}

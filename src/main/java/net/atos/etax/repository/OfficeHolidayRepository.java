package net.atos.etax.repository;

import net.atos.etax.domain.OfficeHoliday;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the OfficeHoliday entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OfficeHolidayRepository extends JpaRepository<OfficeHoliday, Long> {

}

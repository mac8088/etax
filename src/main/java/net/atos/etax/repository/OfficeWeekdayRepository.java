package net.atos.etax.repository;

import net.atos.etax.domain.OfficeWeekday;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the OfficeWeekday entity.
 */
@Repository
public interface OfficeWeekdayRepository extends JpaRepository<OfficeWeekday, Long> {
    List<OfficeWeekday> findAllByOfficeIdEquals(Integer officeId);
}

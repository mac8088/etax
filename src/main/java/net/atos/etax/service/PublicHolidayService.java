package net.atos.etax.service;

import net.atos.etax.domain.PublicHoliday;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link PublicHoliday}.
 */
public interface PublicHolidayService {

    /**
     * Save a publicHoliday.
     *
     * @param publicHoliday the entity to save.
     * @return the persisted entity.
     */
    PublicHoliday save(PublicHoliday publicHoliday);

    /**
     * Get all the publicHolidays.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PublicHoliday> findAll(Pageable pageable);


    /**
     * Get the "id" publicHoliday.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PublicHoliday> findOne(Long id);

    /**
     * Delete the "id" publicHoliday.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

package net.atos.etax.service;

import net.atos.etax.domain.Office;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Office}.
 */
public interface OfficeService {

    /**
     * Save a office.
     *
     * @param office the entity to save.
     * @return the persisted entity.
     */
    Office save(Office office);

    /**
     * Get all the offices.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Office> findAll(Pageable pageable);


    /**
     * Get the "id" office.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Office> findOne(Long id);

    /**
     * Delete the "id" office.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

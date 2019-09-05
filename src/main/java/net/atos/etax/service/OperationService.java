package net.atos.etax.service;

import net.atos.etax.domain.Operation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Operation}.
 */
public interface OperationService {

    /**
     * Save a operation.
     *
     * @param operation the entity to save.
     * @return the persisted entity.
     */
    Operation save(Operation operation);

    /**
     * Get all the operations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Operation> findAll(Pageable pageable);

    /**
     * Get all the operations with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<Operation> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" operation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Operation> findOne(Long id);

    /**
     * Delete the "id" operation.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

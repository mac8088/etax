package net.atos.etax.service;

import net.atos.etax.domain.Uiapp;
import net.atos.etax.repository.UiappRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Uiapp}.
 */
@Service
@Transactional
public class UiappService {

    private final Logger log = LoggerFactory.getLogger(UiappService.class);

    private final UiappRepository uiappRepository;

    public UiappService(UiappRepository uiappRepository) {
        this.uiappRepository = uiappRepository;
    }

    /**
     * Save a uiapp.
     *
     * @param uiapp the entity to save.
     * @return the persisted entity.
     */
    public Uiapp save(Uiapp uiapp) {
        log.debug("Request to save Uiapp : {}", uiapp);
        return uiappRepository.save(uiapp);
    }

    /**
     * Get all the uiapps.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Uiapp> findAll(Pageable pageable) {
        log.debug("Request to get all Uiapps");
        return uiappRepository.findAll(pageable);
    }

    /**
     * Get all the uiapps with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<Uiapp> findAllWithEagerRelationships(Pageable pageable) {
        return uiappRepository.findAllWithEagerRelationships(pageable);
    }
    

    /**
     * Get one uiapp by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Uiapp> findOne(Long id) {
        log.debug("Request to get Uiapp : {}", id);
        return uiappRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the uiapp by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Uiapp : {}", id);
        uiappRepository.deleteById(id);
    }
}

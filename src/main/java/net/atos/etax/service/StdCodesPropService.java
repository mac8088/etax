package net.atos.etax.service;

import net.atos.etax.domain.StdCodesProp;
import net.atos.etax.repository.StdCodesPropRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link StdCodesProp}.
 */
@Service
@Transactional
public class StdCodesPropService {

    private final Logger log = LoggerFactory.getLogger(StdCodesPropService.class);

    private final StdCodesPropRepository stdCodesPropRepository;

    public StdCodesPropService(StdCodesPropRepository stdCodesPropRepository) {
        this.stdCodesPropRepository = stdCodesPropRepository;
    }

    /**
     * Save a stdCodesProp.
     *
     * @param stdCodesProp the entity to save.
     * @return the persisted entity.
     */
    public StdCodesProp save(StdCodesProp stdCodesProp) {
        log.debug("Request to save StdCodesProp : {}", stdCodesProp);
        return stdCodesPropRepository.save(stdCodesProp);
    }

    /**
     * Get all the stdCodesProps.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<StdCodesProp> findAll(Pageable pageable) {
        log.debug("Request to get all StdCodesProps");
        return stdCodesPropRepository.findAll(pageable);
    }


    /**
     * Get one stdCodesProp by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<StdCodesProp> findOne(Long id) {
        log.debug("Request to get StdCodesProp : {}", id);
        return stdCodesPropRepository.findById(id);
    }

    /**
     * Delete the stdCodesProp by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete StdCodesProp : {}", id);
        stdCodesPropRepository.deleteById(id);
    }
}

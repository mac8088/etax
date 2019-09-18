package net.atos.bpm.service;

import net.atos.bpm.domain.Deputy;
import net.atos.bpm.repository.DeputyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Deputy}.
 */
@Service
@Transactional
public class DeputyService {

    private final Logger log = LoggerFactory.getLogger(DeputyService.class);

    private final DeputyRepository deputyRepository;

    public DeputyService(DeputyRepository deputyRepository) {
        this.deputyRepository = deputyRepository;
    }

    /**
     * Save a deputy.
     *
     * @param deputy the entity to save.
     * @return the persisted entity.
     */
    public Deputy save(Deputy deputy) {
        log.debug("Request to save Deputy : {}", deputy);
        return deputyRepository.save(deputy);
    }

    /**
     * Get all the deputies.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Deputy> findAll(Pageable pageable) {
        log.debug("Request to get all Deputies");
        return deputyRepository.findAll(pageable);
    }


    /**
     * Get one deputy by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Deputy> findOne(Long id) {
        log.debug("Request to get Deputy : {}", id);
        return deputyRepository.findById(id);
    }

    /**
     * Delete the deputy by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Deputy : {}", id);
        deputyRepository.deleteById(id);
    }
}

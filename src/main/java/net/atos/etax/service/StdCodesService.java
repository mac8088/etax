package net.atos.etax.service;

import net.atos.etax.domain.StdCodes;
import net.atos.etax.repository.StdCodesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link StdCodes}.
 */
@Service
@Transactional
public class StdCodesService {

    private final Logger log = LoggerFactory.getLogger(StdCodesService.class);

    private final StdCodesRepository stdCodesRepository;

    public StdCodesService(StdCodesRepository stdCodesRepository) {
        this.stdCodesRepository = stdCodesRepository;
    }

    /**
     * Save a stdCodes.
     *
     * @param stdCodes the entity to save.
     * @return the persisted entity.
     */
    public StdCodes save(StdCodes stdCodes) {
        log.debug("Request to save StdCodes : {}", stdCodes);
        return stdCodesRepository.save(stdCodes);
    }

    /**
     * Get all the stdCodes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<StdCodes> findAll(Pageable pageable) {
        log.debug("Request to get all StdCodes");
        return stdCodesRepository.findAll(pageable);
    }


    /**
     * Get one stdCodes by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<StdCodes> findOne(Long id) {
        log.debug("Request to get StdCodes : {}", id);
        return stdCodesRepository.findById(id);
    }

    /**
     * Delete the stdCodes by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete StdCodes : {}", id);
        stdCodesRepository.deleteById(id);
    }
}

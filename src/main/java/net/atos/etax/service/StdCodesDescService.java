package net.atos.etax.service;

import net.atos.etax.domain.StdCodesDesc;
import net.atos.etax.repository.StdCodesDescRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link StdCodesDesc}.
 */
@Service
@Transactional
public class StdCodesDescService {

    private final Logger log = LoggerFactory.getLogger(StdCodesDescService.class);

    private final StdCodesDescRepository stdCodesDescRepository;

    public StdCodesDescService(StdCodesDescRepository stdCodesDescRepository) {
        this.stdCodesDescRepository = stdCodesDescRepository;
    }

    /**
     * Save a stdCodesDesc.
     *
     * @param stdCodesDesc the entity to save.
     * @return the persisted entity.
     */
    public StdCodesDesc save(StdCodesDesc stdCodesDesc) {
        log.debug("Request to save StdCodesDesc : {}", stdCodesDesc);
        return stdCodesDescRepository.save(stdCodesDesc);
    }

    /**
     * Get all the stdCodesDescs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<StdCodesDesc> findAll(Pageable pageable) {
        log.debug("Request to get all StdCodesDescs");
        return stdCodesDescRepository.findAll(pageable);
    }


    /**
     * Get one stdCodesDesc by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<StdCodesDesc> findOne(Long id) {
        log.debug("Request to get StdCodesDesc : {}", id);
        return stdCodesDescRepository.findById(id);
    }

    /**
     * Delete the stdCodesDesc by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete StdCodesDesc : {}", id);
        stdCodesDescRepository.deleteById(id);
    }
}

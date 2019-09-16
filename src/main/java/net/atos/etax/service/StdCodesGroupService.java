package net.atos.etax.service;

import net.atos.etax.domain.StdCodesGroup;
import net.atos.etax.repository.StdCodesGroupRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link StdCodesGroup}.
 */
@Service
@Transactional
public class StdCodesGroupService {

    private final Logger log = LoggerFactory.getLogger(StdCodesGroupService.class);

    private final StdCodesGroupRepository stdCodesGroupRepository;

    public StdCodesGroupService(StdCodesGroupRepository stdCodesGroupRepository) {
        this.stdCodesGroupRepository = stdCodesGroupRepository;
    }

    /**
     * Save a stdCodesGroup.
     *
     * @param stdCodesGroup the entity to save.
     * @return the persisted entity.
     */
    public StdCodesGroup save(StdCodesGroup stdCodesGroup) {
        log.debug("Request to save StdCodesGroup : {}", stdCodesGroup);
        return stdCodesGroupRepository.save(stdCodesGroup);
    }

    /**
     * Get all the stdCodesGroups.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<StdCodesGroup> findAll(Pageable pageable) {
        log.debug("Request to get all StdCodesGroups");
        return stdCodesGroupRepository.findAll(pageable);
    }


    /**
     * Get one stdCodesGroup by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<StdCodesGroup> findOne(Long id) {
        log.debug("Request to get StdCodesGroup : {}", id);
        return stdCodesGroupRepository.findById(id);
    }

    /**
     * Delete the stdCodesGroup by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete StdCodesGroup : {}", id);
        stdCodesGroupRepository.deleteById(id);
    }
}

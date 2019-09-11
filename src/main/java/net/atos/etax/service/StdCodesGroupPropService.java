package net.atos.etax.service;

import net.atos.etax.domain.StdCodesGroupProp;
import net.atos.etax.repository.StdCodesGroupPropRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link StdCodesGroupProp}.
 */
@Service
@Transactional
public class StdCodesGroupPropService {

    private final Logger log = LoggerFactory.getLogger(StdCodesGroupPropService.class);

    private final StdCodesGroupPropRepository stdCodesGroupPropRepository;

    public StdCodesGroupPropService(StdCodesGroupPropRepository stdCodesGroupPropRepository) {
        this.stdCodesGroupPropRepository = stdCodesGroupPropRepository;
    }

    /**
     * Save a stdCodesGroupProp.
     *
     * @param stdCodesGroupProp the entity to save.
     * @return the persisted entity.
     */
    public StdCodesGroupProp save(StdCodesGroupProp stdCodesGroupProp) {
        log.debug("Request to save StdCodesGroupProp : {}", stdCodesGroupProp);
        return stdCodesGroupPropRepository.save(stdCodesGroupProp);
    }

    /**
     * Get all the stdCodesGroupProps.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<StdCodesGroupProp> findAll(Pageable pageable) {
        log.debug("Request to get all StdCodesGroupProps");
        return stdCodesGroupPropRepository.findAll(pageable);
    }


    /**
     * Get one stdCodesGroupProp by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<StdCodesGroupProp> findOne(Long id) {
        log.debug("Request to get StdCodesGroupProp : {}", id);
        return stdCodesGroupPropRepository.findById(id);
    }

    /**
     * Delete the stdCodesGroupProp by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete StdCodesGroupProp : {}", id);
        stdCodesGroupPropRepository.deleteById(id);
    }
}

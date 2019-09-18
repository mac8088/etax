package net.atos.bpm.service;

import net.atos.bpm.domain.DeputyToDo;
import net.atos.bpm.repository.DeputyToDoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link DeputyToDo}.
 */
@Service
@Transactional
public class DeputyToDoService {

    private final Logger log = LoggerFactory.getLogger(DeputyToDoService.class);

    private final DeputyToDoRepository deputyToDoRepository;

    public DeputyToDoService(DeputyToDoRepository deputyToDoRepository) {
        this.deputyToDoRepository = deputyToDoRepository;
    }

    /**
     * Save a deputyToDo.
     *
     * @param deputyToDo the entity to save.
     * @return the persisted entity.
     */
    public DeputyToDo save(DeputyToDo deputyToDo) {
        log.debug("Request to save DeputyToDo : {}", deputyToDo);
        return deputyToDoRepository.save(deputyToDo);
    }

    /**
     * Get all the deputyToDos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DeputyToDo> findAll(Pageable pageable) {
        log.debug("Request to get all DeputyToDos");
        return deputyToDoRepository.findAll(pageable);
    }


    /**
     * Get one deputyToDo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DeputyToDo> findOne(Long id) {
        log.debug("Request to get DeputyToDo : {}", id);
        return deputyToDoRepository.findById(id);
    }

    /**
     * Delete the deputyToDo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DeputyToDo : {}", id);
        deputyToDoRepository.deleteById(id);
    }
}

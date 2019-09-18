package net.atos.bpm.web.rest;

import net.atos.bpm.domain.DeputyToDo;
import net.atos.bpm.repository.DeputyToDoRepository;
import net.atos.etax.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link net.atos.etax.domain.DeputyToDo}.
 */
@RestController
@RequestMapping("/api")
public class DeputyToDoResource {

    private final Logger log = LoggerFactory.getLogger(DeputyToDoResource.class);

    private static final String ENTITY_NAME = "deputyToDo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DeputyToDoRepository deputyToDoRepository;

    public DeputyToDoResource(DeputyToDoRepository deputyToDoRepository) {
        this.deputyToDoRepository = deputyToDoRepository;
    }

    /**
     * {@code POST  /deputy-to-dos} : Create a new deputyToDo.
     *
     * @param deputyToDo the deputyToDo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new deputyToDo, or with status {@code 400 (Bad Request)} if the deputyToDo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/deputy-to-dos")
    public ResponseEntity<DeputyToDo> createDeputyToDo(@RequestBody DeputyToDo deputyToDo) throws URISyntaxException {
        log.debug("REST request to save DeputyToDo : {}", deputyToDo);
        if (deputyToDo.getId() != null) {
            throw new BadRequestAlertException("A new deputyToDo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DeputyToDo result = deputyToDoRepository.save(deputyToDo);
        return ResponseEntity.created(new URI("/api/deputy-to-dos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /deputy-to-dos} : Updates an existing deputyToDo.
     *
     * @param deputyToDo the deputyToDo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated deputyToDo,
     * or with status {@code 400 (Bad Request)} if the deputyToDo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the deputyToDo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/deputy-to-dos")
    public ResponseEntity<DeputyToDo> updateDeputyToDo(@RequestBody DeputyToDo deputyToDo) throws URISyntaxException {
        log.debug("REST request to update DeputyToDo : {}", deputyToDo);
        if (deputyToDo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DeputyToDo result = deputyToDoRepository.save(deputyToDo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, deputyToDo.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /deputy-to-dos} : get all the deputyToDos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of deputyToDos in body.
     */
    @GetMapping("/deputy-to-dos")
    public List<DeputyToDo> getAllDeputyToDos() {
        log.debug("REST request to get all DeputyToDos");
        return deputyToDoRepository.findAll();
    }

    /**
     * {@code GET  /deputy-to-dos/:id} : get the "id" deputyToDo.
     *
     * @param id the id of the deputyToDo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the deputyToDo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/deputy-to-dos/{id}")
    public ResponseEntity<DeputyToDo> getDeputyToDo(@PathVariable Long id) {
        log.debug("REST request to get DeputyToDo : {}", id);
        Optional<DeputyToDo> deputyToDo = deputyToDoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(deputyToDo);
    }

    /**
     * {@code DELETE  /deputy-to-dos/:id} : delete the "id" deputyToDo.
     *
     * @param id the id of the deputyToDo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/deputy-to-dos/{id}")
    public ResponseEntity<Void> deleteDeputyToDo(@PathVariable Long id) {
        log.debug("REST request to delete DeputyToDo : {}", id);
        deputyToDoRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

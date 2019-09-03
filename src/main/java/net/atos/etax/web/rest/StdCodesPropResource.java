package net.atos.etax.web.rest;

import net.atos.etax.domain.StdCodesProp;
import net.atos.etax.repository.StdCodesPropRepository;
import net.atos.etax.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link net.atos.etax.domain.StdCodesProp}.
 */
@RestController
@RequestMapping("/api")
public class StdCodesPropResource {

    private final Logger log = LoggerFactory.getLogger(StdCodesPropResource.class);

    private static final String ENTITY_NAME = "stdCodesProp";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StdCodesPropRepository stdCodesPropRepository;

    public StdCodesPropResource(StdCodesPropRepository stdCodesPropRepository) {
        this.stdCodesPropRepository = stdCodesPropRepository;
    }

    /**
     * {@code POST  /std-codes-props} : Create a new stdCodesProp.
     *
     * @param stdCodesProp the stdCodesProp to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new stdCodesProp, or with status {@code 400 (Bad Request)} if the stdCodesProp has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/std-codes-props")
    public ResponseEntity<StdCodesProp> createStdCodesProp(@Valid @RequestBody StdCodesProp stdCodesProp) throws URISyntaxException {
        log.debug("REST request to save StdCodesProp : {}", stdCodesProp);
        if (stdCodesProp.getId() != null) {
            throw new BadRequestAlertException("A new stdCodesProp cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StdCodesProp result = stdCodesPropRepository.save(stdCodesProp);
        return ResponseEntity.created(new URI("/api/std-codes-props/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /std-codes-props} : Updates an existing stdCodesProp.
     *
     * @param stdCodesProp the stdCodesProp to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stdCodesProp,
     * or with status {@code 400 (Bad Request)} if the stdCodesProp is not valid,
     * or with status {@code 500 (Internal Server Error)} if the stdCodesProp couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/std-codes-props")
    public ResponseEntity<StdCodesProp> updateStdCodesProp(@Valid @RequestBody StdCodesProp stdCodesProp) throws URISyntaxException {
        log.debug("REST request to update StdCodesProp : {}", stdCodesProp);
        if (stdCodesProp.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StdCodesProp result = stdCodesPropRepository.save(stdCodesProp);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, stdCodesProp.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /std-codes-props} : get all the stdCodesProps.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stdCodesProps in body.
     */
    @GetMapping("/std-codes-props")
    public List<StdCodesProp> getAllStdCodesProps() {
        log.debug("REST request to get all StdCodesProps");
        return stdCodesPropRepository.findAll();
    }

    /**
     * {@code GET  /std-codes-props/:id} : get the "id" stdCodesProp.
     *
     * @param id the id of the stdCodesProp to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the stdCodesProp, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/std-codes-props/{id}")
    public ResponseEntity<StdCodesProp> getStdCodesProp(@PathVariable Long id) {
        log.debug("REST request to get StdCodesProp : {}", id);
        Optional<StdCodesProp> stdCodesProp = stdCodesPropRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(stdCodesProp);
    }

    /**
     * {@code DELETE  /std-codes-props/:id} : delete the "id" stdCodesProp.
     *
     * @param id the id of the stdCodesProp to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/std-codes-props/{id}")
    public ResponseEntity<Void> deleteStdCodesProp(@PathVariable Long id) {
        log.debug("REST request to delete StdCodesProp : {}", id);
        stdCodesPropRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

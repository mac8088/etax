package net.atos.etax.web.rest;

import net.atos.etax.domain.OfficeRelationship;
import net.atos.etax.repository.OfficeRelationshipRepository;
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
 * REST controller for managing {@link net.atos.etax.domain.OfficeRelationship}.
 */
@RestController
@RequestMapping("/api")
public class OfficeRelationshipResource {

    private final Logger log = LoggerFactory.getLogger(OfficeRelationshipResource.class);

    private static final String ENTITY_NAME = "officeRelationship";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OfficeRelationshipRepository officeRelationshipRepository;

    public OfficeRelationshipResource(OfficeRelationshipRepository officeRelationshipRepository) {
        this.officeRelationshipRepository = officeRelationshipRepository;
    }

    /**
     * {@code POST  /office-relationships} : Create a new officeRelationship.
     *
     * @param officeRelationship the officeRelationship to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new officeRelationship, or with status {@code 400 (Bad Request)} if the officeRelationship has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/office-relationships")
    public ResponseEntity<OfficeRelationship> createOfficeRelationship(@Valid @RequestBody OfficeRelationship officeRelationship) throws URISyntaxException {
        log.debug("REST request to save OfficeRelationship : {}", officeRelationship);
        if (officeRelationship.getId() != null) {
            throw new BadRequestAlertException("A new officeRelationship cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OfficeRelationship result = officeRelationshipRepository.save(officeRelationship);
        return ResponseEntity.created(new URI("/api/office-relationships/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /office-relationships} : Updates an existing officeRelationship.
     *
     * @param officeRelationship the officeRelationship to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated officeRelationship,
     * or with status {@code 400 (Bad Request)} if the officeRelationship is not valid,
     * or with status {@code 500 (Internal Server Error)} if the officeRelationship couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/office-relationships")
    public ResponseEntity<OfficeRelationship> updateOfficeRelationship(@Valid @RequestBody OfficeRelationship officeRelationship) throws URISyntaxException {
        log.debug("REST request to update OfficeRelationship : {}", officeRelationship);
        if (officeRelationship.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OfficeRelationship result = officeRelationshipRepository.save(officeRelationship);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, officeRelationship.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /office-relationships} : get all the officeRelationships.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of officeRelationships in body.
     */
    @GetMapping("/office-relationships")
    public List<OfficeRelationship> getAllOfficeRelationships() {
        log.debug("REST request to get all OfficeRelationships");
        return officeRelationshipRepository.findAll();
    }

    /**
     * {@code GET  /office-relationships/:id} : get the "id" officeRelationship.
     *
     * @param id the id of the officeRelationship to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the officeRelationship, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/office-relationships/{id}")
    public ResponseEntity<OfficeRelationship> getOfficeRelationship(@PathVariable Long id) {
        log.debug("REST request to get OfficeRelationship : {}", id);
        Optional<OfficeRelationship> officeRelationship = officeRelationshipRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(officeRelationship);
    }

    /**
     * {@code DELETE  /office-relationships/:id} : delete the "id" officeRelationship.
     *
     * @param id the id of the officeRelationship to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/office-relationships/{id}")
    public ResponseEntity<Void> deleteOfficeRelationship(@PathVariable Long id) {
        log.debug("REST request to delete OfficeRelationship : {}", id);
        officeRelationshipRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

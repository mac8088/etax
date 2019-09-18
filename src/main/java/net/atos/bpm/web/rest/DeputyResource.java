package net.atos.bpm.web.rest;

import net.atos.bpm.domain.Deputy;
import net.atos.bpm.service.DeputyService;
import net.atos.bpm.web.rest.errors.BadRequestAlertException;
import net.atos.bpm.service.dto.DeputyCriteria;
import net.atos.bpm.service.DeputyQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link net.atos.etax.domain.Deputy}.
 */
@RestController
@RequestMapping("/api")
public class DeputyResource {

    private final Logger log = LoggerFactory.getLogger(DeputyResource.class);

    private static final String ENTITY_NAME = "deputy";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DeputyService deputyService;

    private final DeputyQueryService deputyQueryService;

    public DeputyResource(DeputyService deputyService, DeputyQueryService deputyQueryService) {
        this.deputyService = deputyService;
        this.deputyQueryService = deputyQueryService;
    }

    /**
     * {@code POST  /deputies} : Create a new deputy.
     *
     * @param deputy the deputy to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new deputy, or with status {@code 400 (Bad Request)} if the deputy has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/deputies")
    public ResponseEntity<Deputy> createDeputy(@RequestBody Deputy deputy) throws URISyntaxException {
        log.debug("REST request to save Deputy : {}", deputy);
        if (deputy.getId() != null) {
            throw new BadRequestAlertException("A new deputy cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Deputy result = deputyService.save(deputy);
        return ResponseEntity.created(new URI("/api/deputies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /deputies} : Updates an existing deputy.
     *
     * @param deputy the deputy to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated deputy,
     * or with status {@code 400 (Bad Request)} if the deputy is not valid,
     * or with status {@code 500 (Internal Server Error)} if the deputy couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/deputies")
    public ResponseEntity<Deputy> updateDeputy(@RequestBody Deputy deputy) throws URISyntaxException {
        log.debug("REST request to update Deputy : {}", deputy);
        if (deputy.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Deputy result = deputyService.save(deputy);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, deputy.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /deputies} : get all the deputies.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of deputies in body.
     */
    @GetMapping("/deputies")
    public ResponseEntity<List<Deputy>> getAllDeputies(DeputyCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get Deputies by criteria: {}", criteria);
        Page<Deputy> page = deputyQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /deputies/count} : count all the deputies.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/deputies/count")
    public ResponseEntity<Long> countDeputies(DeputyCriteria criteria) {
        log.debug("REST request to count Deputies by criteria: {}", criteria);
        return ResponseEntity.ok().body(deputyQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /deputies/:id} : get the "id" deputy.
     *
     * @param id the id of the deputy to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the deputy, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/deputies/{id}")
    public ResponseEntity<Deputy> getDeputy(@PathVariable Long id) {
        log.debug("REST request to get Deputy : {}", id);
        Optional<Deputy> deputy = deputyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(deputy);
    }

    /**
     * {@code DELETE  /deputies/:id} : delete the "id" deputy.
     *
     * @param id the id of the deputy to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/deputies/{id}")
    public ResponseEntity<Void> deleteDeputy(@PathVariable Long id) {
        log.debug("REST request to delete Deputy : {}", id);
        deputyService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

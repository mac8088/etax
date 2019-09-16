package net.atos.etax.web.rest;

import net.atos.etax.domain.StdCodes;
import net.atos.etax.service.StdCodesService;
import net.atos.etax.web.rest.errors.BadRequestAlertException;
import net.atos.etax.service.dto.StdCodesCriteria;
import net.atos.etax.service.StdCodesQueryService;

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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link net.atos.etax.domain.StdCodes}.
 */
@RestController
@RequestMapping("/api")
public class StdCodesResource {

    private final Logger log = LoggerFactory.getLogger(StdCodesResource.class);

    private static final String ENTITY_NAME = "stdCodes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StdCodesService stdCodesService;

    private final StdCodesQueryService stdCodesQueryService;

    public StdCodesResource(StdCodesService stdCodesService, StdCodesQueryService stdCodesQueryService) {
        this.stdCodesService = stdCodesService;
        this.stdCodesQueryService = stdCodesQueryService;
    }

    /**
     * {@code POST  /std-codes} : Create a new stdCodes.
     *
     * @param stdCodes the stdCodes to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new stdCodes, or with status {@code 400 (Bad Request)} if the stdCodes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/std-codes")
    public ResponseEntity<StdCodes> createStdCodes(@Valid @RequestBody StdCodes stdCodes) throws URISyntaxException {
        log.debug("REST request to save StdCodes : {}", stdCodes);
        if (stdCodes.getId() != null) {
            throw new BadRequestAlertException("A new stdCodes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StdCodes result = stdCodesService.save(stdCodes);
        return ResponseEntity.created(new URI("/api/std-codes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /std-codes} : Updates an existing stdCodes.
     *
     * @param stdCodes the stdCodes to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stdCodes,
     * or with status {@code 400 (Bad Request)} if the stdCodes is not valid,
     * or with status {@code 500 (Internal Server Error)} if the stdCodes couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/std-codes")
    public ResponseEntity<StdCodes> updateStdCodes(@Valid @RequestBody StdCodes stdCodes) throws URISyntaxException {
        log.debug("REST request to update StdCodes : {}", stdCodes);
        if (stdCodes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StdCodes result = stdCodesService.save(stdCodes);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, stdCodes.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /std-codes} : get all the stdCodes.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stdCodes in body.
     */
    @GetMapping("/std-codes")
    public ResponseEntity<List<StdCodes>> getAllStdCodes(StdCodesCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get StdCodes by criteria: {}", criteria);
        Page<StdCodes> page = stdCodesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /std-codes/count} : count all the stdCodes.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/std-codes/count")
    public ResponseEntity<Long> countStdCodes(StdCodesCriteria criteria) {
        log.debug("REST request to count StdCodes by criteria: {}", criteria);
        return ResponseEntity.ok().body(stdCodesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /std-codes/:id} : get the "id" stdCodes.
     *
     * @param id the id of the stdCodes to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the stdCodes, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/std-codes/{id}")
    public ResponseEntity<StdCodes> getStdCodes(@PathVariable Long id) {
        log.debug("REST request to get StdCodes : {}", id);
        Optional<StdCodes> stdCodes = stdCodesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(stdCodes);
    }

    /**
     * {@code DELETE  /std-codes/:id} : delete the "id" stdCodes.
     *
     * @param id the id of the stdCodes to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/std-codes/{id}")
    public ResponseEntity<Void> deleteStdCodes(@PathVariable Long id) {
        log.debug("REST request to delete StdCodes : {}", id);
        stdCodesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

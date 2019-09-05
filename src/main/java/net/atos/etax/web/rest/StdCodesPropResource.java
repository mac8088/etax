package net.atos.etax.web.rest;

import net.atos.etax.domain.StdCodesProp;
import net.atos.etax.service.StdCodesPropService;
import net.atos.etax.web.rest.errors.BadRequestAlertException;
import net.atos.etax.service.dto.StdCodesPropCriteria;
import net.atos.etax.service.StdCodesPropQueryService;

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
 * REST controller for managing {@link net.atos.etax.domain.StdCodesProp}.
 */
@RestController
@RequestMapping("/api")
public class StdCodesPropResource {

    private final Logger log = LoggerFactory.getLogger(StdCodesPropResource.class);

    private static final String ENTITY_NAME = "stdCodesProp";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StdCodesPropService stdCodesPropService;

    private final StdCodesPropQueryService stdCodesPropQueryService;

    public StdCodesPropResource(StdCodesPropService stdCodesPropService, StdCodesPropQueryService stdCodesPropQueryService) {
        this.stdCodesPropService = stdCodesPropService;
        this.stdCodesPropQueryService = stdCodesPropQueryService;
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
        StdCodesProp result = stdCodesPropService.save(stdCodesProp);
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
        StdCodesProp result = stdCodesPropService.save(stdCodesProp);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, stdCodesProp.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /std-codes-props} : get all the stdCodesProps.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stdCodesProps in body.
     */
    @GetMapping("/std-codes-props")
    public ResponseEntity<List<StdCodesProp>> getAllStdCodesProps(StdCodesPropCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get StdCodesProps by criteria: {}", criteria);
        Page<StdCodesProp> page = stdCodesPropQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /std-codes-props/count} : count all the stdCodesProps.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/std-codes-props/count")
    public ResponseEntity<Long> countStdCodesProps(StdCodesPropCriteria criteria) {
        log.debug("REST request to count StdCodesProps by criteria: {}", criteria);
        return ResponseEntity.ok().body(stdCodesPropQueryService.countByCriteria(criteria));
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
        Optional<StdCodesProp> stdCodesProp = stdCodesPropService.findOne(id);
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
        stdCodesPropService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

package net.atos.etax.web.rest;

import net.atos.etax.domain.StdCodesGroupProp;
import net.atos.etax.service.StdCodesGroupPropService;
import net.atos.etax.web.rest.errors.BadRequestAlertException;
import net.atos.etax.service.dto.StdCodesGroupPropCriteria;
import net.atos.etax.service.StdCodesGroupPropQueryService;

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
 * REST controller for managing {@link net.atos.etax.domain.StdCodesGroupProp}.
 */
@RestController
@RequestMapping("/api")
public class StdCodesGroupPropResource {

    private final Logger log = LoggerFactory.getLogger(StdCodesGroupPropResource.class);

    private static final String ENTITY_NAME = "stdCodesGroupProp";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StdCodesGroupPropService stdCodesGroupPropService;

    private final StdCodesGroupPropQueryService stdCodesGroupPropQueryService;

    public StdCodesGroupPropResource(StdCodesGroupPropService stdCodesGroupPropService, StdCodesGroupPropQueryService stdCodesGroupPropQueryService) {
        this.stdCodesGroupPropService = stdCodesGroupPropService;
        this.stdCodesGroupPropQueryService = stdCodesGroupPropQueryService;
    }

    /**
     * {@code POST  /std-codes-group-props} : Create a new stdCodesGroupProp.
     *
     * @param stdCodesGroupProp the stdCodesGroupProp to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new stdCodesGroupProp, or with status {@code 400 (Bad Request)} if the stdCodesGroupProp has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/std-codes-group-props")
    public ResponseEntity<StdCodesGroupProp> createStdCodesGroupProp(@Valid @RequestBody StdCodesGroupProp stdCodesGroupProp) throws URISyntaxException {
        log.debug("REST request to save StdCodesGroupProp : {}", stdCodesGroupProp);
        if (stdCodesGroupProp.getId() != null) {
            throw new BadRequestAlertException("A new stdCodesGroupProp cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StdCodesGroupProp result = stdCodesGroupPropService.save(stdCodesGroupProp);
        return ResponseEntity.created(new URI("/api/std-codes-group-props/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /std-codes-group-props} : Updates an existing stdCodesGroupProp.
     *
     * @param stdCodesGroupProp the stdCodesGroupProp to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stdCodesGroupProp,
     * or with status {@code 400 (Bad Request)} if the stdCodesGroupProp is not valid,
     * or with status {@code 500 (Internal Server Error)} if the stdCodesGroupProp couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/std-codes-group-props")
    public ResponseEntity<StdCodesGroupProp> updateStdCodesGroupProp(@Valid @RequestBody StdCodesGroupProp stdCodesGroupProp) throws URISyntaxException {
        log.debug("REST request to update StdCodesGroupProp : {}", stdCodesGroupProp);
        if (stdCodesGroupProp.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StdCodesGroupProp result = stdCodesGroupPropService.save(stdCodesGroupProp);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, stdCodesGroupProp.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /std-codes-group-props} : get all the stdCodesGroupProps.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stdCodesGroupProps in body.
     */
    @GetMapping("/std-codes-group-props")
    public ResponseEntity<List<StdCodesGroupProp>> getAllStdCodesGroupProps(StdCodesGroupPropCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get StdCodesGroupProps by criteria: {}", criteria);
        Page<StdCodesGroupProp> page = stdCodesGroupPropQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /std-codes-group-props/count} : count all the stdCodesGroupProps.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/std-codes-group-props/count")
    public ResponseEntity<Long> countStdCodesGroupProps(StdCodesGroupPropCriteria criteria) {
        log.debug("REST request to count StdCodesGroupProps by criteria: {}", criteria);
        return ResponseEntity.ok().body(stdCodesGroupPropQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /std-codes-group-props/:id} : get the "id" stdCodesGroupProp.
     *
     * @param id the id of the stdCodesGroupProp to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the stdCodesGroupProp, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/std-codes-group-props/{id}")
    public ResponseEntity<StdCodesGroupProp> getStdCodesGroupProp(@PathVariable Long id) {
        log.debug("REST request to get StdCodesGroupProp : {}", id);
        Optional<StdCodesGroupProp> stdCodesGroupProp = stdCodesGroupPropService.findOne(id);
        return ResponseUtil.wrapOrNotFound(stdCodesGroupProp);
    }

    /**
     * {@code DELETE  /std-codes-group-props/:id} : delete the "id" stdCodesGroupProp.
     *
     * @param id the id of the stdCodesGroupProp to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/std-codes-group-props/{id}")
    public ResponseEntity<Void> deleteStdCodesGroupProp(@PathVariable Long id) {
        log.debug("REST request to delete StdCodesGroupProp : {}", id);
        stdCodesGroupPropService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

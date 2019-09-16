package net.atos.etax.web.rest;

import net.atos.etax.domain.StdCodesGroup;
import net.atos.etax.service.StdCodesGroupService;
import net.atos.etax.web.rest.errors.BadRequestAlertException;
import net.atos.etax.service.dto.StdCodesGroupCriteria;
import net.atos.etax.service.StdCodesGroupQueryService;

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
 * REST controller for managing {@link net.atos.etax.domain.StdCodesGroup}.
 */
@RestController
@RequestMapping("/api")
public class StdCodesGroupResource {

    private final Logger log = LoggerFactory.getLogger(StdCodesGroupResource.class);

    private static final String ENTITY_NAME = "stdCodesGroup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StdCodesGroupService stdCodesGroupService;

    private final StdCodesGroupQueryService stdCodesGroupQueryService;

    public StdCodesGroupResource(StdCodesGroupService stdCodesGroupService, StdCodesGroupQueryService stdCodesGroupQueryService) {
        this.stdCodesGroupService = stdCodesGroupService;
        this.stdCodesGroupQueryService = stdCodesGroupQueryService;
    }

    /**
     * {@code POST  /std-codes-groups} : Create a new stdCodesGroup.
     *
     * @param stdCodesGroup the stdCodesGroup to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new stdCodesGroup, or with status {@code 400 (Bad Request)} if the stdCodesGroup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/std-codes-groups")
    public ResponseEntity<StdCodesGroup> createStdCodesGroup(@Valid @RequestBody StdCodesGroup stdCodesGroup) throws URISyntaxException {
        log.debug("REST request to save StdCodesGroup : {}", stdCodesGroup);
        if (stdCodesGroup.getId() != null) {
            throw new BadRequestAlertException("A new stdCodesGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StdCodesGroup result = stdCodesGroupService.save(stdCodesGroup);
        return ResponseEntity.created(new URI("/api/std-codes-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /std-codes-groups} : Updates an existing stdCodesGroup.
     *
     * @param stdCodesGroup the stdCodesGroup to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stdCodesGroup,
     * or with status {@code 400 (Bad Request)} if the stdCodesGroup is not valid,
     * or with status {@code 500 (Internal Server Error)} if the stdCodesGroup couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/std-codes-groups")
    public ResponseEntity<StdCodesGroup> updateStdCodesGroup(@Valid @RequestBody StdCodesGroup stdCodesGroup) throws URISyntaxException {
        log.debug("REST request to update StdCodesGroup : {}", stdCodesGroup);
        if (stdCodesGroup.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StdCodesGroup result = stdCodesGroupService.save(stdCodesGroup);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, stdCodesGroup.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /std-codes-groups} : get all the stdCodesGroups.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stdCodesGroups in body.
     */
    @GetMapping("/std-codes-groups")
    public ResponseEntity<List<StdCodesGroup>> getAllStdCodesGroups(StdCodesGroupCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get StdCodesGroups by criteria: {}", criteria);
        Page<StdCodesGroup> page = stdCodesGroupQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /std-codes-groups/count} : count all the stdCodesGroups.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/std-codes-groups/count")
    public ResponseEntity<Long> countStdCodesGroups(StdCodesGroupCriteria criteria) {
        log.debug("REST request to count StdCodesGroups by criteria: {}", criteria);
        return ResponseEntity.ok().body(stdCodesGroupQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /std-codes-groups/:id} : get the "id" stdCodesGroup.
     *
     * @param id the id of the stdCodesGroup to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the stdCodesGroup, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/std-codes-groups/{id}")
    public ResponseEntity<StdCodesGroup> getStdCodesGroup(@PathVariable Long id) {
        log.debug("REST request to get StdCodesGroup : {}", id);
        Optional<StdCodesGroup> stdCodesGroup = stdCodesGroupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(stdCodesGroup);
    }

    /**
     * {@code DELETE  /std-codes-groups/:id} : delete the "id" stdCodesGroup.
     *
     * @param id the id of the stdCodesGroup to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/std-codes-groups/{id}")
    public ResponseEntity<Void> deleteStdCodesGroup(@PathVariable Long id) {
        log.debug("REST request to delete StdCodesGroup : {}", id);
        stdCodesGroupService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

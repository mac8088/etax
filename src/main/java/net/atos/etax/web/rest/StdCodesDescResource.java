package net.atos.etax.web.rest;

import net.atos.etax.domain.StdCodesDesc;
import net.atos.etax.service.StdCodesDescService;
import net.atos.etax.web.rest.errors.BadRequestAlertException;
import net.atos.etax.service.dto.StdCodesDescCriteria;
import net.atos.etax.service.StdCodesDescQueryService;

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
 * REST controller for managing {@link net.atos.etax.domain.StdCodesDesc}.
 */
@RestController
@RequestMapping("/api")
public class StdCodesDescResource {

    private final Logger log = LoggerFactory.getLogger(StdCodesDescResource.class);

    private static final String ENTITY_NAME = "stdCodesDesc";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StdCodesDescService stdCodesDescService;

    private final StdCodesDescQueryService stdCodesDescQueryService;

    public StdCodesDescResource(StdCodesDescService stdCodesDescService, StdCodesDescQueryService stdCodesDescQueryService) {
        this.stdCodesDescService = stdCodesDescService;
        this.stdCodesDescQueryService = stdCodesDescQueryService;
    }

    /**
     * {@code POST  /std-codes-descs} : Create a new stdCodesDesc.
     *
     * @param stdCodesDesc the stdCodesDesc to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new stdCodesDesc, or with status {@code 400 (Bad Request)} if the stdCodesDesc has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/std-codes-descs")
    public ResponseEntity<StdCodesDesc> createStdCodesDesc(@Valid @RequestBody StdCodesDesc stdCodesDesc) throws URISyntaxException {
        log.debug("REST request to save StdCodesDesc : {}", stdCodesDesc);
        if (stdCodesDesc.getId() != null) {
            throw new BadRequestAlertException("A new stdCodesDesc cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StdCodesDesc result = stdCodesDescService.save(stdCodesDesc);
        return ResponseEntity.created(new URI("/api/std-codes-descs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /std-codes-descs} : Updates an existing stdCodesDesc.
     *
     * @param stdCodesDesc the stdCodesDesc to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stdCodesDesc,
     * or with status {@code 400 (Bad Request)} if the stdCodesDesc is not valid,
     * or with status {@code 500 (Internal Server Error)} if the stdCodesDesc couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/std-codes-descs")
    public ResponseEntity<StdCodesDesc> updateStdCodesDesc(@Valid @RequestBody StdCodesDesc stdCodesDesc) throws URISyntaxException {
        log.debug("REST request to update StdCodesDesc : {}", stdCodesDesc);
        if (stdCodesDesc.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StdCodesDesc result = stdCodesDescService.save(stdCodesDesc);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, stdCodesDesc.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /std-codes-descs} : get all the stdCodesDescs.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stdCodesDescs in body.
     */
    @GetMapping("/std-codes-descs")
    public ResponseEntity<List<StdCodesDesc>> getAllStdCodesDescs(StdCodesDescCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get StdCodesDescs by criteria: {}", criteria);
        Page<StdCodesDesc> page = stdCodesDescQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /std-codes-descs/count} : count all the stdCodesDescs.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/std-codes-descs/count")
    public ResponseEntity<Long> countStdCodesDescs(StdCodesDescCriteria criteria) {
        log.debug("REST request to count StdCodesDescs by criteria: {}", criteria);
        return ResponseEntity.ok().body(stdCodesDescQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /std-codes-descs/:id} : get the "id" stdCodesDesc.
     *
     * @param id the id of the stdCodesDesc to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the stdCodesDesc, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/std-codes-descs/{id}")
    public ResponseEntity<StdCodesDesc> getStdCodesDesc(@PathVariable Long id) {
        log.debug("REST request to get StdCodesDesc : {}", id);
        Optional<StdCodesDesc> stdCodesDesc = stdCodesDescService.findOne(id);
        return ResponseUtil.wrapOrNotFound(stdCodesDesc);
    }

    /**
     * {@code DELETE  /std-codes-descs/:id} : delete the "id" stdCodesDesc.
     *
     * @param id the id of the stdCodesDesc to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/std-codes-descs/{id}")
    public ResponseEntity<Void> deleteStdCodesDesc(@PathVariable Long id) {
        log.debug("REST request to delete StdCodesDesc : {}", id);
        stdCodesDescService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

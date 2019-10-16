package net.atos.etax.web.rest;

import net.atos.etax.domain.Uiapp;
import net.atos.etax.service.UiappService;
import net.atos.etax.web.rest.errors.BadRequestAlertException;
import net.atos.etax.service.dto.UiappCriteria;
import net.atos.etax.service.UiappQueryService;

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
 * REST controller for managing {@link net.atos.etax.domain.Uiapp}.
 */
@RestController
@RequestMapping("/api")
public class UiappResource {

    private final Logger log = LoggerFactory.getLogger(UiappResource.class);

    private static final String ENTITY_NAME = "uiapp";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UiappService uiappService;

    private final UiappQueryService uiappQueryService;

    public UiappResource(UiappService uiappService, UiappQueryService uiappQueryService) {
        this.uiappService = uiappService;
        this.uiappQueryService = uiappQueryService;
    }

    /**
     * {@code POST  /uiapps} : Create a new uiapp.
     *
     * @param uiapp the uiapp to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new uiapp, or with status {@code 400 (Bad Request)} if the uiapp has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/uiapps")
    public ResponseEntity<Uiapp> createUiapp(@Valid @RequestBody Uiapp uiapp) throws URISyntaxException {
        log.debug("REST request to save Uiapp : {}", uiapp);
        if (uiapp.getId() != null) {
            throw new BadRequestAlertException("A new uiapp cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Uiapp result = uiappService.save(uiapp);
        return ResponseEntity.created(new URI("/api/uiapps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /uiapps} : Updates an existing uiapp.
     *
     * @param uiapp the uiapp to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated uiapp,
     * or with status {@code 400 (Bad Request)} if the uiapp is not valid,
     * or with status {@code 500 (Internal Server Error)} if the uiapp couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/uiapps")
    public ResponseEntity<Uiapp> updateUiapp(@Valid @RequestBody Uiapp uiapp) throws URISyntaxException {
        log.debug("REST request to update Uiapp : {}", uiapp);
        if (uiapp.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Uiapp result = uiappService.save(uiapp);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, uiapp.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /uiapps} : get all the uiapps.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of uiapps in body.
     */
    @GetMapping("/uiapps")
    public ResponseEntity<List<Uiapp>> getAllUiapps(UiappCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get Uiapps by criteria: {}", criteria);
        Page<Uiapp> page = uiappQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /uiapps/count} : count all the uiapps.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/uiapps/count")
    public ResponseEntity<Long> countUiapps(UiappCriteria criteria) {
        log.debug("REST request to count Uiapps by criteria: {}", criteria);
        return ResponseEntity.ok().body(uiappQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /uiapps/:id} : get the "id" uiapp.
     *
     * @param id the id of the uiapp to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the uiapp, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/uiapps/{id}")
    public ResponseEntity<Uiapp> getUiapp(@PathVariable Long id) {
        log.debug("REST request to get Uiapp : {}", id);
        Optional<Uiapp> uiapp = uiappService.findOne(id);
        return ResponseUtil.wrapOrNotFound(uiapp);
    }

    /**
     * {@code DELETE  /uiapps/:id} : delete the "id" uiapp.
     *
     * @param id the id of the uiapp to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/uiapps/{id}")
    public ResponseEntity<Void> deleteUiapp(@PathVariable Long id) {
        log.debug("REST request to delete Uiapp : {}", id);
        uiappService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

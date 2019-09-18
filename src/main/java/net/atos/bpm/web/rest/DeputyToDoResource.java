package net.atos.bpm.web.rest;

import net.atos.bpm.domain.DeputyToDo;
import net.atos.bpm.service.DeputyToDoService;
import net.atos.bpm.web.rest.errors.BadRequestAlertException;
import net.atos.bpm.service.dto.DeputyToDoCriteria;
import net.atos.bpm.service.DeputyToDoQueryService;

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
 * REST controller for managing {@link net.atos.etax.domain.DeputyToDo}.
 */
@RestController
@RequestMapping("/api")
public class DeputyToDoResource {

    private final Logger log = LoggerFactory.getLogger(DeputyToDoResource.class);

    private static final String ENTITY_NAME = "deputyToDo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DeputyToDoService deputyToDoService;

    private final DeputyToDoQueryService deputyToDoQueryService;

    public DeputyToDoResource(DeputyToDoService deputyToDoService, DeputyToDoQueryService deputyToDoQueryService) {
        this.deputyToDoService = deputyToDoService;
        this.deputyToDoQueryService = deputyToDoQueryService;
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
        DeputyToDo result = deputyToDoService.save(deputyToDo);
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
        DeputyToDo result = deputyToDoService.save(deputyToDo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, deputyToDo.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /deputy-to-dos} : get all the deputyToDos.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of deputyToDos in body.
     */
    @GetMapping("/deputy-to-dos")
    public ResponseEntity<List<DeputyToDo>> getAllDeputyToDos(DeputyToDoCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get DeputyToDos by criteria: {}", criteria);
        Page<DeputyToDo> page = deputyToDoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /deputy-to-dos/count} : count all the deputyToDos.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/deputy-to-dos/count")
    public ResponseEntity<Long> countDeputyToDos(DeputyToDoCriteria criteria) {
        log.debug("REST request to count DeputyToDos by criteria: {}", criteria);
        return ResponseEntity.ok().body(deputyToDoQueryService.countByCriteria(criteria));
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
        Optional<DeputyToDo> deputyToDo = deputyToDoService.findOne(id);
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
        deputyToDoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

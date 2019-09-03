package net.atos.etax.web.rest;

import net.atos.etax.domain.PublicHoliday;
import net.atos.etax.repository.PublicHolidayRepository;
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
 * REST controller for managing {@link net.atos.etax.domain.PublicHoliday}.
 */
@RestController
@RequestMapping("/api")
public class PublicHolidayResource {

    private final Logger log = LoggerFactory.getLogger(PublicHolidayResource.class);

    private static final String ENTITY_NAME = "publicHoliday";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PublicHolidayRepository publicHolidayRepository;

    public PublicHolidayResource(PublicHolidayRepository publicHolidayRepository) {
        this.publicHolidayRepository = publicHolidayRepository;
    }

    /**
     * {@code POST  /public-holidays} : Create a new publicHoliday.
     *
     * @param publicHoliday the publicHoliday to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new publicHoliday, or with status {@code 400 (Bad Request)} if the publicHoliday has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/public-holidays")
    public ResponseEntity<PublicHoliday> createPublicHoliday(@Valid @RequestBody PublicHoliday publicHoliday) throws URISyntaxException {
        log.debug("REST request to save PublicHoliday : {}", publicHoliday);
        if (publicHoliday.getId() != null) {
            throw new BadRequestAlertException("A new publicHoliday cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PublicHoliday result = publicHolidayRepository.save(publicHoliday);
        return ResponseEntity.created(new URI("/api/public-holidays/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /public-holidays} : Updates an existing publicHoliday.
     *
     * @param publicHoliday the publicHoliday to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated publicHoliday,
     * or with status {@code 400 (Bad Request)} if the publicHoliday is not valid,
     * or with status {@code 500 (Internal Server Error)} if the publicHoliday couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/public-holidays")
    public ResponseEntity<PublicHoliday> updatePublicHoliday(@Valid @RequestBody PublicHoliday publicHoliday) throws URISyntaxException {
        log.debug("REST request to update PublicHoliday : {}", publicHoliday);
        if (publicHoliday.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PublicHoliday result = publicHolidayRepository.save(publicHoliday);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, publicHoliday.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /public-holidays} : get all the publicHolidays.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of publicHolidays in body.
     */
    @GetMapping("/public-holidays")
    public List<PublicHoliday> getAllPublicHolidays() {
        log.debug("REST request to get all PublicHolidays");
        return publicHolidayRepository.findAll();
    }

    /**
     * {@code GET  /public-holidays/:id} : get the "id" publicHoliday.
     *
     * @param id the id of the publicHoliday to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the publicHoliday, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/public-holidays/{id}")
    public ResponseEntity<PublicHoliday> getPublicHoliday(@PathVariable Long id) {
        log.debug("REST request to get PublicHoliday : {}", id);
        Optional<PublicHoliday> publicHoliday = publicHolidayRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(publicHoliday);
    }

    /**
     * {@code DELETE  /public-holidays/:id} : delete the "id" publicHoliday.
     *
     * @param id the id of the publicHoliday to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/public-holidays/{id}")
    public ResponseEntity<Void> deletePublicHoliday(@PathVariable Long id) {
        log.debug("REST request to delete PublicHoliday : {}", id);
        publicHolidayRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

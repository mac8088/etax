package net.atos.etax.web.rest;

import net.atos.etax.domain.OfficeHoliday;
import net.atos.etax.repository.OfficeHolidayRepository;
import net.atos.etax.web.rest.errors.BadRequestAlertException;

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
 * REST controller for managing {@link net.atos.etax.domain.OfficeHoliday}.
 */
@RestController
@RequestMapping("/api")
public class OfficeHolidayResource {

    private final Logger log = LoggerFactory.getLogger(OfficeHolidayResource.class);

    private static final String ENTITY_NAME = "officeHoliday";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OfficeHolidayRepository officeHolidayRepository;

    public OfficeHolidayResource(OfficeHolidayRepository officeHolidayRepository) {
        this.officeHolidayRepository = officeHolidayRepository;
    }

    /**
     * {@code POST  /office-holidays} : Create a new officeHoliday.
     *
     * @param officeHoliday the officeHoliday to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new officeHoliday, or with status {@code 400 (Bad Request)} if the officeHoliday has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/office-holidays")
    public ResponseEntity<OfficeHoliday> createOfficeHoliday(@Valid @RequestBody OfficeHoliday officeHoliday) throws URISyntaxException {
        log.debug("REST request to save OfficeHoliday : {}", officeHoliday);
        if (officeHoliday.getId() != null) {
            throw new BadRequestAlertException("A new officeHoliday cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OfficeHoliday result = officeHolidayRepository.save(officeHoliday);
        return ResponseEntity.created(new URI("/api/office-holidays/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /office-holidays} : Updates an existing officeHoliday.
     *
     * @param officeHoliday the officeHoliday to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated officeHoliday,
     * or with status {@code 400 (Bad Request)} if the officeHoliday is not valid,
     * or with status {@code 500 (Internal Server Error)} if the officeHoliday couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/office-holidays")
    public ResponseEntity<OfficeHoliday> updateOfficeHoliday(@Valid @RequestBody OfficeHoliday officeHoliday) throws URISyntaxException {
        log.debug("REST request to update OfficeHoliday : {}", officeHoliday);
        if (officeHoliday.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OfficeHoliday result = officeHolidayRepository.save(officeHoliday);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, officeHoliday.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /office-holidays} : get all the officeHolidays.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of officeHolidays in body.
     */
    @GetMapping("/office-holidays")
    public ResponseEntity<List<OfficeHoliday>> getAllOfficeHolidays(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of OfficeHolidays");
        Page<OfficeHoliday> page = officeHolidayRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /office-holidays/:id} : get the "id" officeHoliday.
     *
     * @param id the id of the officeHoliday to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the officeHoliday, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/office-holidays/{id}")
    public ResponseEntity<OfficeHoliday> getOfficeHoliday(@PathVariable Long id) {
        log.debug("REST request to get OfficeHoliday : {}", id);
        Optional<OfficeHoliday> officeHoliday = officeHolidayRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(officeHoliday);
    }

    /**
     * {@code DELETE  /office-holidays/:id} : delete the "id" officeHoliday.
     *
     * @param id the id of the officeHoliday to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/office-holidays/{id}")
    public ResponseEntity<Void> deleteOfficeHoliday(@PathVariable Long id) {
        log.debug("REST request to delete OfficeHoliday : {}", id);
        officeHolidayRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

package net.atos.etax.web.rest;

import net.atos.etax.domain.OfficeWeekday;
import net.atos.etax.repository.OfficeWeekdayRepository;
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
 * REST controller for managing {@link net.atos.etax.domain.OfficeWeekday}.
 */
@RestController
@RequestMapping("/api")
public class OfficeWeekdayResource {

    private final Logger log = LoggerFactory.getLogger(OfficeWeekdayResource.class);

    private static final String ENTITY_NAME = "officeWeekday";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OfficeWeekdayRepository officeWeekdayRepository;

    public OfficeWeekdayResource(OfficeWeekdayRepository officeWeekdayRepository) {
        this.officeWeekdayRepository = officeWeekdayRepository;
    }

    /**
     * {@code POST  /office-weekdays} : Create a new officeWeekday.
     *
     * @param officeWeekday the officeWeekday to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new officeWeekday, or with status {@code 400 (Bad Request)} if the officeWeekday has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/office-weekdays")
    public ResponseEntity<OfficeWeekday> createOfficeWeekday(@Valid @RequestBody OfficeWeekday officeWeekday) throws URISyntaxException {
        log.debug("REST request to save OfficeWeekday : {}", officeWeekday);
        if (officeWeekday.getId() != null) {
            throw new BadRequestAlertException("A new officeWeekday cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OfficeWeekday result = officeWeekdayRepository.save(officeWeekday);
        return ResponseEntity.created(new URI("/api/office-weekdays/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /office-weekdays} : Updates an existing officeWeekday.
     *
     * @param officeWeekday the officeWeekday to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated officeWeekday,
     * or with status {@code 400 (Bad Request)} if the officeWeekday is not valid,
     * or with status {@code 500 (Internal Server Error)} if the officeWeekday couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/office-weekdays")
    public ResponseEntity<OfficeWeekday> updateOfficeWeekday(@Valid @RequestBody OfficeWeekday officeWeekday) throws URISyntaxException {
        log.debug("REST request to update OfficeWeekday : {}", officeWeekday);
        if (officeWeekday.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OfficeWeekday result = officeWeekdayRepository.save(officeWeekday);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, officeWeekday.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /office-weekdays} : get all the officeWeekdays.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of officeWeekdays in body.
     */
    @GetMapping("/office-weekdays")
    public ResponseEntity<List<OfficeWeekday>> getAllOfficeWeekdays(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of OfficeWeekdays");
        Page<OfficeWeekday> page = officeWeekdayRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /office-weekdays/:id} : get the "id" officeWeekday.
     *
     * @param id the id of the officeWeekday to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the officeWeekday, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/office-weekdays/{id}")
    public ResponseEntity<OfficeWeekday> getOfficeWeekday(@PathVariable Long id) {
        log.debug("REST request to get OfficeWeekday : {}", id);
        Optional<OfficeWeekday> officeWeekday = officeWeekdayRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(officeWeekday);
    }

    /**
     * {@code DELETE  /office-weekdays/:id} : delete the "id" officeWeekday.
     *
     * @param id the id of the officeWeekday to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/office-weekdays/{id}")
    public ResponseEntity<Void> deleteOfficeWeekday(@PathVariable Long id) {
        log.debug("REST request to delete OfficeWeekday : {}", id);
        officeWeekdayRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

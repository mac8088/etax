package net.atos.etax.web.rest;

import net.atos.etax.domain.OfficeAreaCode;
import net.atos.etax.repository.OfficeAreaCodeRepository;
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
 * REST controller for managing {@link net.atos.etax.domain.OfficeAreaCode}.
 */
@RestController
@RequestMapping("/api")
public class OfficeAreaCodeResource {

    private final Logger log = LoggerFactory.getLogger(OfficeAreaCodeResource.class);

    private static final String ENTITY_NAME = "officeAreaCode";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OfficeAreaCodeRepository officeAreaCodeRepository;

    public OfficeAreaCodeResource(OfficeAreaCodeRepository officeAreaCodeRepository) {
        this.officeAreaCodeRepository = officeAreaCodeRepository;
    }

    /**
     * {@code POST  /office-area-codes} : Create a new officeAreaCode.
     *
     * @param officeAreaCode the officeAreaCode to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new officeAreaCode, or with status {@code 400 (Bad Request)} if the officeAreaCode has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/office-area-codes")
    public ResponseEntity<OfficeAreaCode> createOfficeAreaCode(@Valid @RequestBody OfficeAreaCode officeAreaCode) throws URISyntaxException {
        log.debug("REST request to save OfficeAreaCode : {}", officeAreaCode);
        if (officeAreaCode.getId() != null) {
            throw new BadRequestAlertException("A new officeAreaCode cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OfficeAreaCode result = officeAreaCodeRepository.save(officeAreaCode);
        return ResponseEntity.created(new URI("/api/office-area-codes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /office-area-codes} : Updates an existing officeAreaCode.
     *
     * @param officeAreaCode the officeAreaCode to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated officeAreaCode,
     * or with status {@code 400 (Bad Request)} if the officeAreaCode is not valid,
     * or with status {@code 500 (Internal Server Error)} if the officeAreaCode couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/office-area-codes")
    public ResponseEntity<OfficeAreaCode> updateOfficeAreaCode(@Valid @RequestBody OfficeAreaCode officeAreaCode) throws URISyntaxException {
        log.debug("REST request to update OfficeAreaCode : {}", officeAreaCode);
        if (officeAreaCode.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OfficeAreaCode result = officeAreaCodeRepository.save(officeAreaCode);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, officeAreaCode.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /office-area-codes} : get all the officeAreaCodes.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of officeAreaCodes in body.
     */
    @GetMapping("/office-area-codes")
    public ResponseEntity<List<OfficeAreaCode>> getAllOfficeAreaCodes(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of OfficeAreaCodes");
        Page<OfficeAreaCode> page = officeAreaCodeRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /office-area-codes/:id} : get the "id" officeAreaCode.
     *
     * @param id the id of the officeAreaCode to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the officeAreaCode, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/office-area-codes/{id}")
    public ResponseEntity<OfficeAreaCode> getOfficeAreaCode(@PathVariable Long id) {
        log.debug("REST request to get OfficeAreaCode : {}", id);
        Optional<OfficeAreaCode> officeAreaCode = officeAreaCodeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(officeAreaCode);
    }

    /**
     * {@code DELETE  /office-area-codes/:id} : delete the "id" officeAreaCode.
     *
     * @param id the id of the officeAreaCode to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/office-area-codes/{id}")
    public ResponseEntity<Void> deleteOfficeAreaCode(@PathVariable Long id) {
        log.debug("REST request to delete OfficeAreaCode : {}", id);
        officeAreaCodeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code FIND  /office-area-codes/search-office:id} : find the "officeId" officeAreaCode.
     *
     * @param officeId the officeId of the officeAreaCode to find.
     * @return the {@link ResponseEntity<List<OfficeAreaCode>>} with status {@code 204 (NO_CONTENT)}.
     */
    @GetMapping("/office-area-codes/search-office/{officeId}")
    public ResponseEntity<List<OfficeAreaCode>> getAllOfficeAreaCodeByOfficeId(@PathVariable Integer officeId) {
        log.debug("REST request to find OfficeAreaCode by officeId");
        List<OfficeAreaCode> result = officeAreaCodeRepository.findAllByOfficeIdEquals(officeId);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert(applicationName,"Search result",ENTITY_NAME)).body(result);
    }
}

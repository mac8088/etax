package net.atos.etax.web.rest;

import net.atos.etax.domain.OfficeTaxFunc;
import net.atos.etax.repository.OfficeTaxFuncRepository;
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
 * REST controller for managing {@link net.atos.etax.domain.OfficeTaxFunc}.
 */
@RestController
@RequestMapping("/api")
public class OfficeTaxFuncResource {

    private final Logger log = LoggerFactory.getLogger(OfficeTaxFuncResource.class);

    private static final String ENTITY_NAME = "officeTaxFunc";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OfficeTaxFuncRepository officeTaxFuncRepository;

    public OfficeTaxFuncResource(OfficeTaxFuncRepository officeTaxFuncRepository) {
        this.officeTaxFuncRepository = officeTaxFuncRepository;
    }

    /**
     * {@code POST  /office-tax-funcs} : Create a new officeTaxFunc.
     *
     * @param officeTaxFunc the officeTaxFunc to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new officeTaxFunc, or with status {@code 400 (Bad Request)} if the officeTaxFunc has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/office-tax-funcs")
    public ResponseEntity<OfficeTaxFunc> createOfficeTaxFunc(@Valid @RequestBody OfficeTaxFunc officeTaxFunc) throws URISyntaxException {
        log.debug("REST request to save OfficeTaxFunc : {}", officeTaxFunc);
        if (officeTaxFunc.getId() != null) {
            throw new BadRequestAlertException("A new officeTaxFunc cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OfficeTaxFunc result = officeTaxFuncRepository.save(officeTaxFunc);
        return ResponseEntity.created(new URI("/api/office-tax-funcs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /office-tax-funcs} : Updates an existing officeTaxFunc.
     *
     * @param officeTaxFunc the officeTaxFunc to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated officeTaxFunc,
     * or with status {@code 400 (Bad Request)} if the officeTaxFunc is not valid,
     * or with status {@code 500 (Internal Server Error)} if the officeTaxFunc couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/office-tax-funcs")
    public ResponseEntity<OfficeTaxFunc> updateOfficeTaxFunc(@Valid @RequestBody OfficeTaxFunc officeTaxFunc) throws URISyntaxException {
        log.debug("REST request to update OfficeTaxFunc : {}", officeTaxFunc);
        if (officeTaxFunc.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OfficeTaxFunc result = officeTaxFuncRepository.save(officeTaxFunc);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, officeTaxFunc.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /office-tax-funcs} : get all the officeTaxFuncs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of officeTaxFuncs in body.
     */
    @GetMapping("/office-tax-funcs")
    public List<OfficeTaxFunc> getAllOfficeTaxFuncs() {
        log.debug("REST request to get all OfficeTaxFuncs");
        return officeTaxFuncRepository.findAll();
    }

    /**
     * {@code GET  /office-tax-funcs/:id} : get the "id" officeTaxFunc.
     *
     * @param id the id of the officeTaxFunc to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the officeTaxFunc, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/office-tax-funcs/{id}")
    public ResponseEntity<OfficeTaxFunc> getOfficeTaxFunc(@PathVariable Long id) {
        log.debug("REST request to get OfficeTaxFunc : {}", id);
        Optional<OfficeTaxFunc> officeTaxFunc = officeTaxFuncRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(officeTaxFunc);
    }

    /**
     * {@code DELETE  /office-tax-funcs/:id} : delete the "id" officeTaxFunc.
     *
     * @param id the id of the officeTaxFunc to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/office-tax-funcs/{id}")
    public ResponseEntity<Void> deleteOfficeTaxFunc(@PathVariable Long id) {
        log.debug("REST request to delete OfficeTaxFunc : {}", id);
        officeTaxFuncRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

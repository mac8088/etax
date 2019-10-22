package net.atos.etax.web.rest;

import net.atos.etax.domain.ProfileSetup;
import net.atos.etax.repository.ProfileSetupRepository;
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
 * REST controller for managing {@link net.atos.etax.domain.ProfileSetup}.
 */
@RestController
@RequestMapping("/api")
public class ProfileSetupResource {

    private final Logger log = LoggerFactory.getLogger(ProfileSetupResource.class);

    private static final String ENTITY_NAME = "profileSetup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProfileSetupRepository profileSetupRepository;

    public ProfileSetupResource(ProfileSetupRepository profileSetupRepository) {
        this.profileSetupRepository = profileSetupRepository;
    }

    /**
     * {@code POST  /profile-setups} : Create a new profileSetup.
     *
     * @param profileSetup the profileSetup to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new profileSetup, or with status {@code 400 (Bad Request)} if the profileSetup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/profile-setups")
    public ResponseEntity<ProfileSetup> createProfileSetup(@Valid @RequestBody ProfileSetup profileSetup) throws URISyntaxException {
        log.debug("REST request to save ProfileSetup : {}", profileSetup);
        if (profileSetup.getId() != null) {
            throw new BadRequestAlertException("A new profileSetup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProfileSetup result = profileSetupRepository.save(profileSetup);
        return ResponseEntity.created(new URI("/api/profile-setups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /profile-setups} : Updates an existing profileSetup.
     *
     * @param profileSetup the profileSetup to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated profileSetup,
     * or with status {@code 400 (Bad Request)} if the profileSetup is not valid,
     * or with status {@code 500 (Internal Server Error)} if the profileSetup couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/profile-setups")
    public ResponseEntity<ProfileSetup> updateProfileSetup(@Valid @RequestBody ProfileSetup profileSetup) throws URISyntaxException {
        log.debug("REST request to update ProfileSetup : {}", profileSetup);
        if (profileSetup.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProfileSetup result = profileSetupRepository.save(profileSetup);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, profileSetup.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /profile-setups} : get all the profileSetups.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of profileSetups in body.
     */
    @GetMapping("/profile-setups")
    public ResponseEntity<List<ProfileSetup>> getAllProfileSetups(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of ProfileSetups");
        Page<ProfileSetup> page = profileSetupRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /profile-setups/:id} : get the "id" profileSetup.
     *
     * @param id the id of the profileSetup to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the profileSetup, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/profile-setups/{id}")
    public ResponseEntity<ProfileSetup> getProfileSetup(@PathVariable Long id) {
        log.debug("REST request to get ProfileSetup : {}", id);
        Optional<ProfileSetup> profileSetup = profileSetupRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(profileSetup);
    }

    /**
     * {@code DELETE  /profile-setups/:id} : delete the "id" profileSetup.
     *
     * @param id the id of the profileSetup to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/profile-setups/{id}")
    public ResponseEntity<Void> deleteProfileSetup(@PathVariable Long id) {
        log.debug("REST request to delete ProfileSetup : {}", id);
        profileSetupRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

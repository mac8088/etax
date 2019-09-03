package net.atos.etax.web.rest;

import net.atos.etax.domain.ExchangeRate;
import net.atos.etax.repository.ExchangeRateRepository;
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
 * REST controller for managing {@link net.atos.etax.domain.ExchangeRate}.
 */
@RestController
@RequestMapping("/api")
public class ExchangeRateResource {

    private final Logger log = LoggerFactory.getLogger(ExchangeRateResource.class);

    private static final String ENTITY_NAME = "exchangeRate";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ExchangeRateRepository exchangeRateRepository;

    public ExchangeRateResource(ExchangeRateRepository exchangeRateRepository) {
        this.exchangeRateRepository = exchangeRateRepository;
    }

    /**
     * {@code POST  /exchange-rates} : Create a new exchangeRate.
     *
     * @param exchangeRate the exchangeRate to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new exchangeRate, or with status {@code 400 (Bad Request)} if the exchangeRate has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/exchange-rates")
    public ResponseEntity<ExchangeRate> createExchangeRate(@Valid @RequestBody ExchangeRate exchangeRate) throws URISyntaxException {
        log.debug("REST request to save ExchangeRate : {}", exchangeRate);
        if (exchangeRate.getId() != null) {
            throw new BadRequestAlertException("A new exchangeRate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ExchangeRate result = exchangeRateRepository.save(exchangeRate);
        return ResponseEntity.created(new URI("/api/exchange-rates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /exchange-rates} : Updates an existing exchangeRate.
     *
     * @param exchangeRate the exchangeRate to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated exchangeRate,
     * or with status {@code 400 (Bad Request)} if the exchangeRate is not valid,
     * or with status {@code 500 (Internal Server Error)} if the exchangeRate couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/exchange-rates")
    public ResponseEntity<ExchangeRate> updateExchangeRate(@Valid @RequestBody ExchangeRate exchangeRate) throws URISyntaxException {
        log.debug("REST request to update ExchangeRate : {}", exchangeRate);
        if (exchangeRate.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ExchangeRate result = exchangeRateRepository.save(exchangeRate);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, exchangeRate.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /exchange-rates} : get all the exchangeRates.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of exchangeRates in body.
     */
    @GetMapping("/exchange-rates")
    public List<ExchangeRate> getAllExchangeRates() {
        log.debug("REST request to get all ExchangeRates");
        return exchangeRateRepository.findAll();
    }

    /**
     * {@code GET  /exchange-rates/:id} : get the "id" exchangeRate.
     *
     * @param id the id of the exchangeRate to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the exchangeRate, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/exchange-rates/{id}")
    public ResponseEntity<ExchangeRate> getExchangeRate(@PathVariable Long id) {
        log.debug("REST request to get ExchangeRate : {}", id);
        Optional<ExchangeRate> exchangeRate = exchangeRateRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(exchangeRate);
    }

    /**
     * {@code DELETE  /exchange-rates/:id} : delete the "id" exchangeRate.
     *
     * @param id the id of the exchangeRate to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/exchange-rates/{id}")
    public ResponseEntity<Void> deleteExchangeRate(@PathVariable Long id) {
        log.debug("REST request to delete ExchangeRate : {}", id);
        exchangeRateRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

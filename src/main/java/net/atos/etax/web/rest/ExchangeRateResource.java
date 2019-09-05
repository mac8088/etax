package net.atos.etax.web.rest;

import net.atos.etax.domain.ExchangeRate;
import net.atos.etax.service.ExchangeRateService;
import net.atos.etax.web.rest.errors.BadRequestAlertException;
import net.atos.etax.service.dto.ExchangeRateCriteria;
import net.atos.etax.service.ExchangeRateQueryService;

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
 * REST controller for managing {@link net.atos.etax.domain.ExchangeRate}.
 */
@RestController
@RequestMapping("/api")
public class ExchangeRateResource {

    private final Logger log = LoggerFactory.getLogger(ExchangeRateResource.class);

    private static final String ENTITY_NAME = "exchangeRate";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ExchangeRateService exchangeRateService;

    private final ExchangeRateQueryService exchangeRateQueryService;

    public ExchangeRateResource(ExchangeRateService exchangeRateService, ExchangeRateQueryService exchangeRateQueryService) {
        this.exchangeRateService = exchangeRateService;
        this.exchangeRateQueryService = exchangeRateQueryService;
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
        ExchangeRate result = exchangeRateService.save(exchangeRate);
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
        ExchangeRate result = exchangeRateService.save(exchangeRate);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, exchangeRate.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /exchange-rates} : get all the exchangeRates.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of exchangeRates in body.
     */
    @GetMapping("/exchange-rates")
    public ResponseEntity<List<ExchangeRate>> getAllExchangeRates(ExchangeRateCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get ExchangeRates by criteria: {}", criteria);
        Page<ExchangeRate> page = exchangeRateQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /exchange-rates/count} : count all the exchangeRates.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/exchange-rates/count")
    public ResponseEntity<Long> countExchangeRates(ExchangeRateCriteria criteria) {
        log.debug("REST request to count ExchangeRates by criteria: {}", criteria);
        return ResponseEntity.ok().body(exchangeRateQueryService.countByCriteria(criteria));
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
        Optional<ExchangeRate> exchangeRate = exchangeRateService.findOne(id);
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
        exchangeRateService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

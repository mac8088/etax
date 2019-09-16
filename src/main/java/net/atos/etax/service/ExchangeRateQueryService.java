package net.atos.etax.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import net.atos.etax.domain.ExchangeRate;
import net.atos.etax.domain.*; // for static metamodels
import net.atos.etax.repository.ExchangeRateRepository;
import net.atos.etax.service.dto.ExchangeRateCriteria;

/**
 * Service for executing complex queries for {@link ExchangeRate} entities in the database.
 * The main input is a {@link ExchangeRateCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ExchangeRate} or a {@link Page} of {@link ExchangeRate} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ExchangeRateQueryService extends QueryService<ExchangeRate> {

    private final Logger log = LoggerFactory.getLogger(ExchangeRateQueryService.class);

    private final ExchangeRateRepository exchangeRateRepository;

    public ExchangeRateQueryService(ExchangeRateRepository exchangeRateRepository) {
        this.exchangeRateRepository = exchangeRateRepository;
    }

    /**
     * Return a {@link List} of {@link ExchangeRate} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ExchangeRate> findByCriteria(ExchangeRateCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ExchangeRate> specification = createSpecification(criteria);
        return exchangeRateRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ExchangeRate} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ExchangeRate> findByCriteria(ExchangeRateCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ExchangeRate> specification = createSpecification(criteria);
        return exchangeRateRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ExchangeRateCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ExchangeRate> specification = createSpecification(criteria);
        return exchangeRateRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<ExchangeRate> createSpecification(ExchangeRateCriteria criteria) {
        Specification<ExchangeRate> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ExchangeRate_.id));
            }
            if (criteria.getCstdCurrencyA() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCstdCurrencyA(), ExchangeRate_.cstdCurrencyA));
            }
            if (criteria.getCstdCurrencyB() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCstdCurrencyB(), ExchangeRate_.cstdCurrencyB));
            }
            if (criteria.getRate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRate(), ExchangeRate_.rate));
            }
            if (criteria.getStartDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartDate(), ExchangeRate_.startDate));
            }
            if (criteria.getEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndDate(), ExchangeRate_.endDate));
            }
            if (criteria.getCcVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCcVersion(), ExchangeRate_.ccVersion));
            }
        }
        return specification;
    }
}

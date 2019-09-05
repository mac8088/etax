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

import net.atos.etax.domain.StdCodesProp;
import net.atos.etax.domain.*; // for static metamodels
import net.atos.etax.repository.StdCodesPropRepository;
import net.atos.etax.service.dto.StdCodesPropCriteria;

/**
 * Service for executing complex queries for {@link StdCodesProp} entities in the database.
 * The main input is a {@link StdCodesPropCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link StdCodesProp} or a {@link Page} of {@link StdCodesProp} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class StdCodesPropQueryService extends QueryService<StdCodesProp> {

    private final Logger log = LoggerFactory.getLogger(StdCodesPropQueryService.class);

    private final StdCodesPropRepository stdCodesPropRepository;

    public StdCodesPropQueryService(StdCodesPropRepository stdCodesPropRepository) {
        this.stdCodesPropRepository = stdCodesPropRepository;
    }

    /**
     * Return a {@link List} of {@link StdCodesProp} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<StdCodesProp> findByCriteria(StdCodesPropCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<StdCodesProp> specification = createSpecification(criteria);
        return stdCodesPropRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link StdCodesProp} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<StdCodesProp> findByCriteria(StdCodesPropCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<StdCodesProp> specification = createSpecification(criteria);
        return stdCodesPropRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(StdCodesPropCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<StdCodesProp> specification = createSpecification(criteria);
        return stdCodesPropRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<StdCodesProp> createSpecification(StdCodesPropCriteria criteria) {
        Specification<StdCodesProp> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), StdCodesProp_.id));
            }
            if (criteria.getGroupCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGroupCode(), StdCodesProp_.groupCode));
            }
            if (criteria.getInternalCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getInternalCode(), StdCodesProp_.internalCode));
            }
            if (criteria.getPropCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPropCode(), StdCodesProp_.propCode));
            }
            if (criteria.getStartDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartDate(), StdCodesProp_.startDate));
            }
            if (criteria.getEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndDate(), StdCodesProp_.endDate));
            }
            if (criteria.getValueDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValueDate(), StdCodesProp_.valueDate));
            }
            if (criteria.getValueString() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValueString(), StdCodesProp_.valueString));
            }
            if (criteria.getValueBool() != null) {
                specification = specification.and(buildSpecification(criteria.getValueBool(), StdCodesProp_.valueBool));
            }
            if (criteria.getValueNumber() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValueNumber(), StdCodesProp_.valueNumber));
            }
        }
        return specification;
    }
}

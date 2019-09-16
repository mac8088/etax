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

import net.atos.etax.domain.StdCodesGroupProp;
import net.atos.etax.domain.*; // for static metamodels
import net.atos.etax.repository.StdCodesGroupPropRepository;
import net.atos.etax.service.dto.StdCodesGroupPropCriteria;

/**
 * Service for executing complex queries for {@link StdCodesGroupProp} entities in the database.
 * The main input is a {@link StdCodesGroupPropCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link StdCodesGroupProp} or a {@link Page} of {@link StdCodesGroupProp} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class StdCodesGroupPropQueryService extends QueryService<StdCodesGroupProp> {

    private final Logger log = LoggerFactory.getLogger(StdCodesGroupPropQueryService.class);

    private final StdCodesGroupPropRepository stdCodesGroupPropRepository;

    public StdCodesGroupPropQueryService(StdCodesGroupPropRepository stdCodesGroupPropRepository) {
        this.stdCodesGroupPropRepository = stdCodesGroupPropRepository;
    }

    /**
     * Return a {@link List} of {@link StdCodesGroupProp} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<StdCodesGroupProp> findByCriteria(StdCodesGroupPropCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<StdCodesGroupProp> specification = createSpecification(criteria);
        return stdCodesGroupPropRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link StdCodesGroupProp} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<StdCodesGroupProp> findByCriteria(StdCodesGroupPropCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<StdCodesGroupProp> specification = createSpecification(criteria);
        return stdCodesGroupPropRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(StdCodesGroupPropCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<StdCodesGroupProp> specification = createSpecification(criteria);
        return stdCodesGroupPropRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<StdCodesGroupProp> createSpecification(StdCodesGroupPropCriteria criteria) {
        Specification<StdCodesGroupProp> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), StdCodesGroupProp_.id));
            }
            if (criteria.getGroupCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGroupCode(), StdCodesGroupProp_.groupCode));
            }
            if (criteria.getPropCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPropCode(), StdCodesGroupProp_.propCode));
            }
            if (criteria.getPropDesc() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPropDesc(), StdCodesGroupProp_.propDesc));
            }
            if (criteria.getStartDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartDate(), StdCodesGroupProp_.startDate));
            }
            if (criteria.getEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndDate(), StdCodesGroupProp_.endDate));
            }
            if (criteria.getPropType() != null) {
                specification = specification.and(buildSpecification(criteria.getPropType(), StdCodesGroupProp_.propType));
            }
            if (criteria.getPropMdtr() != null) {
                specification = specification.and(buildSpecification(criteria.getPropMdtr(), StdCodesGroupProp_.propMdtr));
            }
            if (criteria.getDfltValueDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDfltValueDate(), StdCodesGroupProp_.dfltValueDate));
            }
            if (criteria.getDfltValueString() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDfltValueString(), StdCodesGroupProp_.dfltValueString));
            }
            if (criteria.getDfltValueBool() != null) {
                specification = specification.and(buildSpecification(criteria.getDfltValueBool(), StdCodesGroupProp_.dfltValueBool));
            }
            if (criteria.getDfltValueNumber() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDfltValueNumber(), StdCodesGroupProp_.dfltValueNumber));
            }
        }
        return specification;
    }
}

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

import net.atos.etax.domain.StdCodes;
import net.atos.etax.domain.*; // for static metamodels
import net.atos.etax.repository.StdCodesRepository;
import net.atos.etax.service.dto.StdCodesCriteria;

/**
 * Service for executing complex queries for {@link StdCodes} entities in the database.
 * The main input is a {@link StdCodesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link StdCodes} or a {@link Page} of {@link StdCodes} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class StdCodesQueryService extends QueryService<StdCodes> {

    private final Logger log = LoggerFactory.getLogger(StdCodesQueryService.class);

    private final StdCodesRepository stdCodesRepository;

    public StdCodesQueryService(StdCodesRepository stdCodesRepository) {
        this.stdCodesRepository = stdCodesRepository;
    }

    /**
     * Return a {@link List} of {@link StdCodes} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<StdCodes> findByCriteria(StdCodesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<StdCodes> specification = createSpecification(criteria);
        return stdCodesRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link StdCodes} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<StdCodes> findByCriteria(StdCodesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<StdCodes> specification = createSpecification(criteria);
        return stdCodesRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(StdCodesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<StdCodes> specification = createSpecification(criteria);
        return stdCodesRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<StdCodes> createSpecification(StdCodesCriteria criteria) {
        Specification<StdCodes> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), StdCodes_.id));
            }
            if (criteria.getGroupCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGroupCode(), StdCodes_.groupCode));
            }
            if (criteria.getInternalCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getInternalCode(), StdCodes_.internalCode));
            }
            if (criteria.getStartDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartDate(), StdCodes_.startDate));
            }
            if (criteria.getEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndDate(), StdCodes_.endDate));
            }
            if (criteria.getParentInternalCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getParentInternalCode(), StdCodes_.parentInternalCode));
            }
            if (criteria.getComments() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComments(), StdCodes_.comments));
            }
            if (criteria.getSecLevel() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSecLevel(), StdCodes_.secLevel));
            }
            if (criteria.getCodeValueDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCodeValueDate(), StdCodes_.codeValueDate));
            }
            if (criteria.getCodeValueString() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCodeValueString(), StdCodes_.codeValueString));
            }
            if (criteria.getCodeValueBool() != null) {
                specification = specification.and(buildSpecification(criteria.getCodeValueBool(), StdCodes_.codeValueBool));
            }
            if (criteria.getCodeValueNumber() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCodeValueNumber(), StdCodes_.codeValueNumber));
            }
            if (criteria.getStdCodesDescId() != null) {
                specification = specification.and(buildSpecification(criteria.getStdCodesDescId(),
                    root -> root.join(StdCodes_.stdCodesDescs, JoinType.LEFT).get(StdCodesDesc_.id)));
            }
            if (criteria.getStdCodesGroupId() != null) {
                specification = specification.and(buildSpecification(criteria.getStdCodesGroupId(),
                    root -> root.join(StdCodes_.stdCodesGroup, JoinType.LEFT).get(StdCodesGroup_.id)));
            }
        }
        return specification;
    }
}

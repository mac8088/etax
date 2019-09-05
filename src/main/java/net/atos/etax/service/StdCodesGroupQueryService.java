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

import net.atos.etax.domain.StdCodesGroup;
import net.atos.etax.domain.*; // for static metamodels
import net.atos.etax.repository.StdCodesGroupRepository;
import net.atos.etax.service.dto.StdCodesGroupCriteria;

/**
 * Service for executing complex queries for {@link StdCodesGroup} entities in the database.
 * The main input is a {@link StdCodesGroupCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link StdCodesGroup} or a {@link Page} of {@link StdCodesGroup} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class StdCodesGroupQueryService extends QueryService<StdCodesGroup> {

    private final Logger log = LoggerFactory.getLogger(StdCodesGroupQueryService.class);

    private final StdCodesGroupRepository stdCodesGroupRepository;

    public StdCodesGroupQueryService(StdCodesGroupRepository stdCodesGroupRepository) {
        this.stdCodesGroupRepository = stdCodesGroupRepository;
    }

    /**
     * Return a {@link List} of {@link StdCodesGroup} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<StdCodesGroup> findByCriteria(StdCodesGroupCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<StdCodesGroup> specification = createSpecification(criteria);
        return stdCodesGroupRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link StdCodesGroup} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<StdCodesGroup> findByCriteria(StdCodesGroupCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<StdCodesGroup> specification = createSpecification(criteria);
        return stdCodesGroupRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(StdCodesGroupCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<StdCodesGroup> specification = createSpecification(criteria);
        return stdCodesGroupRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<StdCodesGroup> createSpecification(StdCodesGroupCriteria criteria) {
        Specification<StdCodesGroup> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), StdCodesGroup_.id));
            }
            if (criteria.getGroupCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGroupCode(), StdCodesGroup_.groupCode));
            }
            if (criteria.getGroupDesc() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGroupDesc(), StdCodesGroup_.groupDesc));
            }
            if (criteria.getSystemInd() != null) {
                specification = specification.and(buildSpecification(criteria.getSystemInd(), StdCodesGroup_.systemInd));
            }
            if (criteria.getSecLevelRequired() != null) {
                specification = specification.and(buildSpecification(criteria.getSecLevelRequired(), StdCodesGroup_.secLevelRequired));
            }
            if (criteria.getValueRequired() != null) {
                specification = specification.and(buildSpecification(criteria.getValueRequired(), StdCodesGroup_.valueRequired));
            }
            if (criteria.getValueType() != null) {
                specification = specification.and(buildSpecification(criteria.getValueType(), StdCodesGroup_.valueType));
            }
            if (criteria.getDescriptionRequired() != null) {
                specification = specification.and(buildSpecification(criteria.getDescriptionRequired(), StdCodesGroup_.descriptionRequired));
            }
            if (criteria.getExternalCodeRequired() != null) {
                specification = specification.and(buildSpecification(criteria.getExternalCodeRequired(), StdCodesGroup_.externalCodeRequired));
            }
            if (criteria.getExternalCodeLength() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getExternalCodeLength(), StdCodesGroup_.externalCodeLength));
            }
            if (criteria.getParentGroupRequired() != null) {
                specification = specification.and(buildSpecification(criteria.getParentGroupRequired(), StdCodesGroup_.parentGroupRequired));
            }
            if (criteria.getParentGroupCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getParentGroupCode(), StdCodesGroup_.parentGroupCode));
            }
            if (criteria.getTouppercase() != null) {
                specification = specification.and(buildSpecification(criteria.getTouppercase(), StdCodesGroup_.touppercase));
            }
            if (criteria.getStdCodesId() != null) {
                specification = specification.and(buildSpecification(criteria.getStdCodesId(),
                    root -> root.join(StdCodesGroup_.stdCodes, JoinType.LEFT).get(StdCodes_.id)));
            }
        }
        return specification;
    }
}
